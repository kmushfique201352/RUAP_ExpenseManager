package com.example.moneymanagerruap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.moneymanagerruap.databinding.ActivityServiceChargeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServiceChargeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] monthNo={
            "January","February","March","April","May","June","July","August","September","October","November","December"
    };

    private SimpleDateFormat dateFormat;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;

    ActivityServiceChargeBinding binding;

    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityServiceChargeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();

        //Spinner to select flat No
        binding.monthNoSpin.setOnItemSelectedListener(this);
        ArrayAdapter aaMonth=new ArrayAdapter(this, android.R.layout.simple_spinner_item,monthNo);
        aaMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.monthNoSpin.setAdapter(aaMonth);

        //Current Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateandTime = sdf.format(new Date());
        binding.dateOfEntry.setText(currentDateandTime);

        binding.RefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Flat").whereEqualTo("flatNo",binding.FlatNo.getText().toString())
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful() && !task.getResult().isEmpty()){
                                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                                    binding.nameOwner.setText(documentSnapshot.getString("name"));
                                }else{
                                    binding.nameOwner.setText(null);
                                    Toast.makeText(ServiceChargeActivity.this, "Failed to find", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.nameOwner.toString())||TextUtils.isEmpty(binding.monthNoSpin.toString())||TextUtils.isEmpty(binding.yearOfSC.toString())||TextUtils.isEmpty(binding.amount.toString())||TextUtils.isEmpty(binding.mrh.toString())){
                    Toast.makeText(ServiceChargeActivity.this, "Fillup details", Toast.LENGTH_SHORT).show();
                }else{
                    binding.pBar.setVisibility(View.VISIBLE);
                    binding.main.setVisibility(View.INVISIBLE);
                    addServiceCharge();
                }
            }
        });

    }
    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        Intent i;
        startActivity(i=new Intent(ServiceChargeActivity.this,AdminActivity.class));
    }
    private void addServiceCharge(){
        Map<String,Object> sc=new HashMap<>();
        sc.put("date",binding.dateOfEntry.getText().toString());
        sc.put("month",binding.monthNoSpin.getSelectedItem().toString());
        sc.put("year",binding.yearOfSC.getText().toString());
        sc.put("amount",binding.amount.getText().toString());
        sc.put("mrh",binding.mrh.getText().toString());

        firebaseFirestore.collection("Flat").whereEqualTo("flatNo",binding.FlatNo.getText().toString())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                            String documentId=documentSnapshot.getId();

                            builder=new AlertDialog.Builder(ServiceChargeActivity.this);
                            builder.setTitle("Confirmation !!!");
                            builder.setCancelable(true);
                            builder.setMessage("On date: "+binding.dateOfEntry.getText().toString().trim()+", "+binding.nameOwner.getText().toString().trim()+"("+binding.FlatNo.getText().toString().trim()+")"+" has given Service charge TK "+binding.amount.getText().toString().trim()+" for the month "+binding.monthNoSpin.getSelectedItem().toString().trim()+"-"+binding.yearOfSC.getText().toString().trim()+" ?");
                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                            firebaseFirestore.collection("Flat").document(documentId).collection("ServiceCharge").document(binding.monthNoSpin.getSelectedItem().toString()+"-"+binding.yearOfSC.getText().toString())
                                    .set(sc).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Map<String,Object> income=new HashMap<>();
                                            income.put("amount",binding.amount.getText().toString());

                                            firebaseFirestore.collection("Income").document(binding.FlatNo.getText().toString()+"-"+binding.monthNoSpin.getSelectedItem().toString()+"-"+binding.yearOfSC.getText().toString())
                                                    .set(income).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(ServiceChargeActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                                            binding.pBar.setVisibility(View.INVISIBLE);
                                                            binding.main.setVisibility(View.VISIBLE);
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
                                                            Toast.makeText(ServiceChargeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                                            binding.pBar.setVisibility(View.INVISIBLE);
                                                            binding.main.setVisibility(View.VISIBLE);
                                                        }
                                                    });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ServiceChargeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                            binding.pBar.setVisibility(View.INVISIBLE);
                                            binding.main.setVisibility(View.VISIBLE);
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
                        }else{
                            Toast.makeText(ServiceChargeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}