package com.example.moneymanagerruap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.moneymanagerruap.databinding.ActivityAdminBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;
    int income=0,expense=0;

    NetworkChangeListener networkChangeListener=new NetworkChangeListener();

    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();



        binding.addFlatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,FlatAddActivity.class);
                try {
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        binding.showFlatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,ShowFlatActivity.class);
                try {
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        binding.updateFlatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,UpdateFlatActivity.class);
                try {
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        binding.expensesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,ExpensesActivity.class);
                try{
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        binding.showExpensebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,ShowExpensesActivity.class);
                try{
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
//        binding.MonthlyChargebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(AdminActivity.this,MonthUpdateActivity.class);
//                try {
//                    startActivity(intent);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
        binding.serviceChargebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,ServiceChargeActivity.class);
                    try {
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }
        });

        firebaseFirestore.collection("Income")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot ds:task.getResult()){
                            IncomeJava incomeJava=new IncomeJava(
                                    ds.getString("amount")
                            );
                            int amo=Integer.parseInt(ds.getString("amount"));
                            income=income+amo;
                        }
                        binding.incomeAdmin.setText(String.valueOf(income));
                        int balance=income-expense;
                        binding.balanceAdmin.setText(String.valueOf(balance));
                    }
                });

        firebaseFirestore.collection("Expenses")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot ds:task.getResult()){
                            ExpensesJava expensesJava=new ExpensesJava(
                                    ds.getString("date"),
                                    ds.getString("purpose"),
                                    ds.getString("expense")
                            );
                            int amo=Integer.parseInt(ds.getString("expense"));
                            expense=expense+amo;
                        }
                        binding.expensesAdmin.setText(String.valueOf(expense));
                        int balance=income-expense;
                        binding.balanceAdmin.setText(String.valueOf(balance));
                    }
                });


        }

    @Override
    protected void onStart() {
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}