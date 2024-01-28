package com.example.outfitmakerfake.CreazioneOutfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.Home;
import com.example.outfitmakerfake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import Storage.Archivio.ArchivioDAO;
import Storage.Archivio.ArchivioService;
import Storage.Armadio.ArmadioDAO;
import Storage.Armadio.ArmadioService;

public class OutfitCreato extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Capo> lista_capi;
    AdapterOutfitCreato myAdapter;
    FirebaseFirestore db;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArmadioService armadioService;
    Button btn_salva_outfit_creato;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outfit_creato);

        btn_salva_outfit_creato = findViewById(R.id.btn_salva_outfit_creato);

        armadioService = new ArmadioService(new ArmadioDAO());


        recyclerView = findViewById(R.id.recycleList_outfit_creato);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        lista_capi = getIntent().getParcelableArrayListExtra("outfit");
        myAdapter = new AdapterOutfitCreato(OutfitCreato.this, lista_capi);

        recyclerView.setAdapter(myAdapter);

        btn_salva_outfit_creato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                armadioService.creaOutfit(lista_capi).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if(task.isSuccessful()){
                            Boolean creazioneOutfit = task.getResult();
                            if(creazioneOutfit){
                                Toast.makeText(getApplicationContext(), "Outfit creato e salvato in Archivio", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Errore nella creazione dell'outfit", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }
}
