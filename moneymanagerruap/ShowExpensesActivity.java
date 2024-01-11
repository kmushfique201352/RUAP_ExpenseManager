package com.example.moneymanagerruap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.moneymanagerruap.databinding.ActivityShowExpensesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowExpensesActivity extends AppCompatActivity {
    ActivityShowExpensesBinding binding;
    FirebaseFirestore firebaseFirestore;
    ArrayList<ExpensesJava> expensesJavaArrayList;
    ExpenseAdapter expenseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();

        expensesJavaArrayList=new ArrayList<>();
        binding.myExRecycle.setHasFixedSize(true);
        binding.myExRecycle.setLayoutManager(new LinearLayoutManager(this));

        Expshow();
    }
    private void Expshow(){
        firebaseFirestore.collection("Expenses")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot ds:task.getResult()){
                            ExpensesJava expensesJava=new ExpensesJava(
                                    ds.getString("date"),
                                    ds.getString("purpose"),
                                    ds.getString("expense") );
                            expensesJavaArrayList.add(expensesJava);
                        }
                        expenseAdapter=new ExpenseAdapter(ShowExpensesActivity.this,expensesJavaArrayList);
                        binding.myExRecycle.setAdapter(expenseAdapter);
                    }
                });
    }
}