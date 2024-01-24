package com.example.outfitmakerfake.Utility.Filtri;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class AdapterRicercaFiltri extends RecyclerView.Adapter<AdapterRicercaFiltri.Support> {

    Context context;
    ArrayList<Capo> capoArrayListFilter;
    String idCapo;
    FirebaseFirestore db;
    FirebaseAuth auth;

    public AdapterRicercaFiltri(Context context, ArrayList<Capo> capoArrayListFilter) {
        this.context = context;
        this.capoArrayListFilter = capoArrayListFilter;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public AdapterRicercaFiltri.@NonNull Support onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.singolo_elemento_filtro, parent, false);

        return new Support(v);
    }

    @Override
    public void onBindViewHolder(AdapterRicercaFiltri.@NonNull Support support, int position) {
        Capo capo = capoArrayListFilter.get(position);
        idCapo = capoArrayListFilter.get(position).getId_indumento();

        support.nome.setText(capo.getNome_brand());
        support.tipologia.setText(capo.getTipologia());
        support.stagionalita.setText(capo.getStagionalita());
        support.occasione.setText(capo.getOccasione());

        String coloriString = TextUtils.join(", ", capo.getColori());
        support.listaColori.setText(coloriString + " ");

        if(capo.getTipologia().equals("Felpa")){
            support.immagine.setImageResource(R.drawable.felpa);
        } else if(capo.getTipologia().equals("T-shirt") && capo.getOccasione().equals("Elegante")){
            support.immagine.setImageResource(R.drawable.polo);
        } else if(capo.getTipologia().equals("T-shirt")){
            support.immagine.setImageResource(R.drawable.t_shirt);
        } else if(capo.getTipologia().equals("Maglia lunga") && capo.getOccasione().equals("Elegante")){
            support.immagine.setImageResource(R.drawable.maglia_lunga_elegante);
        } else if(capo.getTipologia().equals("Maglia lunga")){
            support.immagine.setImageResource(R.drawable.maglia_lunga);
        } else if(capo.getTipologia().equals("Giacca") && capo.getOccasione().equals("Elegante")){
            support.immagine.setImageResource(R.drawable.giacca_elegante);
        } else if(capo.getTipologia().equals("Giacca")){
            support.immagine.setImageResource(R.drawable.giacca);
        } else if(capo.getTipologia().equals("Camicia")){
            support.immagine.setImageResource(R.drawable.camicia);
        } else if(capo.getTipologia().equals("Cappotto")){
            support.immagine.setImageResource(R.drawable.cappotto);
        } else if(capo.getTipologia().equals("Jeans") || capo.getTipologia().equals("Pantalone")){
            support.immagine.setImageResource(R.drawable.jeans);
        } else if(capo.getTipologia().equals("Gonna")){
            support.immagine.setImageResource(R.drawable.gonna);
        } else if(capo.getTipologia().equals("Vestito corto")){
            support.immagine.setImageResource(R.drawable.vestito_corto);
        } else if(capo.getTipologia().equals("Vestito lungo")){
            support.immagine.setImageResource(R.drawable.vestito_lungo);
        } else if(capo.getTipologia().equals("Scarpe")){
            support.immagine.setImageResource(R.drawable.scarpa_casual);
        }
        support.immagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "id: " + capoArrayListFilter.get(position).getId_indumento(), Toast.LENGTH_SHORT).show();
            }
        });

        support.btn_elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Conferma eliminazione");
                builder.setMessage("Sei sicuro di voler eliminare questo indumento?");

                builder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int currentPosition = support.getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION) {
                            db.collection("capi")
                                    .document(capoArrayListFilter.get(currentPosition).getId_indumento())
                                    .delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Rimuove l'elemento dalla lista utilizzando la posizione corrente
                                                capoArrayListFilter.remove(currentPosition);
                                                // Notifica all'Adapter che i dati sono stati modificati
                                                notifyItemRemoved(currentPosition);
                                                Toast.makeText(context, "Indumento cancellato", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(context, "Errore: " + task.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });

                builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount(){
        return capoArrayListFilter.size();
    }

    public static class Support extends RecyclerView.ViewHolder{

        TextView nome, listaColori, tipologia, stagionalita, occasione;
        ImageView immagine, btn_elimina;

        public Support(@NonNull View itemView){
            super(itemView);

            immagine = itemView.findViewById(R.id.immagineV);
            nome = itemView.findViewById(R.id.nomeTVL);
            listaColori = itemView.findViewById(R.id.coloriTVL);
            tipologia = itemView.findViewById(R.id.tipologiaTVL);
            stagionalita = itemView.findViewById(R.id.stagionalitàTVL);
            occasione = itemView.findViewById(R.id.occasioneTVL);

            btn_elimina = itemView.findViewById(R.id.rimuovi_btn);
        }
    }


}
