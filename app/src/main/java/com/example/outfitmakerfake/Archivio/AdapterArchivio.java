package com.example.outfitmakerfake.Archivio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.CreazioneOutfit.AdapterCreazioneOutfit;
import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.Entity.Outfit;
import com.example.outfitmakerfake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
        String tipologia_singolo_elenco = "";
        for(Capo capo : outfit.getLista_capi()){
            tipologia_singolo_elenco = tipologia_singolo_elenco + capo.getTipologia() + ", ";
            holder.id_outfit.setText(tipologia_singolo_elenco);
        }
        //holder.id_outfit.setText("Outfit");

        holder.img_ingrandisci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OutfitUtente.class);
                i.putExtra("id_outfit", outfit.getId_outfit());
                i.putParcelableArrayListExtra("lista_capi", outfit.getLista_capi());
                context.startActivity(i);
            }
        });

        holder.img_remove_outfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Log.d("REMMM", "Size: " + outfitArrayList.size());

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Conferma eliminazione");
                    builder.setMessage("Sei sicuro di voler eliminare questo outfit?");

                    builder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String outfitIdToDelete = outfitArrayList.get(currentPosition).getId_outfit();

                            db.collection("outfit")
                                    .document(outfitIdToDelete)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            if (currentPosition < outfitArrayList.size() &&
                                                    outfitArrayList.get(currentPosition).getId_outfit().equals(outfitIdToDelete)) {
                                                outfitArrayList.remove(currentPosition);
                                                notifyItemRemoved(currentPosition);
                                                Toast.makeText(context, "Outfit cancellato", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(context, "Outfit cancellato", Toast.LENGTH_SHORT);
                                                Intent i = new Intent(context, Archivio.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                context.startActivity(i);
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Errore nell'eliminazione: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
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
        ImageView img_ingrandisci, img_remove_outfit;

        public SupportA(@NonNull View itemView) {
            super(itemView);

            frammento_vedi_outfit = itemView.findViewById(R.id.contenitore_frammento_vedi_outfit);
            id_outfit = itemView.findViewById(R.id.id_outfit_archivio);
            img_ingrandisci = itemView.findViewById(R.id.img_ingrandisci);
            img_remove_outfit = itemView.findViewById(R.id.img_remove_outfit);
        }
    }
}
