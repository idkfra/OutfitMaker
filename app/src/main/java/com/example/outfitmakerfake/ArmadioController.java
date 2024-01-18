package com.example.outfitmakerfake;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.outfitmakerfake.Entity.Armadio;
import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.LoginController;
import com.example.outfitmakerfake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ArmadioController extends AppCompatActivity {

    EditText capoET;
    CheckBox coloreET;
    RadioGroup tipologiaET;
    RadioGroup stagionalitaET;
    Drawable immagineET;//?
    ProgressBar progressBar;

    String nomeCapo;
    ArrayList<String> colore;
    String tipologia;
    String stagionalita;

    Drawable immagine;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.armadio);

       /* progressBar=findViewById();
        nomeCapo=findViewById();
        coloreET=findViewById();
        tipologiaET=findViewById();
        stagionalitaET=findViewById();
*/
        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();


    }

    private Armadio getArmadioInstance() {
        // Ottieni l'ID dell'utente attualmente connesso
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Ritorna un'istanza di Armadio con l'ID dell'utente
        return new Armadio(userId, new ArrayList<>());
    }

}
