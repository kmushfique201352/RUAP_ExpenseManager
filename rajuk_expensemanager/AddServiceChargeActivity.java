package com.example.rajuk_expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.rajuk_expensemanager.databinding.ActivityAddServiceChargeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddServiceChargeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SimpleDateFormat dateFormat;

    ActivityAddServiceChargeBinding binding;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    FlatOwner flatOwner;

    String[] flatNo={
            "Flat No: 101","Flat No: 102","Flat No: 103","Flat No: 104","Flat No: 105","Flat No: 106",
            "Flat No: 201","Flat No: 202","Flat No: 203","Flat No: 204","Flat No: 205","Flat No: 206",
            "Flat No: 301","Flat No: 302","Flat No: 303","Flat No: 304","Flat No: 305","Flat No: 306",
            "Flat No: 401","Flat No: 402","Flat No: 403","Flat No: 404","Flat No: 405","Flat No: 406",
            "Flat No: 501","Flat No: 502","Flat No: 503","Flat No: 504","Flat No: 505","Flat No: 506",
            "Flat No: 601","Flat No: 602","Flat No: 603","Flat No: 604","Flat No: 605","Flat No: 606",
            "Flat No: 701","Flat No: 702","Flat No: 703","Flat No: 704","Flat No: 705","Flat No: 706",
            "Flat No: 801","Flat No: 802","Flat No: 803","Flat No: 804","Flat No: 805","Flat No: 806",
            "Flat No: 901","Flat No: 402","Flat No: 903","Flat No: 904","Flat No: 905","Flat No: 906",
            "Flat No: 1001","Flat No: 1002","Flat No: 1003","Flat No: 1004","Flat No: 1005","Flat No: 1006",
            "Flat No: 1101","Flat No: 1102","Flat No: 1103","Flat No: 1104","Flat No: 1105","Flat No: 1106",
            "Flat No: 1201","Flat No: 1202","Flat No: 1203","Flat No: 1204","Flat No: 1205","Flat No: 1206",
            "Flat No: 1301","Flat No: 1302","Flat No: 1303","Flat No: 1304","Flat No: 1305","Flat No: 1306",
            "Flat No: 1401","Flat No: 1402","Flat No: 1403","Flat No: 1404","Flat No: 1405","Flat No: 1406",
    };
    String[] month={"January","February","March","April","May","June","July","August","September","October","November","December"};
    String[] year={"2020","2021","2022"};
    String FlatID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddServiceChargeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateandTime = sdf.format(new Date());
        binding.dateOf.setText(currentDateandTime);

        binding.FlatNoSS.setOnItemSelectedListener(this);
        ArrayAdapter aaFlat=new ArrayAdapter(this, android.R.layout.simple_spinner_item,flatNo);
        aaFlat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.FlatNoSS.setAdapter(aaFlat);
        FlatID=binding.FlatNoSS.getSelectedItem().toString().trim();
        loadOwner();

        ArrayAdapter aaMonth=new ArrayAdapter(this, android.R.layout.simple_spinner_item,month);
        aaMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.MonthSS.setAdapter(aaMonth);

        ArrayAdapter aaYear=new ArrayAdapter(this, android.R.layout.simple_spinner_item,year);
        aaYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.YearSS.setAdapter(aaYear);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void loadOwner(){
        firebaseFirestore.collection("FlatOwner").whereEqualTo("flatNo",FlatID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot ds:task.getResult()){
                            flatOwner=new FlatOwner(
                                    ds.getString("idOwner"),
                                    ds.getString("email"),
                                    ds.getString("date"),
                                    ds.getString("name"),
                                    ds.getString("flatNo"),
                                    ds.getString("password")
                            );
                        }
                        binding.FlatOwner.setText(flatOwner.name);
                    }
                });
    }


}