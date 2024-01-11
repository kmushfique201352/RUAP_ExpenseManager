package com.example.rajuk_expensemanager;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.rajuk_expensemanager.databinding.ActivityViewFlatOwnersBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewFlatOwnersActivity extends AppCompatActivity {
    ActivityViewFlatOwnersBinding binding;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    ArrayList<FlatOwner> flatOwnerArrayList;
    FlatOwnerAdapter flatOwnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewFlatOwnersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        flatOwnerArrayList=new ArrayList<>();
        binding.myrecycle.setLayoutManager(new LinearLayoutManager(this));
        binding.myrecycle.setHasFixedSize(true);


        loadFlatOwner();
    }
    public void loadFlatOwner(){
        firebaseFirestore.collection("FlatOwner")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                            FlatOwner flatOwner=new FlatOwner(
                                    documentSnapshot.getString("idOwner"),
                                    documentSnapshot.getString("email"),
                                    documentSnapshot.getString("date"),
                                    documentSnapshot.getString("name"),
                                    documentSnapshot.getString("flatNo"),
                                    documentSnapshot.getString("password")
                            );
                            flatOwnerArrayList.add(flatOwner);
                        }
                        flatOwnerAdapter=new FlatOwnerAdapter(ViewFlatOwnersActivity.this,flatOwnerArrayList);
                        binding.myrecycle.setAdapter(flatOwnerAdapter);
                    }
                });
    }
}


//.orderBy("flatNo", Query.Direction.ASCENDING)
//        .addSnapshotListener(new EventListener<QuerySnapshot>() {
//@Override
//public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//        if(error!=null){
    //        Log.e("Firebase error ",error.getMessage());
    //        return;
//        }
//        for(DocumentChange dc:value.getDocumentChanges()){
    //        if(dc.getType()==DocumentChange.Type.ADDED){
        //        flatOwnerArrayList.add(dc.getDocument().toObject(FlatOwner.class));
    //        }
    //        flatOwnerAdapter.notifyDataSetChanged();
//        }
//        flatOwnerAdapter=new FlatOwnerAdapter(ViewFlatOwnersActivity.this,flatOwnerArrayList);
//        binding.myrecycle.setAdapter(flatOwnerAdapter);
//        }
//        });