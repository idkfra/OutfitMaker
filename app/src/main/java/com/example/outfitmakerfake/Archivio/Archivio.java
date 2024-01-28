package com.example.outfitmakerfake.Archivio;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.Entity.Outfit;
import com.example.outfitmakerfake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Archivio extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Outfit> outfitArrayList;
    AdapterArchivio myAdapter;
    FirebaseFirestore db;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archivio);
        recyclerView = findViewById(R.id.recycleList_archivio);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        outfitArrayList = new ArrayList<>();
        myAdapter = new AdapterArchivio(this, outfitArrayList);

        recyclerView.setAdapter(myAdapter);
        eventChangeListener();
    }

    private void eventChangeListener() {
        String uid = currentUser.getUid();
        Log.d("ARCCC", "uid: " + uid);

        db.collection("outfit")
                .whereEqualTo("uid", uid)
                //.orderBy("id_outfit", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.d("LISTA", "Firestore error: " + error.getMessage());
                            return;
                        }

                        // Pulisci l'ArrayList prima di aggiungere nuovi outfit
                        outfitArrayList.clear();

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                outfitArrayList.add(dc.getDocument().toObject(Outfit.class));
                            }
                        }

                        myAdapter.notifyDataSetChanged();
                    }
                });
    }
}