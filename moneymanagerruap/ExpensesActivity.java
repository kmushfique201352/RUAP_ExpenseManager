package com.example.moneymanagerruap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.moneymanagerruap.databinding.ActivityExpensesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExpensesActivity extends AppCompatActivity {
    ActivityExpensesBinding binding;
    private SimpleDateFormat dateFormat;
    FirebaseFirestore firebaseFirestore;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();

        //Current Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateandTime = sdf.format(new Date());
        binding.date.setText(currentDateandTime);

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddExpenses();
            }
        });
    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        Intent i;
        startActivity(i=new Intent(ExpensesActivity.this,AdminActivity.class));
    }

    private void AddExpenses(){
        String date=binding.date.getText().toString().trim();
        String purpose=binding.purpose.getText().toString().trim();
        String amountExp=binding.amountExp.getText().toString().trim();

        if(TextUtils.isEmpty(binding.purpose.getText().toString())||TextUtils.isEmpty(binding.amountExp.getText().toString())){
            Toast.makeText(this, "Details not given", Toast.LENGTH_SHORT).show();
        }else{
            builder=new AlertDialog.Builder(ExpensesActivity.this);
            builder.setTitle("Confirmation !!!");
            builder.setCancelable(true);
            builder.setMessage("On date: "+date+" you have spend TK "+amountExp+" for "+purpose+" ?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Map<String,Object> owner=new HashMap<>();
                    owner.put("date",date);
                    owner.put("purpose",purpose);
                    owner.put("expense",amountExp);

                    firebaseFirestore.collection("Expenses")
                            .add(owner).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Toast.makeText(ExpensesActivity.this, "Expenses Added", Toast.LENGTH_SHORT).show();
                                    binding.purpose.setText("");
                                    binding.amountExp.setText("");
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    onRestart();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ExpensesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alertDialog=builder.create();
            alertDialog.show();
        }

    }
}