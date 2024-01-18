package Storage;

import android.util.Log;

import com.example.outfitmakerfake.Entity.Armadio;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArmadioDAO {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Armadio armadio;

    public ArmadioDAO(){

    }

    public ArmadioDAO(FirebaseAuth mAuth, FirebaseFirestore db) {
        this.mAuth = mAuth;
        this.db = db;
    }

    public String generateUniqueArmadioId() {
        return UUID.randomUUID().toString();
    }


    public void creaArmadioFirestore(String idArmadio) {
        Map<String, Object> armadio = new HashMap<>();
        armadio.put("idArmadio", idArmadio);
        armadio.put("listaCapi", new ArrayList<>()); // Inizializza la lista dei capi come vuota

        db.collection("armadi")
                .document(idArmadio)
                .set(armadio)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ARMADIO", "Documento Armadio aggiunto con successo");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ARMADIO", "Errore durante l'aggiunta del documento Armadio + (" + e + ")");
                    }
                });
    }
}
