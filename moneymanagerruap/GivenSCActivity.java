package com.example.moneymanagerruap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.moneymanagerruap.databinding.ActivityGivenScactivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GivenSCActivity extends AppCompatActivity {
    ActivityGivenScactivityBinding binding;
    int sum=0;

    FirebaseFirestore firebaseFirestore;

    ArrayList<Month> monthArrayList;
    SCAdapter scAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGivenScactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();

        binding.flat.setText(getIntent().getStringExtra("flat"));
        binding.name.setText(getIntent().getStringExtra("name"));

        monthArrayList=new ArrayList<>();
        binding.scView.setHasFixedSize(true);
        binding.scView.setLayoutManager(new LinearLayoutManager(this));

        showSC();
    }
    private void showSC(){
        firebaseFirestore.collection("Flat").whereEqualTo("name",binding.name.getText().toString())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                            String docID=documentSnapshot.getId();

                            firebaseFirestore.collection("Flat").document(docID).collection("ServiceCharge").orderBy("mrh", Query.Direction.DESCENDING)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for(DocumentSnapshot ds:task.getResult()){
                                                Month month=new Month(
                                                        ds.getString("date"),
                                                        ds.getString("amount"),
                                                        ds.getString("month"),
                                                        ds.getString("year"),
                                                        ds.getString("mrh"));
                                                monthArrayList.add(month);
                                                int am=Integer.parseInt(ds.getString("amount"));
                                                sum=sum+am;
                                            }
                                            scAdapter=new SCAdapter(GivenSCActivity.this,monthArrayList);
                                            binding.scView.setAdapter(scAdapter);

                                            binding.AM.setText(String.valueOf(sum));
                                        }
                                    });

                        }else{
                            Toast.makeText(GivenSCActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}