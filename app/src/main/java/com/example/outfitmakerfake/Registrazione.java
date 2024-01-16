package com.example.outfitmakerfake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

import Storage.UtenteDAO;

public class Registrazione extends AppCompatActivity {

    EditText nomeET;
    EditText cognomeET;
    EditText emailET;
    EditText passwordET;
    EditText telefonoET;
    ProgressBar progressBar;

    String nome;
    String cognome;
    String email;
    String password;
    String telefono;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione);

        progressBar = findViewById(R.id.progressBarR);
        nomeET = findViewById(R.id.editTextNome);
        cognomeET = findViewById(R.id.editTextCognome);
        emailET = findViewById(R.id.editTextEmail);
        passwordET = findViewById(R.id.editTextPassword);
        telefonoET = findViewById(R.id.editTextTelefono);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public void inserisciDati(View v){
        Log.d("UTENTE", "Entra in  inserisciDati");
        progressBar.setVisibility(View.VISIBLE);
        nome = nomeET.getText().toString();
        cognome = cognomeET.getText().toString();
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        telefono = telefonoET.getText().toString();

        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Compilare tutti i campi", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if (nome.length() < 3 || nome.length() > 15 || cognome.length() < 3 || cognome.length() > 15) {
            Toast.makeText(getApplicationContext(), "Nome e cognome devono avere da 3 a 15 caratteri", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        /*if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Formato email non valido", Toast.LENGTH_SHORT).show();
            return;
        }*/

        if (password.length() < 6 || !password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            Toast.makeText(getApplicationContext(), "La password deve avere almeno 6 caratteri, un numero e un simbolo", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if (telefono.length() != 10) {
            Toast.makeText(getApplicationContext(), "Il telefono deve avere 10 caratteri", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        UtenteDAO utenteDAO = new UtenteDAO();
        /*if(utenteDAO.creaUtente(nome, cognome, email, password, telefono)){
            Toast.makeText(getApplicationContext(), "Registrazione avvenuta con successo", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
        }*/

        /*if(!utenteDAO.creaUtente(nome, cognome, email, password, telefono)){
            Toast.makeText(getApplicationContext(), "Errore nella registrazione", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(getApplicationContext(), "Registrazione avvenuta con successo", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
        }*/

        utenteDAO.creaUtente(nome, cognome, email, password, telefono)
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        Log.d("REGISTRAZIONE", "Entra in onComplete");
                        if (task.isSuccessful() && task.getResult()) {
                            Log.d("REGISTRAZIONE", "task isSuccessful");
                            // Registrazione avvenuta con successo
                            Toast.makeText(getApplicationContext(), "Registrazione avvenuta con successo", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            startActivity(i);
                        } else {
                            Log.d("REGISTRAZIONE", "Tast NOT Succesfull");
                            // Errore nella registrazione
                            Toast.makeText(getApplicationContext(), "Errore nella registrazione", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    public void vaiLogin(View v){
        Intent i = new Intent(getApplicationContext(), Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }
}