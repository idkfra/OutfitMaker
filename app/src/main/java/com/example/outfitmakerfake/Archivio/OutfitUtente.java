package com.example.outfitmakerfake.Archivio;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.Armadio.AdapterListaArmadio;
import com.example.outfitmakerfake.Armadio.ArmadioController;
import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OutfitUtente extends AppCompatActivity {

    String id_outfit;
    ArrayList<Capo> lista_capi;

    RecyclerView recyclerView;
    AdapterListaArmadio myAdapter;
    FirebaseFirestore db;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanteState){
        super.onCreate(savedInstanteState);
        setContentView(R.layout.outfit_utente);

        id_outfit = getIntent().getStringExtra("id_outfit");
        lista_capi = getIntent().getParcelableArrayListExtra("lista_capi");

        recyclerView = findViewById(R.id.recycleList_outfitUtente);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterListaArmadio(getApplicationContext(), lista_capi);

        recyclerView.setAdapter(myAdapter);
    }
}
