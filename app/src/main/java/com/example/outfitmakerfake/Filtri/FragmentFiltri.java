package com.example.outfitmakerfake.Filtri;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.R;
import com.example.outfitmakerfake.Utility.NetworkUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import Storage.Armadio.ArmadioDAO;
import Storage.Armadio.ArmadioService;

public class FragmentFiltri extends Fragment {

    ArmadioService armadioService;

    RecyclerView recyclerView;
    ArrayList<Capo> capoArrayList;
    AdapterRicercaFiltri myAdapter;

    FirebaseFirestore db;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    GridLayout gridLayout;
    RadioGroup stagionalitaRG;
    RadioGroup tipologiaRG;
    Button btn_ricerca_filtri;

    String stagione_selezionata = "";
    String tipologia_selezionata = "";

    public FragmentFiltri() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frammento_filtri, container, false);

        gridLayout = v.findViewById(R.id.gridLayoutColoreFiltri);
        stagionalitaRG = v.findViewById(R.id.radioGroupStagFiltri);
        tipologiaRG = v.findViewById(R.id.radioGroupTipFiltri);
        btn_ricerca_filtri = v.findViewById(R.id.btn_filtri);

        recyclerView = v.findViewById(R.id.recycleListFiltri);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db = FirebaseFirestore.getInstance();
        capoArrayList = new ArrayList<Capo>();
        myAdapter = new AdapterRicercaFiltri(getContext(), capoArrayList);

        recyclerView.setAdapter(myAdapter);
        EventChangeListener();

        armadioService = new ArmadioService(new ArmadioDAO());

        btn_ricerca_filtri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!NetworkUtil.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), "Connessione Internet assente", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Prendo il valore dei colori
                int childCount = gridLayout.getChildCount();
                ArrayList<String> coloriSelezionati = new ArrayList<>();

                for (int i = 0; i < childCount; i++) {
                    View childView = gridLayout.getChildAt(i);

                    if (childView instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) childView;

                        if (checkBox.isChecked()) {
                            Log.d("INDUMENTO", "Entro nell'if per prendere i colori");

                            String color = checkBox.getText().toString();
                            coloriSelezionati.add(color);
                            Log.d("INDUMENTO", "Colore: " + color);
                        }
                    }
                }
                if (coloriSelezionati.isEmpty()) {
                    Log.d("INDUMENTO", "Errore colore");
                    Toast.makeText(getContext(), "Inserire il colore", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Prendo il valore di stagionalità
                int selectedRadioButtonIdStag = stagionalitaRG.getCheckedRadioButtonId();
                if (selectedRadioButtonIdStag != 1) {
                    RadioButton selectedRadioButtonStag = getView().findViewById(selectedRadioButtonIdStag);
                    stagione_selezionata = selectedRadioButtonStag.getText().toString();
                } else {
                    Toast.makeText(getContext(), "Inserire la Stagionalità", Toast.LENGTH_SHORT).show();
                    Log.d("INDUMENTO", "Errore stagione");
                    return;
                }

                //Prendo il valore di Tipologia
                int selectedRadioGroupIdTip = tipologiaRG.getCheckedRadioButtonId();
                if (selectedRadioGroupIdTip != 1) {
                    RadioButton selectedRadioGroupTip = getView().findViewById(selectedRadioGroupIdTip);
                    tipologia_selezionata = selectedRadioGroupTip.getText().toString();
                } else {
                    Toast.makeText(getContext(), "Inserire la Tipologia", Toast.LENGTH_SHORT).show();
                    Log.d("INDUMENTO", "Errore tipologia");
                    return;
                }

                armadioService.ricercaFiltri(coloriSelezionati, stagione_selezionata, tipologia_selezionata)
                        .addOnCompleteListener(new OnCompleteListener<ArrayList<Capo>>() {
                            @Override
                            public void onComplete(@NonNull Task<ArrayList<Capo>> task) {
                                if (task.isSuccessful()) {
                                    ArrayList<Capo> risultatoRicerca = task.getResult();
                                    if (risultatoRicerca != null && !risultatoRicerca.isEmpty()) {
                                        capoArrayList.clear();
                                        capoArrayList.addAll(risultatoRicerca);

                                        myAdapter.notifyDataSetChanged();
                                        Toast.makeText(getContext(), "Ricerca completata con successo", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Nessun capo trovato con i filtri selezionati", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Exception exception = task.getException();
                                    Toast.makeText(getContext(), "Errore durante la ricerca: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return v;
    }

    private void EventChangeListener() {
        // Ottieni l'ID dell'utente corrente
        String uid = mAuth.getCurrentUser().getUid();

        // Ottieni la reference alla tua collezione
        CollectionReference capiCollection = db.collection("capi");

        // Aggiungi l'ascoltatore per i cambiamenti
        capiCollection.whereEqualTo("userId", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "Listen failed.", e);
                            return;
                        }

                        // Se snapshot non è nullo, ci sono dati validi
                        if (snapshot != null) {
                            // Aggiorna il tuo ArrayList capoArrayList con i nuovi dati
                            capoArrayList.clear();
                            for (QueryDocumentSnapshot document : snapshot) {
                                // Converti il documento in un oggetto Capo e aggiungilo all'ArrayList
                                Capo capo = document.toObject(Capo.class);
                                capoArrayList.add(capo);
                            }

                            // Notifica all'Adapter che i dati sono cambiati
                            myAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("TAG", "Current data: null");
                        }
                    }
                });
    }
}