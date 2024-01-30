package Storage.Armadio;

import android.util.Log;

import com.example.outfitmakerfake.Entity.Capo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ArmadioDAO {

    String idArmadio = "";
    String idArchivio = "";
    String uid;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    public ArmadioDAO() {

    }

    public ArmadioDAO(FirebaseAuth mAuth, FirebaseFirestore db) {
        this.mAuth = mAuth;
        this.db = db;
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

    public Task<Boolean> aggiungiCapo(String nomeBrand, List<String> colori, String tipologia, String stagionalita, String occasione) {
        if (currentUser != null) {
            // L'utente è attualmente autenticato
            uid = currentUser.getUid();
            // Riferimento al documento utente nella collezione "utenti" basato sull'UID
            DocumentReference utenteRef = FirebaseFirestore.getInstance().collection("utenti").document(uid);

            utenteRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                idArmadio = documentSnapshot.getString("idArmadio");
                                if (idArmadio != null) {
                                    // Hai ottenuto con successo l'idArmadio associato all'utente
                                    Log.d("INDUMENTO", "idArmadio: " + idArmadio);
                                } else {
                                    // "idArmadio" non è presente nel documento o è null
                                    Log.d("INDUMENTO", "idArmadio non disponibile per l'utente");
                                }
                            } else {
                                // Il documento utente non esiste
                                Log.d("INDUMENTO", "Documento utente non trovato");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@androidx.annotation.NonNull Exception e) {
                            // Gestisci l'errore durante la lettura del documento
                            Log.e("ERRORE", "Errore durante la lettura del documento utente", e);
                        }
                    });
        } else {
            // Nessun utente autenticato al momento
            Log.d("INDUMENTO", "Nessun utente attualmente autenticato");
        }

        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        // Genera un nuovo ID univoco per il capo
        String idCapo = generateUniqueIndumentoId();

        // Creare un nuovo documento per il capo nella raccolta "capi"
        Map<String, Object> capoMap = new HashMap<>();
        capoMap.put("id_indumento", idCapo);
        capoMap.put("nome_brand", nomeBrand);
        capoMap.put("colori", colori);
        capoMap.put("tipologia", tipologia);
        capoMap.put("stagionalita", stagionalita);
        capoMap.put("occasione", occasione);
        capoMap.put("uid", uid);
        capoMap.put("isScelto", false);

        // Nota: Non è possibile memorizzare direttamente un'immagine in Firestore, quindi potresti doverla salvare separatamente, ad esempio su Firebase Storage, e memorizzare solo il percorso o l'URL dell'immagine nel documento.

        db.collection("capi")
                .document(idCapo)
                .set(capoMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        taskCompletionSource.setResult(true);
                    } else {
                        Log.d("CAZZOCULO", "Errore durante l'aggiunta del capo al documento: " + task.getException());
                        taskCompletionSource.setResult(false);
                    }
                });

        return taskCompletionSource.getTask();
    }

    public Task<Boolean> modificaCapo(String nomeBrand, List<String> colori, String tipologia, String stagionalita, String occasione) {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String uid = user.getUid();

            FirebaseFirestore.getInstance().collection("capi")
                    .whereEqualTo("uid", uid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                if(!task.getResult().isEmpty()) {
                                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                    DocumentReference userRef = document.getReference();

                                    Map<String, Object> nuoviDatiCapo  = new HashMap<>();
                                    nuoviDatiCapo.put("nome_brand", nomeBrand);
                                    nuoviDatiCapo.put("colori", colori);
                                    nuoviDatiCapo.put("tipologia", tipologia);
                                    nuoviDatiCapo.put("stagionalita", stagionalita);
                                    nuoviDatiCapo.put("occasione", occasione);

                                    userRef.update(nuoviDatiCapo)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    taskCompletionSource.setResult(true);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("MODIFICA", "Errore durante l'aggiornamento dei dati utente", e);
                                                    taskCompletionSource.setResult(false);
                                                }
                                            });
                                }

                            }
                        }
                    });
        }
        return taskCompletionSource.getTask();
    }


    public Task<ArrayList<Capo>> ricercaFiltri(ArrayList<String> colori, String stagionalita, String tipologia) {
        final TaskCompletionSource<ArrayList<Capo>> taskCompletionSource = new TaskCompletionSource<>();

        FirebaseUser user = mAuth.getCurrentUser();
        ArrayList<Capo> capi = new ArrayList<>();

        if (user != null) {
            String uid = user.getUid();

            db.collection("capi")
                    .whereEqualTo("uid", uid)
                    .whereArrayContainsAny("colori", colori)
                    .whereEqualTo("stagionalita", stagionalita)
                    .whereEqualTo("tipologia", tipologia)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                            try {
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task.getResult();

                                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                        for (QueryDocumentSnapshot document : querySnapshot) {
                                            String nome_brand = document.getString("nome_brand");
                                            List<String> colori = (List<String>) document.get("colori");
                                            String tipologia = document.getString("tipologia");
                                            String stagionalita = document.getString("stagionalita");
                                            String occasione = document.getString("occasione");

                                            Capo capo = new Capo(nome_brand, colori, tipologia, stagionalita, occasione);
                                            capi.add(capo);
                                        }
                                    }
                                } else {
                                    throw task.getException();
                                }

                                taskCompletionSource.setResult(capi);
                            } catch (Exception e) {
                                taskCompletionSource.setException(e);
                            }
                        }
                    });
        } else {
            taskCompletionSource.setException(new Exception("Utente non valido"));
        }

        return taskCompletionSource.getTask();
    }

    public Task<Boolean> resettaSceltaCapi() {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            FirebaseFirestore.getInstance().collection("capi")
                    .whereEqualTo("uid", uid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (!task.getResult().isEmpty()) {
                                    List<DocumentSnapshot> documents = task.getResult().getDocuments();

                                    // Itera su tutti i documenti e imposta "isScelto" a false
                                    for (DocumentSnapshot document : documents) {
                                        DocumentReference userRef = document.getReference();

                                        Map<String, Object> nuoviDatiCapo = new HashMap<>();
                                        nuoviDatiCapo.put("isScelto", false);

                                        userRef.update(nuoviDatiCapo)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        // Puoi aggiungere del log o gestire altri casi di successo qui
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e("INSERTTT", "Errore durante l'aggiornamento dei dati utente", e);
                                                        taskCompletionSource.setResult(false);
                                                    }
                                                });
                                    }
                                    taskCompletionSource.setResult(true);
                                } else {
                                    // Nessun documento trovato
                                    taskCompletionSource.setResult(false);
                                }
                            }
                        }
                    });
        } else {
            taskCompletionSource.setResult(false);
        }
        return taskCompletionSource.getTask();
    }

    public Task<Boolean> creaOutfit(ArrayList<Capo> lista_capi){
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        if(currentUser != null){
            uid = currentUser.getUid();

            DocumentReference utenteRef = db.collection("utenti").document(uid);

            utenteRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                idArchivio = documentSnapshot.getString("idArchivio");
                            }
                        }
                    });

            String id_outfit = generateUniqueOutfitId();

           Map<String, Object> outfit_new = new HashMap<>();
           outfit_new.put("uid", uid);
           outfit_new.put("id_outfit", id_outfit);
           outfit_new.put("id_archivio", idArchivio);
           outfit_new.put("lista_capi", lista_capi);

           db.collection("outfit")
                   .document(id_outfit)
                   .set(outfit_new)
                   .addOnCompleteListener(task -> {
                       if (task.isSuccessful()) {
                           taskCompletionSource.setResult(true);
                       } else {
                           Log.d("INSERTTT", "Errore durante l'aggiunta del capo al documento: " + task.getException());
                           taskCompletionSource.setResult(false);
                       }
                   });
        }

        return taskCompletionSource.getTask();
    }

    public Task<Boolean> capiMinimiTop(String uid){
        Query shirt_felpa_query = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereIn("tipologia", Arrays.asList("T-shirt", "Felpa"))
                .limit(1);

        Task<QuerySnapshot> tShirtFelpaTask = shirt_felpa_query.get();

        return tShirtFelpaTask.continueWith(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot tShirtFelpaSnapshot = task.getResult();
                return tShirtFelpaSnapshot != null && !tShirtFelpaSnapshot.isEmpty();
            } else {
                // Errore nella query
                Exception e = task.getException();
                if (e != null) {
                    // Gestisci l'errore qui
                }
                return false;
            }
        });
    }

    public Task<Boolean> capiMinimiCenter(String uid){
        Query shirt_felpa_query = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereIn("tipologia", Arrays.asList("Pantalone", "Jeans", "Gonna", "Pantalone corto"))
                .limit(1);

        Task<QuerySnapshot> tShirtFelpaTask = shirt_felpa_query.get();

        return tShirtFelpaTask.continueWith(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot tShirtFelpaSnapshot = task.getResult();
                return tShirtFelpaSnapshot != null && !tShirtFelpaSnapshot.isEmpty();
            } else {
                // Errore nella query
                Exception e = task.getException();
                if (e != null) {
                    // Gestisci l'errore qui
                }
                return false;
            }
        });
    }

    public Task<Boolean> capiMinimiBottom(String uid){
        Query shirt_felpa_query = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereIn("tipologia", Arrays.asList("Scarpe"))
                .limit(1);

        Task<QuerySnapshot> tShirtFelpaTask = shirt_felpa_query.get();

        return tShirtFelpaTask.continueWith(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot tShirtFelpaSnapshot = task.getResult();
                return tShirtFelpaSnapshot != null && !tShirtFelpaSnapshot.isEmpty();
            } else {
                // Errore nella query
                Exception e = task.getException();
                if (e != null) {
                    // Gestisci l'errore qui
                }
                return false;
            }
        });
    }

    public Task<ArrayList<Capo>> generaOutfit(String stagionalita) {
        ArrayList<Capo> lista_capi = new ArrayList<>();

        ArrayList<Capo> buffer_top = new ArrayList<>();
        ArrayList<Capo> buffer_center = new ArrayList<>();
        ArrayList<Capo> buffer_bottom = new ArrayList<>();

        String uid = FirebaseAuth.getInstance().getUid();

        TaskCompletionSource<ArrayList<Capo>> taskCompletionSource = new TaskCompletionSource<>();

        // T-SHIRT
        Query query_shirt = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereEqualTo("stagionalita", stagionalita)
                .whereEqualTo("tipologia", "T-shirt");
        Task<QuerySnapshot> task_shirt = query_shirt.get();
        task_shirt.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Capo capo = document.toObject(Capo.class);
                    buffer_top.add(capo);
                }
            } else {
                Log.d("GENERATE_OUTFIT", "Error getting documents: ", task.getException());
            }
        });

        // FELPE
        Query query_felpa = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereEqualTo("stagionalita", stagionalita)
                .whereEqualTo("tipologia", "Felpa");
        Task<QuerySnapshot> task_felpa = query_felpa.get();
        task_felpa.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Capo capo = document.toObject(Capo.class);
                    buffer_top.add(capo);
                }
            } else {
                Log.d("GENERATE_OUTFIT", "Error getting documents: ", task.getException());
            }
        });

        // CAMICIA
        Query query_camicia = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereEqualTo("stagionalita", stagionalita)
                .whereEqualTo("tipologia", "Camicia");
        Task<QuerySnapshot> task_camicia = query_camicia.get();
        task_camicia.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Capo capo = document.toObject(Capo.class);
                    buffer_top.add(capo);
                }
            } else {
                Log.d("GENERATE_OUTFIT", "Error getting documents: ", task.getException());
            }
        });

        // MAGLIA LUNGA
        Query query_maglia_lunga = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereEqualTo("stagionalita", stagionalita)
                .whereEqualTo("tipologia", "Maglia lunga");
        Task<QuerySnapshot> task_maglia_lunga = query_maglia_lunga.get();
        task_maglia_lunga.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Capo capo = document.toObject(Capo.class);
                    buffer_top.add(capo);
                }
            } else {
                Log.d("GENERATE_OUTFIT", "Error getting documents: ", task.getException());
            }
        });

        // CENTER
        // JEANS
        Query query_jeans = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereEqualTo("stagionalita", stagionalita)
                .whereEqualTo("tipologia", "Jeans");
        Task<QuerySnapshot> task_jeans = query_jeans.get();
        task_jeans.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Capo capo = document.toObject(Capo.class);
                    buffer_center.add(capo);
                }
            } else {
                Log.d("GENERATE_OUTFIT", "Error getting documents: ", task.getException());
            }
        });

        // PANTALONE
        Query query_pantalone = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereEqualTo("stagionalita", stagionalita)
                .whereEqualTo("tipologia", "Pantalone");
        Task<QuerySnapshot> task_pantalone = query_pantalone.get();
        task_pantalone.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Capo capo = document.toObject(Capo.class);
                    buffer_center.add(capo);
                }
            } else {
                Log.d("GENERATE_OUTFIT", "Error getting documents: ", task.getException());
            }
        });

        //PANTALONE CORTO
        Query query_pantalone_corto = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereEqualTo("stagionalita", stagionalita)
                .whereEqualTo("tipologia", "Pantalone corto");
        Task<QuerySnapshot> task_pantalone_corto = query_pantalone_corto.get();
        task_pantalone_corto.addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Capo capo = document.toObject(Capo.class);
                    buffer_center.add(capo);
                }
            } else {
                Log.d("GENERATE_OUTFIT", "Error getting documents: ", task.getException());
            }
        });

        //GONNA
        Query query_gonna = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereEqualTo("stagionalita", stagionalita)
                .whereEqualTo("tipologia", "Gonna");
        Task<QuerySnapshot> task_gonna = query_gonna.get();
        task_gonna.addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Capo capo = document.toObject(Capo.class);
                    buffer_center.add(capo);
                }
            } else {
                Log.d("GENERATE_OUTFIT", "Error getting documents: ", task.getException());
            }
        });

        //SCARPE
        Query query_scarpe = db.collection("capi")
                .whereEqualTo("uid", uid)
                .whereEqualTo("stagionalita", stagionalita)
                .whereEqualTo("tipologia", "Scarpe");
        Task<QuerySnapshot> task_scarpe = query_scarpe.get();
        task_scarpe.addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Capo capo = document.toObject(Capo.class);
                    buffer_bottom.add(capo);
                }
            } else {
                Log.d("GENERATE_OUTFIT", "Error getting documents: ", task.getException());
            }
        });


        Tasks.whenAllComplete(task_shirt, task_felpa, task_camicia, task_maglia_lunga, task_jeans, task_pantalone, task_pantalone_corto, task_gonna)
                .addOnCompleteListener(allTasks -> {
                    if (allTasks.isSuccessful()) {
                        if (!buffer_top.isEmpty() && !buffer_center.isEmpty() && !buffer_bottom.isEmpty()) {
                            Random random = new Random();

                            int capoRandomTopPosition = random.nextInt(buffer_top.size());
                            Capo capoTopCasuale = buffer_top.get(capoRandomTopPosition);

                            int capoRandomCenterPosition = random.nextInt(buffer_center.size());
                            Capo capoCenterCasuale = buffer_center.get(capoRandomCenterPosition);

                            int capoRandomBottomPosition = random.nextInt(buffer_bottom.size());
                            Capo capoBottomCasuale = buffer_bottom.get(capoRandomBottomPosition);

                            lista_capi.add(capoTopCasuale);
                            lista_capi.add(capoCenterCasuale);
                            lista_capi.add(capoBottomCasuale);
                            taskCompletionSource.setResult(lista_capi);
                        } else {
                            taskCompletionSource.setResult(new ArrayList<>());
                        }
                    } else {
                        Log.d("GENNN", "Error in Tasks.whenAllComplete: " + allTasks.getException());
                        taskCompletionSource.setResult(new ArrayList<>());
                    }
                });

        return taskCompletionSource.getTask();
    }

    public String generateUniqueArmadioId() {
        return UUID.randomUUID().toString();
    }

    public String generateUniqueIndumentoId() {
        return UUID.randomUUID().toString();
    }

    public String generateUniqueOutfitId(){return UUID.randomUUID().toString();}
}