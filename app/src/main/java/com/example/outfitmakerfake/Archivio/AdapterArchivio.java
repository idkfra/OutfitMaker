package com.example.outfitmakerfake.Archivio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    }

    @Override
    public int getItemCount() {
        return outfitArrayList.size();
    }

    public static class SupportA extends RecyclerView.ViewHolder{

        TextView id_outfit;

        public SupportA(@NonNull View itemView) {
            super(itemView);

            id_outfit = itemView.findViewById(R.id.id_outfit_archivio);
        }
    }
}
