package com.example.rajuk_expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.rajuk_expensemanager.databinding.ActivityAddFlatBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddFlatActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SimpleDateFormat dateFormat;

    String[] flatNo={
            "101   ","102","103","104","105","106",
            "201","202","203","204","205","206",
            "301","302","303","304","305","306",
            "401","402","403","404","405","406",
            "501","502","503","504","505","506",
            "601","602","603","604","605","606 ",
            "701 ","702 ","703 ","704 ","705 ","706 ",
            "801 ","802 ","803 ","804 ","805 ","806 ",
            "901 ","402 ","903 ","904 ","905 ","906 ",
            "1001","1002","1003","1004","1005","1006",
            "1101","1102","1103","1104","1105","1106",
            "1201","1202","1203","1204","1205","1206",
            "1301","1302","1303","1304","1305","1306",
            "1401","1402","1403","1404","1405","1406",
    };

    ActivityAddFlatBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddFlatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateandTime = sdf.format(new Date());
        binding.dateOfEntry.setText(currentDateandTime);

        binding.flatNo.setOnItemSelectedListener(this);
        ArrayAdapter aaFlat=new ArrayAdapter(this, android.R.layout.simple_spinner_item,flatNo);
        aaFlat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.flatNo.setAdapter(aaFlat);

        binding.addNewPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date=binding.dateOfEntry.getText().toString();
                String name=binding.name.getText().toString();
                String flatNo=binding.flatNo.getSelectedItem().toString();
                String email=binding.email.getText().toString().trim();
                String password=binding.password.getText().toString().trim();
                if(name.trim().length()<=0||flatNo.trim().length()<=0||email.trim().length()<=0||password.trim().length()<=0){
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Map<String,Object> owner=new HashMap<>();
                        owner.put("idOwner",firebaseAuth.getUid());
                        owner.put("date",date);
                        owner.put("name",name);
                        owner.put("flatNo",flatNo);
                        owner.put("email",email);
                        owner.put("password",password);

                        firebaseFirestore.collection("FlatOwner").document(firebaseAuth.getUid())
                                .set(owner)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(AddFlatActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                        binding.name.setText("");
                                        binding.email.setText("");
                                        binding.password.setText("");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddFlatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddFlatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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