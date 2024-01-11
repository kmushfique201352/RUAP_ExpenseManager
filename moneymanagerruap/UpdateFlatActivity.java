package com.example.moneymanagerruap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.moneymanagerruap.databinding.ActivityUpdateFlatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpdateFlatActivity extends AppCompatActivity {
    ActivityUpdateFlatBinding binding;

    FirebaseFirestore firebaseFirestore;

    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateFlatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flatNo=binding.flatNo.getText().toString();
                String newName=binding.newName.getText().toString();
                String dateNew=binding.dateNo.getText().toString();
                binding.flatNo.setText("");
                binding.newName.setText("");
                binding.dateNo.setText("");
                UpdateName(flatNo,newName,dateNew);
            }
        });
        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flatNo=binding.flatNo.getText().toString();
                String newName=binding.newName.getText().toString();
                binding.flatNo.setText("");
                binding.newName.setText("");
                DeleteFlat(flatNo);
            }
        });
    }
    private void UpdateName(String flatNo,String newName,String dateNew){
        Map<String,Object> owner=new HashMap<>();
        owner.put("name",newName);
        owner.put("date",dateNew);

        firebaseFirestore.collection("Flat").whereEqualTo("flatNo",flatNo)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                            String documentId=documentSnapshot.getId();

                            builder=new AlertDialog.Builder(UpdateFlatActivity.this);
                            builder.setTitle("Warning !!!");
                            builder.setCancelable(true);
                            builder.setMessage("You want to Update flat "+flatNo+" ?");
                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i){
                            firebaseFirestore.collection("Flat").document(documentId)
                                    .update(owner).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(UpdateFlatActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UpdateFlatActivity.this, "Error !!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(UpdateFlatActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void DeleteFlat(String flatNo){

        firebaseFirestore.collection("Flat").whereEqualTo("flatNo",flatNo)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                            String documentId=documentSnapshot.getId();

                            builder=new AlertDialog.Builder(UpdateFlatActivity.this);
                            builder.setTitle("Warning !!!");
                            builder.setCancelable(true);
                            builder.setMessage("You want to Delete flat "+flatNo+" ?");
                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i){
                                    firebaseFirestore.collection("Flat").document(documentId)
                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(UpdateFlatActivity.this, "Successfully Deleted!!", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(UpdateFlatActivity.this, "Error !!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(UpdateFlatActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}