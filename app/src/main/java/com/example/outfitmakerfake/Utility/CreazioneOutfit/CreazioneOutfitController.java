package com.example.outfitmakerfake.Utility.CreazioneOutfit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.R;
import com.example.outfitmakerfake.RecycleViewCreazioneInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import Storage.Armadio.ArmadioDAO;
import Storage.Armadio.ArmadioService;

public class CreazioneOutfitController extends AppCompatActivity implements RecycleViewCreazioneInterface {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db;
    FirebaseUser user = mAuth.getCurrentUser();
    ArmadioService armadioService;
    AdapterCreazioneOutfit myAdapter;
    ArrayList<Capo> capoArrayList;
    ArrayList<Capo> outfit;
    RecyclerView recyclerView;
    Button btn_crea_outfit;
    Button btn_resetta_lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creazione_outfit);

        btn_resetta_lista = findViewById(R.id.btn_resetta_lista);

        recyclerView = findViewById(R.id.recycleListCreazione);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        capoArrayList = new ArrayList<>();
        outfit = new ArrayList<>();
        myAdapter = new AdapterCreazioneOutfit(CreazioneOutfitController.this, capoArrayList);

        recyclerView.setAdapter(myAdapter);
        EventChangeListener();

        armadioService = new ArmadioService(new ArmadioDAO());

        btn_crea_outfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < outfit.size(); i++) {
                    Log.d("INSERTTT", "Tipologia " + i + ": " + outfit.get(i).getTipologia() + "isScelto: " + outfit.get(i).getScelto());
                }
            }
        });

        btn_resetta_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                armadioService.resettaSceltaCapi();
                // Resetta anche 'outfit'
                outfit.clear();
                myAdapter.notifyDataSetChanged();
                Log.d("INSERTTT", "Lista resettata");
            }
        });


    }

    private void EventChangeListener() {
        String uid = user.getUid();

        db.collection("capi")
                .whereEqualTo("uid", uid)
                .orderBy("tipologia", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.d("LISTA", "Firestore error: " + error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Capo capo = dc.getDocument().toObject(Capo.class);
                                capoArrayList.add(capo);

                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void aggiungiCapoClicked(int position) {
        Toast.makeText(this, "Click position: " + position, Toast.LENGTH_SHORT).show();
    }
}