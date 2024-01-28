package com.example.outfitmakerfake.Archivio;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.CreazioneOutfit.AdapterCreazioneOutfit;
import com.example.outfitmakerfake.Entity.Outfit;
import com.example.outfitmakerfake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdapterArchivio extends RecyclerView.Adapter<AdapterArchivio.SupportA> {
    Context context;
    ArrayList<Outfit> outfitArrayList;
    String idOutfit;
    FirebaseFirestore db;
    FirebaseAuth auth;

    public AdapterArchivio(Context context, ArrayList<Outfit> outfitArrayList) {
        this.context = context;
        this.outfitArrayList = outfitArrayList;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }


    @NonNull
    @Override
    public SupportA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.singolo_elemento_archivio, parent, false);

        return new SupportA(v);
    }

    @Override
    public void onBindViewHolder(AdapterArchivio.@org.checkerframework.checker.nullness.qual.NonNull SupportA holder, int position) {
        String uid = auth.getCurrentUser().getUid();
        Outfit outfit = outfitArrayList.get(position);
        holder.id_outfit.setText("Outfit " + (position + 1));

        holder.img_ingrandisci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OutfitUtente.class);
                i.putExtra("id_outfit", outfit.getId_outfit());
                i.putParcelableArrayListExtra("lista_capi", outfit.getLista_capi());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return outfitArrayList.size();
    }

    public static class SupportA extends RecyclerView.ViewHolder{
        FrameLayout frammento_vedi_outfit;

        TextView id_outfit;
        ImageView img_ingrandisci;

        public SupportA(@NonNull View itemView) {
            super(itemView);

            frammento_vedi_outfit = itemView.findViewById(R.id.contenitore_frammento_vedi_outfit);
            id_outfit = itemView.findViewById(R.id.id_outfit_archivio);
            img_ingrandisci = itemView.findViewById(R.id.img_ingrandisci);
        }
    }
}
