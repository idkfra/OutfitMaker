package Storage;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class AreaUtenteDAO {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;

    public AreaUtenteDAO() {
    }

    public AreaUtenteDAO(FirebaseAuth mAuth, FirebaseFirestore db) {
        this.mAuth = mAuth;
        this.db = db;
    }

    public Task<Boolean> ottieniDatiUtente(String uid, String nome, String cognome, String email, String telefono) {
        Log.d("AREAUTENTELOG", "Entra in ottieniDatiUtente");

        // Crea una nuova istanza di TaskCompletionSource
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

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

                                // Esegui l'operazione desiderata con i dati
                                // ...

                                // Completa il Task con successo
                                taskCompletionSource.setResult(true);
                            } else {
                                Log.w("AREAUTENTELOG", "Documento dell'utente corrente non trovato");
                                // Completa il Task con fallimento
                                taskCompletionSource.setResult(false);
                            }
                        } else {
                            Log.w("AREAUTENTELOG", "Errore durante il recupero del documento dell'utente corrente" + task.getException());
                            // Completa il Task con fallimento
                            taskCompletionSource.setResult(false);
                        }
                    }
                });

        return taskCompletionSource.getTask();
    }
}