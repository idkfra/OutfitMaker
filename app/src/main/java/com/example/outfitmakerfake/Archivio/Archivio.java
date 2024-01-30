package com.example.outfitmakerfake.Archivio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.AreaUtente.AreaUtenteController;
import com.example.outfitmakerfake.Entity.Outfit;
import com.example.outfitmakerfake.R;
import com.example.outfitmakerfake.Utility.NetworkUtil;
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
    FragmentManager fm;
    FrameLayout frammento_vedi_outfit;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archivio);

        if (!NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente", Toast.LENGTH_SHORT).show();
            return;
        }

        fm = getSupportFragmentManager();
        frammento_vedi_outfit = findViewById(R.id.contenitore_frammento_vedi_outfit);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.menu1){
            setContentView(R.layout.home);
            return true;
        } else if(id == R.id.menu2){
            Intent i = new Intent(getApplicationContext(), AreaUtenteController.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            return true;
        } else if(id == R.id.menu3){
            setContentView(R.layout.assistenza);
            return true;
        } else if(id == R.id.menu4){
            setContentView(R.layout.istruzioni);
        }
        return super.onOptionsItemSelected(item);
    }
}