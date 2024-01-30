package com.example.outfitmakerfake.Armadio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.R;
import com.example.outfitmakerfake.AreaUtente.AreaUtenteController;
import com.example.outfitmakerfake.Filtri.FragmentFiltri;
import com.example.outfitmakerfake.Utility.NetworkUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ArmadioController extends AppCompatActivity {
    FragmentManager fm;
    FrameLayout frammento_inserisci_capo;
    ImageView btn_frammento_modifica_capo;
    FrameLayout frammento_modifica_capo;
    FrameLayout frammento_filtri;
    RecyclerView recyclerView;
    ArrayList<Capo> capoArrayList;
    AdapterListaArmadio myAdapter;
    FirebaseFirestore db;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.armadio);

        if (!NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente", Toast.LENGTH_SHORT).show();
            return;
        }

        btn_frammento_modifica_capo = findViewById(R.id.btn_modifica_capo);
        frammento_modifica_capo = findViewById(R.id.contenitore_frammento_modifica_capo_armadio);

        frammento_inserisci_capo = findViewById(R.id.contenitoreFrammento);
        frammento_filtri = findViewById(R.id.contenitore_frammento_filtri);
        fm = getSupportFragmentManager();

        recyclerView = findViewById(R.id.recycleList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        capoArrayList = new ArrayList<Capo>();
        myAdapter = new AdapterListaArmadio(ArmadioController.this, capoArrayList);

        recyclerView.setAdapter(myAdapter);
        EventChangeListener();
    }

    private void EventChangeListener() {
        String uid = currentUser.getUid();;

        db.collection("capi")
                .whereEqualTo("uid", uid)
                .orderBy("tipologia", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.d("LISTA", "Firestore error: " + error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                capoArrayList.add(dc.getDocument().toObject(Capo.class));
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void inserisciCapoClicked(View v) {
        frammento_inserisci_capo.setVisibility(View.VISIBLE);
        FragmentInserisciCapo ic = new FragmentInserisciCapo();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.contenitoreFrammento, ic, "InserisciCapo");
        ft.addToBackStack(null);
        ft.commit();
    }

    public void rimuoviFrammentoClicked(View v){
        FragmentTransaction ft = fm.beginTransaction();
        Fragment ic = fm.findFragmentByTag("InserisciCapo");
        if(ic != null)
            ft.remove(ic);
        else
            Toast.makeText(this, "Inserire prima un FragmentA", Toast.LENGTH_SHORT).show();
        frammento_inserisci_capo.setVisibility(View.GONE);
        ft.commit();
    }

    public void inserisciFrammentoModificaCapo(View v){
        frammento_modifica_capo.setVisibility(View.VISIBLE);
        FragmentModificaCapo mc = new FragmentModificaCapo();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.contenitore_frammento_modifica_capo_armadio, mc, "ModificaCapo");
        ft.commit();
    }

    public void rimuoviFrammentoModificaClicked(View v){
        FragmentTransaction ft = fm.beginTransaction();
        Fragment mc = fm.findFragmentByTag("ModificaCapo");
        if(mc !=null)
            ft.remove(mc);
        else
            Toast.makeText(this, "Inserire prima un Fragment", Toast.LENGTH_SHORT).show();
        frammento_modifica_capo.setVisibility(View.GONE);
        ft.commit();
    }

    public void inserisciFrammentoFiltri(View v){
        frammento_filtri.setVisibility(View.VISIBLE);
        FragmentFiltri ff = new FragmentFiltri();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.contenitore_frammento_filtri, ff, "RicercaFiltri");
        ft.commit();
    }

    public void rimuoviFrammentoFiltri(View v){
        FragmentTransaction ft = fm.beginTransaction();
        Fragment ff = fm.findFragmentByTag("RicercaFiltri");
        if(ff != null)
            ft.remove(ff);
        else
            Toast.makeText(this, "Inserire prima un FragmentA", Toast.LENGTH_SHORT).show();
        frammento_filtri.setVisibility(View.GONE);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.menu1){
            setContentView(R.layout.home);
            return true;
        } else if(id == R.id.menu2){
            Intent i = new Intent(getApplicationContext(), AreaUtenteController.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            return true;
        } else if(id == R.id.menu3){
            setContentView(R.layout.assistenza);
            return true;
        } else if(id == R.id.menu4){
            setContentView(R.layout.istruzioni);
        }
        return super.onOptionsItemSelected(item);
    }
}