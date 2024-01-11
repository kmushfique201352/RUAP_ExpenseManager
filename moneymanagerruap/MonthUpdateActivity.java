package com.example.moneymanagerruap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.moneymanagerruap.databinding.ActivityMonthUpdateBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MonthUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityMonthUpdateBinding binding;

    FirebaseFirestore firebaseFirestore;

    String[] month={"January","February","March","April","May","June","July","August","September","October","November","December"};
    String[] year={"2020","2021","2022","2023"};

    AlertDialog alertDialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMonthUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();

        //Spinner to select Month
        binding.monthSpin.setOnItemSelectedListener(this);
        ArrayAdapter aaFlat=new ArrayAdapter(this, android.R.layout.simple_spinner_item,month);
        aaFlat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.monthSpin.setAdapter(aaFlat);

        //Spinner to select Year
        binding.yearSpin.setOnItemSelectedListener(this);
        ArrayAdapter aaFlat2=new ArrayAdapter(this, android.R.layout.simple_spinner_item,year);
        aaFlat2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.yearSpin.setAdapter(aaFlat2);

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month=binding.monthSpin.getSelectedItem().toString().trim();
                String year=binding.yearSpin.getSelectedItem().toString().trim();
                String amount=binding.amountForMonth.getText().toString().trim();

                if(TextUtils.isEmpty(binding.amountForMonth.getText().toString())){
                    Toast.makeText(MonthUpdateActivity.this, "Details not given", Toast.LENGTH_SHORT).show();
                }else{
                    Map<String,Object> owner=new HashMap<>();
                    owner.put("month",month);
                    owner.put("year",year);
                    owner.put("amount",amount);

                    firebaseFirestore.collection("MonthlyServiceCharge")
                            .add(owner).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(MonthUpdateActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MonthUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
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