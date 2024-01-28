package Storage.Archivio;

import android.util.Log;

import com.example.outfitmakerfake.Entity.Outfit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArchivioDAO {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String uid;
    FirebaseUser currentUser = mAuth.getCurrentUser();

    public ArchivioDAO() {
    }

    public ArchivioDAO(FirebaseAuth mAuth, FirebaseFirestore db) {
        this.mAuth = mAuth;
        this.db = db;
    }

    public void creaArchivioFirestore(String idArchivio, String uid){

        Map<String, Object> archivio = new HashMap<>();
        archivio.put("idArchivio", idArchivio);
        archivio.put("uid", uid);
        archivio.put("lista_outfit", new ArrayList<>());

        db.collection("archivio")
                .document(idArchivio)
                .set(archivio)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("AAA", "Documento Armadio aggiunto con successo");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("AAA", "Errore durante l'aggiunta del documento Armadio + (" + e + ")");
                    }
                });
    }

    public String generateUniqueArchivioId() {
        return UUID.randomUUID().toString();
    }
}
