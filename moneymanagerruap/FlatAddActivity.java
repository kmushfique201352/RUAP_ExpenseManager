package com.example.moneymanagerruap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.moneymanagerruap.databinding.ActivityFlatAddBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlatAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SimpleDateFormat dateFormat;
    Context context;
    //Spinner array
    String[] flatNo={
            "101   ","102","103","104","105","106",
            "201","202","203","204","205","206",
            "301","302","303","304","305","306",
            "401","402","403","404","405","406",
            "501","502","503","504","505","506",
            "601","602","603","604","605","606 ",
            "701","702","703","704","705","706",
            "801","802","803","804","805","806",
            "901","902","903","904","905","906",
            "1001","1002","1003","1004","1005","1006",
            "1101","1102","1103","1104","1105","1106",
            "1201","1202","1203","1204","1205","1206",
            "1301","1302","1303","1304","1305","1306",
            "1401","1402","1403","1404","1405","1406",
    };


    ActivityFlatAddBinding binding;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFlatAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();

        //Current Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateandTime = sdf.format(new Date());
        binding.dateOfEntry.setText(currentDateandTime);

        //Spinner to select flat No
        binding.flatNoSpin.setOnItemSelectedListener(this);
        ArrayAdapter aaFlat=new ArrayAdapter(this, android.R.layout.simple_spinner_item,flatNo);
        aaFlat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.flatNoSpin.setAdapter(aaFlat);

        //add button work
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date=binding.dateOfEntry.getText().toString().trim();
                String name=binding.name.getText().toString().trim();
                String flatNo=binding.flatNoSpin.getSelectedItem().toString().trim();

                //no empty data will be taken
                if(TextUtils.isEmpty(binding.name.getText().toString())){
                    Toast.makeText(FlatAddActivity.this, "Details not given", Toast.LENGTH_SHORT).show();
                }else{
                    builder=new AlertDialog.Builder(FlatAddActivity.this);
                    builder.setTitle("Warning !!!");
                    builder.setCancelable(true);
                    builder.setMessage("Add flat ?");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Map<String,Object> owner=new HashMap<>();
                            owner.put("date",date);
                            owner.put("name",name);
                            owner.put("flatNo",flatNo);

                            firebaseFirestore.collection("Flat").document(flatNo)
                                    .set(owner).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(FlatAddActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                            binding.name.setText("");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(FlatAddActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}