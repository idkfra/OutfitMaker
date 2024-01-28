package com.example.outfitmakerfake.Archivio;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdapterOutfitUtente extends RecyclerView.Adapter<AdapterOutfitUtente.SupportOU> {
    Context context;
    ArrayList<Capo> lista_capi;
    FirebaseFirestore db;
    FirebaseAuth auth;

    public AdapterOutfitUtente(Context context, ArrayList<Capo> lista_capi) {
        this.context = context;
        this.lista_capi = lista_capi;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public AdapterOutfitUtente.SupportOU onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.singolo_elemento_outfit_utente, parent, false);

        return new SupportOU(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOutfitUtente.SupportOU holder, int position) {
        Capo capo = lista_capi.get(position);
        String idCapo = lista_capi.get(position).getId_indumento();

        holder.nome.setText(capo.getNome_brand());
        holder.tipologia.setText(capo.getTipologia());
        holder.stagionalita.setText(capo.getStagionalita());
        holder.occasione.setText(capo.getOccasione());

        String coloriString = TextUtils.join(", ", capo.getColori());
        holder.listaColori.setText(coloriString + " ");

        if(capo.getTipologia().equals("Felpa")){
            holder.immagine.setImageResource(R.drawable.felpa);
        } else if(capo.getTipologia().equals("T-shirt") && capo.getOccasione().equals("Elegante")){
            holder.immagine.setImageResource(R.drawable.polo);
        } else if(capo.getTipologia().equals("T-shirt")){
            holder.immagine.setImageResource(R.drawable.t_shirt);
        } else if(capo.getTipologia().equals("Maglia lunga") && capo.getOccasione().equals("Elegante")){
            holder.immagine.setImageResource(R.drawable.maglia_lunga_elegante);
        } else if(capo.getTipologia().equals("Maglia lunga")){
            holder.immagine.setImageResource(R.drawable.maglia_lunga);
        } else if(capo.getTipologia().equals("Giacca") && capo.getOccasione().equals("Elegante")){
            holder.immagine.setImageResource(R.drawable.giacca_elegante);
        } else if(capo.getTipologia().equals("Giacca")){
            holder.immagine.setImageResource(R.drawable.giacca);
        } else if(capo.getTipologia().equals("Camicia")){
            holder.immagine.setImageResource(R.drawable.camicia);
        } else if(capo.getTipologia().equals("Cappotto")){
            holder.immagine.setImageResource(R.drawable.cappotto);
        } else if(capo.getTipologia().equals("Jeans") || capo.getTipologia().equals("Pantalone")){
            holder.immagine.setImageResource(R.drawable.jeans);
        } else if(capo.getTipologia().equals("Gonna")){
            holder.immagine.setImageResource(R.drawable.gonna);
        } else if(capo.getTipologia().equals("Vestito corto")){
            holder.immagine.setImageResource(R.drawable.vestito_corto);
        } else if(capo.getTipologia().equals("Vestito lungo")){
            holder.immagine.setImageResource(R.drawable.vestito_lungo);
        } else if(capo.getTipologia().equals("Scarpe")){
            holder.immagine.setImageResource(R.drawable.scarpa_casual);
        }
    }

    @Override
    public int getItemCount() {
        return lista_capi.size();
    }

    public static class SupportOU extends RecyclerView.ViewHolder {
        TextView nome, listaColori, tipologia, stagionalita, occasione;
        ImageView immagine;

        public SupportOU(@NonNull View itemView){
            super(itemView);

            immagine = itemView.findViewById(R.id.immagineOU);
            nome = itemView.findViewById(R.id.nomeOU);
            listaColori = itemView.findViewById(R.id.coloriOU);
            tipologia = itemView.findViewById(R.id.tipologiaOU);
            stagionalita = itemView.findViewById(R.id.stagionalitàOU);
            occasione = itemView.findViewById(R.id.occasioneOU);
        }
    }
}
