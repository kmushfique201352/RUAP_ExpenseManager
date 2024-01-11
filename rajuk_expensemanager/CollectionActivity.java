package com.example.rajuk_expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rajuk_expensemanager.databinding.ActivityCollectionBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity {
    ActivityCollectionBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    int sumExpenses=0;

    ArrayList<TransactionModel> transactionModelArrayList;
    TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCollectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        transactionModelArrayList=new ArrayList<>();
        binding.historyRecycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.historyRecycleView.setHasFixedSize(true);

        binding.addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CollectionActivity.this,AddExpensesActivity.class);
                try {
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        laodData();

    }
    private void laodData(){
        firebaseFirestore.collection("Expense").document(firebaseAuth.getUid()).collection("Purpose")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot ds:task.getResult()){
                            TransactionModel model=new TransactionModel(
                                    ds.getString("id"),
                                    ds.getString("amount"),
                                    ds.getString("date"),
                                    ds.getString("purpose"),
                                    ds.getString("type")
                            );
                            int amount=Integer.parseInt(ds.getString("amount"));
                            sumExpenses=sumExpenses+amount;
                            transactionModelArrayList.add(model);
                        }
                        binding.totalExpense.setText(String.valueOf(sumExpenses));
                        binding.totalBalance.setText(String.valueOf(0-sumExpenses));

                        transactionAdapter=new TransactionAdapter(CollectionActivity.this,transactionModelArrayList);
                        binding.historyRecycleView.setAdapter(transactionAdapter);
                    }
                });
    }
}