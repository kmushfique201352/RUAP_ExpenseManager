package com.example.rajuk_expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rajuk_expensemanager.databinding.ActivityAddExpensesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddExpensesActivity extends AppCompatActivity {
    private SimpleDateFormat dateFormat;

    ActivityAddExpensesBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();


        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateandTime = sdf.format(new Date());
        binding.dateOfExpense.setText(currentDateandTime);

        binding.addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date=binding.dateOfExpense.getText().toString().trim();
                String purpose=binding.purpose.getText().toString().trim();
                String amount=binding.amount.getText().toString().trim();

                if(amount.length()<=0||purpose.length()<=0){
                    return;
                }
                String id= UUID.randomUUID().toString();
                Map<String,Object> expenses=new HashMap<>();
                expenses.put("id",id);
                expenses.put("date",date);
                expenses.put("purpose",purpose);
                expenses.put("amount",amount);

                firebaseFirestore.collection("Expense").document(firebaseAuth.getUid()).collection("Purpose").document(id)
                        .set(expenses)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddExpensesActivity.this, "Expenses added", Toast.LENGTH_SHORT).show();
                                binding.amount.setText("");
                                binding.purpose.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddExpensesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}