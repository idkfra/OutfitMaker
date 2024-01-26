package com.example.outfitmakerfake.CreazioneOutfit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
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

public class AdapterOutfitCreato extends RecyclerView.Adapter<AdapterOutfitCreato.SupportOC> {
    Context context;
    ArrayList<Capo> lista_capi;
    String id_indumento;
    FirebaseFirestore db;
    FirebaseAuth auth;

    public AdapterOutfitCreato(Context context, ArrayList<Capo> lista_capi) {
        this.context = context;
        this.lista_capi = lista_capi;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public AdapterOutfitCreato.SupportOC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.singolo_elemento_outfit_creato, parent, false);
       return new SupportOC(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOutfitCreato.SupportOC holder, int position) {
        Capo capo = lista_capi.get(position);
        id_indumento = lista_capi.get(position).getId_indumento();

        holder.nome.setText(capo.getNome_brand());
        holder.tipologia.setText(capo.getTipologia());
        holder.stagionalita.setText(capo.getStagionalita());
        holder.occasione.setText(capo.getOccasione());

        // Visualizza la lista di colori come una singola stringa separata da virgole
        String coloriString = TextUtils.join(", ", capo.getColori());
        holder.lista_colori.setText(coloriString + " ");

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

    public static class SupportOC extends RecyclerView.ViewHolder{
        ImageView immagine;
        TextView nome, lista_colori, tipologia, stagionalita, occasione;


        public SupportOC(@NonNull View itemView){
            super(itemView);

            immagine = itemView.findViewById(R.id.immagineOC);
            nome = itemView.findViewById(R.id.nomeOC);
            lista_colori = itemView.findViewById(R.id.coloriOC);
            tipologia = itemView.findViewById(R.id.tipologiaOC);
            stagionalita = itemView.findViewById(R.id.stagionalitàOC);
            occasione = itemView.findViewById(R.id.occasioneOC);
        }
    }
}
