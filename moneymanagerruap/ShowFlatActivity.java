package com.example.moneymanagerruap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.moneymanagerruap.databinding.ActivityShowFlatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowFlatActivity extends AppCompatActivity {
    ActivityShowFlatBinding binding;

    FirebaseFirestore firebaseFirestore;

    ArrayList<Flat> flatArrayList;
    FlatAdapter flatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowFlatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();

        flatArrayList=new ArrayList<>();
        binding.myRecycle.setHasFixedSize(true);
        binding.myRecycle.setLayoutManager(new LinearLayoutManager(this));

        showDate();
    }
    private void showDate(){
        firebaseFirestore.collection("Flat").orderBy("flatNo", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot ds:task.getResult()){
                            Flat flat=new Flat(
                                ds.getString("date"),
                                ds.getString("name"),
                                ds.getString("flatNo"));
                            flatArrayList.add(flat);
                        }
                        flatAdapter=new FlatAdapter(ShowFlatActivity.this,flatArrayList);
                        binding.myRecycle.setAdapter(flatAdapter);
                    }
                });
    }
}