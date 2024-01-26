package com.example.outfitmakerfake.CreazioneOutfit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OutfitCreato extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Capo> lista_capi;
    AdapterOutfitCreato myAdapter;
    FirebaseFirestore db;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    Button btn_salva_outfit;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outfit_creato);

        recyclerView = findViewById(R.id.recycleList_outfit_creato);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        lista_capi = getIntent().getParcelableArrayListExtra("outfit");
        myAdapter = new AdapterOutfitCreato(OutfitCreato.this, lista_capi);

        recyclerView.setAdapter(myAdapter);

        btn_salva_outfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
