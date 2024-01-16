package com.example.outfitmakerfake;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class AreaUtente extends AppCompatActivity {

    public String nome, cognome, email, telefono;
    TextView nomeTV, cognomeTV, emailTV, telefonoTV;
    FirebaseFirestore db;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("AREAUTENTELOG", "onCreate AreaUtente");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_utente);

        nomeTV = findViewById(R.id.nomeAU);
        cognomeTV = findViewById(R.id.cognomeAU);
        emailTV = findViewById(R.id.emailAU);
        telefonoTV = findViewById(R.id.telefonoAU);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();
            db = FirebaseFirestore.getInstance();
            Log.d("AREAUTENTELOG", uid);

            ottieniDatiUtente();
        }
    }

    /*@Override
    public void onStart(){
        super.onStart();
        ottieniDatiUtente();
    }*/

    public void ottieniDatiUtente(){
        Log.d("AREAUTENTELOG", "Entra in ottieniDatiUtente");
        // Utilizza il campo uid per cercare l'utente
        db.collection("utenti")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("AREAUTENTELOG", "entrato in onComplete");
                        if (task.isSuccessful()) {
                            Log.d("AREAUTENTELOG", "entrato in isSuccessful");
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                // Dovrebbe esserci un solo documento, ma è comunque possibile gestire più risultati
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                Log.d("AREAUTENTELOG", "Il documento esiste");
                                nome = document.getString("nome");
                                cognome = document.getString("cognome");
                                email = document.getString("email");
                                telefono = document.getString("telefono");

                                Log.d("AREAUTENTELOG", "Nome: " + nome);
                                // Ora che ho i dati, posso chiamare settaDatiUtente per aggiornare le TextView
                                settaDatiUtente();
                            } else {
                                Log.w("AREAUTENTELOG", "Documento dell'utente corrente non trovato");
                            }
                        } else {
                            Log.w("AREAUTENTELOG", "Errore durante il recupero del documento dell'utente corrente" + task.getException());
                        }
                    }
                });
    }

    public void settaDatiUtente(){
        Log.d("AREAUTENTELOG", "Entra in settaDatiUtente");
        nomeTV.setText("Nome: " + nome);
        cognomeTV.setText("Cognome: " + cognome);
        emailTV.setText("Email: " + email);
        telefonoTV.setText("Telefono: " + telefono);
    }

    public void disconnessioneClicked(View v){
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
        finish();
    }
}