package com.example.outfitmakerfake.Utility.CreazioneOutfit;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputContentInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.Entity.Outfit;
import com.example.outfitmakerfake.R;
import com.example.outfitmakerfake.RecycleViewCreazioneInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Storage.Armadio.ArmadioDAO;
import Storage.Armadio.ArmadioService;

public class AdapterCreazioneOutfit extends RecyclerView.Adapter<AdapterCreazioneOutfit.SupportCreazione> {
    
    Context context;
    public ArrayList<Capo> capoArrayList;
    String idCapo;
    FirebaseFirestore db;
    FirebaseAuth auth;
    ArrayList<Capo> outfit = new ArrayList<>();
    ArmadioService armadioService = new ArmadioService(new ArmadioDAO());



    public AdapterCreazioneOutfit(Context context, ArrayList<Capo> capoArrayList) {
        this.context = context;
        this.capoArrayList = capoArrayList;
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
    
    @Override
    public AdapterCreazioneOutfit.@NonNull SupportCreazione onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.singolo_elemento_creazione_outfit, parent, false);
        return new SupportCreazione(v);
    }
    
    @Override
    public void onBindViewHolder(AdapterCreazioneOutfit.@NonNull SupportCreazione supportCreazione, int position){
        Capo capo = capoArrayList.get(position);
        idCapo = capoArrayList.get(position).getId_indumento();
        Log.d("TAG", "idCapo: " + idCapo);

        supportCreazione.nome.setText(capo.getNome_brand());
        supportCreazione.tipologia.setText(capo.getTipologia());
        supportCreazione.stagionalita.setText(capo.getStagionalita());
        supportCreazione.occasione.setText(capo.getOccasione());

        // Visualizza la lista di colori come una singola stringa separata da virgole
        String coloriString = TextUtils.join(", ", capo.getColori());
        supportCreazione.listaColori.setText(coloriString + " ");

        if(capo.getTipologia().equals("Felpa")){
            supportCreazione.immagine.setImageResource(R.drawable.felpa);
        } else if(capo.getTipologia().equals("T-shirt") && capo.getOccasione().equals("Elegante")){
            supportCreazione.immagine.setImageResource(R.drawable.polo);
        } else if(capo.getTipologia().equals("T-shirt")){
            supportCreazione.immagine.setImageResource(R.drawable.t_shirt);
        } else if(capo.getTipologia().equals("Maglia lunga") && capo.getOccasione().equals("Elegante")){
            supportCreazione.immagine.setImageResource(R.drawable.maglia_lunga_elegante);
        } else if(capo.getTipologia().equals("Maglia lunga")){
            supportCreazione.immagine.setImageResource(R.drawable.maglia_lunga);
        } else if(capo.getTipologia().equals("Giacca") && capo.getOccasione().equals("Elegante")){
            supportCreazione.immagine.setImageResource(R.drawable.giacca_elegante);
        } else if(capo.getTipologia().equals("Giacca")){
            supportCreazione.immagine.setImageResource(R.drawable.giacca);
        } else if(capo.getTipologia().equals("Camicia")){
            supportCreazione.immagine.setImageResource(R.drawable.camicia);
        } else if(capo.getTipologia().equals("Cappotto")){
            supportCreazione.immagine.setImageResource(R.drawable.cappotto);
        } else if(capo.getTipologia().equals("Jeans") || capo.getTipologia().equals("Pantalone")){
            supportCreazione.immagine.setImageResource(R.drawable.jeans);
        } else if(capo.getTipologia().equals("Gonna")){
            supportCreazione.immagine.setImageResource(R.drawable.gonna);
        } else if(capo.getTipologia().equals("Vestito corto")){
            supportCreazione.immagine.setImageResource(R.drawable.vestito_corto);
        } else if(capo.getTipologia().equals("Vestito lungo")){
            supportCreazione.immagine.setImageResource(R.drawable.vestito_lungo);
        } else if(capo.getTipologia().equals("Scarpe")){
            supportCreazione.immagine.setImageResource(R.drawable.scarpa_casual);
        }

        /*supportCreazione.isScelto.setText("isScelto: " + capo.getScelto());

        supportCreazione.btn_inserisci_capo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_Capo = capo.getId_indumento();

                supportCreazione.progressBar.setVisibility(View.VISIBLE);
                if (!capo.getScelto()) {
                    armadioService.cambiaSceltoTrue(id_Capo).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<Boolean> task) {
                            Log.d("INSERTTT", "task = " + task.getResult());
                            Log.d("INSERTTT", "entra in onClick");
                            if (task.isSuccessful()) {
                                boolean modificaRiuscita = task.getResult();
                                Log.d("INSERTTT", "task = " + modificaRiuscita);
                                if (modificaRiuscita) {
                                    Log.d("INSERTTT", "entra in modificaRiuscita");
                                    supportCreazione.progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(v.getContext(), "Dati modificati con successo in True", Toast.LENGTH_SHORT).show();
                                    supportCreazione.linearLayout.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.violet));                                    notifyDataSetChanged();
                                } else {
                                    supportCreazione.progressBar.setVisibility(View.INVISIBLE);
                                    Log.d("MODIFICA", "entra in modificaErrore");
                                    Toast.makeText(v.getContext(), "Errore durante la modifica dei dati", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                supportCreazione.progressBar.setVisibility(View.INVISIBLE);
                                Exception exception = task.getException();
                                Log.d("INSERTTT", String.valueOf(task.getException()));
                                Toast.makeText(v.getContext(), "Errore: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    armadioService.cambiaSceltoFalse(id_Capo).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<Boolean> task) {
                            Log.d("INSERTTT", "task = " + task.getResult());
                            Log.d("INSERTTT", "entra in onClick");
                            if (task.isSuccessful()) {
                                boolean modificaRiuscita = task.getResult();
                                Log.d("INSERTTT", "task = " + modificaRiuscita);
                                if (modificaRiuscita) {
                                    Log.d("INSERTTT", "entra in modificaRiuscita");
                                    supportCreazione.progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(v.getContext(), "Dati modificati con success in False", Toast.LENGTH_SHORT).show();
                                    supportCreazione.linearLayout.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.white));                                    notifyDataSetChanged();
                                } else {
                                    supportCreazione.progressBar.setVisibility(View.INVISIBLE);
                                    Log.d("MODIFICA", "entra in modificaErrore");
                                    Toast.makeText(v.getContext(), "Errore durante la modifica dei dati", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                supportCreazione.progressBar.setVisibility(View.INVISIBLE);
                                Exception exception = task.getException();
                                Log.d("INSERTTT", String.valueOf(task.getException()));
                                Toast.makeText(v.getContext(), "Errore: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });*/

        supportCreazione.btn_inserisci_capo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = supportCreazione.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    Capo capo = capoArrayList.get(clickedPosition);

                    if (!outfit.contains(capo)) {
                        outfit.add(capo);
                        Toast.makeText(context, "Capo inserito nell'array", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Il capo è già presente nell'array", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        supportCreazione.btn_crea_outfit_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int somma_shirt=0, somma_maglia_lunga=0, somma_felpe=0, somma_giacca=0, somma_cappotto=0, somma_camicia=0, somma_jeans=0, somma_pantalone=0,
                        somma_pantalone_corto = 0, somma_gonna=0, somma_vestito_lungo=0, somma_vestito_corto=0, somma_scarpe=0;
                for(Capo capo: outfit){
                    if(capo.getTipologia().equals("T-shirt")){
                        somma_shirt++;
                        Log.d("INSERTTT", "T-shirt: " + somma_shirt);
                    } else if(capo.getTipologia().equals("Felpa")){
                        somma_felpe++;
                        Log.d("INSERTTT", "Felpe: " + somma_felpe);
                    } else if(capo.getTipologia().equals("Maglia lunga")){
                        somma_maglia_lunga++;
                        Log.d("INSERTTT", "Maglie lunghe: " + somma_maglia_lunga);
                    } else if(capo.getTipologia().equals("Camicia")){
                        somma_camicia++;
                        Log.d("INSERTTT", "Camicie: " + somma_camicia);
                    } else if(capo.getTipologia().equals("Giacca")){
                        somma_giacca++;
                        Log.d("INSERTTT", "Giacche: " + somma_giacca);
                    } else if(capo.getTipologia().equals("Cappotto")){
                        somma_cappotto++;
                        Log.d("INSERTTT", "Cappotto: : " + somma_cappotto);
                    } else if(capo.getTipologia().equals("Jeans")){
                        somma_jeans++;
                        Log.d("INSERTTT", "Jeans:  " + somma_jeans);
                    } else if(capo.getTipologia().equals("Pantalone")){
                        somma_pantalone++;
                        Log.d("INSERTTT", "Pantalone: " + somma_pantalone);
                    } else if(capo.getTipologia().equals("Gonna")){
                        somma_gonna++;
                        Log.d("INSERTTT", "Gonna: " + somma_gonna);
                    } else if(capo.getTipologia().equals("Pantalone corto")){
                        somma_pantalone_corto++;
                        Log.d("INSERTTT", "Cappotto: : " + somma_pantalone_corto);
                    } else if(capo.getTipologia().equals("Vestito corto")){
                        somma_vestito_corto++;
                        Log.d("INSERTTT", "Vestito corto: " + somma_vestito_corto);
                    } else if(capo.getTipologia().equals("Vestito lungo")){
                        somma_vestito_lungo++;
                        Log.d("INSERTTT", "Vestito lungo: " + somma_vestito_lungo);
                    } else if(capo.getTipologia().equals("Scarpe")){
                        somma_scarpe++;
                        Log.d("INSERTTT", "Scarpe: " + somma_scarpe);
                    }
                }

                if(somma_shirt<=1 && somma_felpe<=1 && somma_maglia_lunga<=1 && somma_camicia<=1 && somma_giacca<=1 && somma_cappotto<=1 && somma_jeans<=1
                        && somma_pantalone<=1 && somma_gonna<=1 && somma_vestito_corto<=1 && somma_vestito_lungo<=1 && somma_scarpe<=1){
                    armadioService.creaOutfit(outfit).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<Boolean> task) {
                            supportCreazione.progressBar_outfit.setVisibility(View.VISIBLE);
                            if(task.isSuccessful()){
                                boolean creazione_outfit = task.getResult();
                                Log.d("INSERTTT", "creazione_outfit = " + creazione_outfit);

                                if(creazione_outfit){
                                    Toast.makeText(v.getContext(), "Outfit creato con successo", Toast.LENGTH_SHORT).show();

                                } else {
                                    Log.d("INSERTTT", "Entra in else di creazioneIndumento");
                                    Toast.makeText(v.getContext(), "Errore nella creazione dell'Indumento", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Exception exception = task.getException();
                                Log.d("INDUMENTO", "else in onComplete exception: " + exception);
                                Toast.makeText(v.getContext(), "Errore: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            supportCreazione.progressBar_outfit.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    Toast.makeText(v.getContext(), "Numero di capi errato", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return capoArrayList.size();
    }

    public static class SupportCreazione extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        Button btn_inserisci_capo;
        Button btn_crea_outfit_f;
        ProgressBar progressBar;
        ProgressBar progressBar_outfit; //progressBar nel singolo elemento quando clicco per creare outfit
        TextView nome, listaColori, tipologia, stagionalita, occasione;
        ImageView immagine;

        public SupportCreazione(@NonNull View itemView){
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linear_layout_creazione);
            immagine = itemView.findViewById(R.id.immagine_capo_creazione);
            nome = itemView.findViewById(R.id.nome_capo_creazione);
            listaColori = itemView.findViewById(R.id.colori_capo_creazione);
            tipologia = itemView.findViewById(R.id.tipologia_capo_creazione);
            stagionalita = itemView.findViewById(R.id.stagionalità_capo_creazione);
            occasione = itemView.findViewById(R.id.occasione_capo_creazione);
            btn_inserisci_capo = itemView.findViewById(R.id.btn_inserisci_capo);
            progressBar = itemView.findViewById(R.id.progress_bar_scelta);
            btn_crea_outfit_f = itemView.findViewById(R.id.btn_crea_outfit_f);
            progressBar_outfit = itemView.findViewById(R.id.progressBar_outfit);
        }
    }
    
    
}
