package Storage;

import android.util.Log;

import com.example.outfitmakerfake.Entity.Utente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class UtenteDAO {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Utente utente;

    public UtenteDAO() {
    }

    public UtenteDAO(FirebaseAuth mAuth, FirebaseFirestore db) {
        this.mAuth = mAuth;
        this.db = db;
    }

    public Task<Boolean> creaUtente(String nome, String cognome, String email, String password, String telefono) {
        Log.d("UTENTE", "Entra in creaUtente");

        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("UTENTE", "Entro in onComplete()");
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if (currentUser != null) {
                                String uid = currentUser.getUid();
                                Log.d("UTENTE", "UID in creaUtenteFirestore: " + uid);
                                creaUtenteFirestore(uid, nome, cognome, email, password, telefono);
                                utente = new Utente(uid, nome, cognome, email, password, telefono);
                                taskCompletionSource.setResult(true);
                            } else {
                                Log.d("UTENTE", "Errore: currentUser è null");
                                taskCompletionSource.setResult(false);
                            }
                        } else {
                            Log.d("CREAUTENTE", "Errore durante la creazione dell'utente " + email + " " + password);
                            taskCompletionSource.setResult(false);
                        }
                        Exception exception = task.getException();
                        Log.d("UTENTE", "Errore in onComplete() " + exception);
                    }
                });

        return taskCompletionSource.getTask();
    }

    public void creaUtenteFirestore(String uid, String nome, String cognome, String email, String password, String telefono) {
        Log.d("UTENTE", "Entra in creaUtenteFirestore");
        Map<String, Object> utente = new HashMap<>();
        utente.put("uid", uid);
        utente.put("nome", nome);
        utente.put("cognome", cognome);
        utente.put("email", email);
        utente.put("password", password);
        utente.put("telefono", telefono);
        Log.d("UTENTE", "Legge i dati");

        db.collection("utenti")
                .add(utente)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("UTENTE", "DocumentSnapshot aggiunto con ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("UTENTE", "Errore durante l'aggiunta del documento + (" + e + ")");
                    }
                });
    }

    public Task<Boolean> effettuaLogin(String email, String password) {
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            taskCompletionSource.setResult(true);
                        } else {
                            taskCompletionSource.setResult(false);
                        }
                    }
                });

        return taskCompletionSource.getTask();
    }

    /*public void getUtente(Utente utente){
        this.utente = utente;
    }*/
}