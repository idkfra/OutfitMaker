package com.example.outfitmakerfake;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import Utility.FragmentInserisciCapo;

public class ArmadioController extends AppCompatActivity {
    FragmentManager fm;
    FrameLayout contenitoreFrammento;
    private Boolean attivatoFrammento = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.armadio);

        contenitoreFrammento = findViewById(R.id.contenitoreFrammento);

        fm = getSupportFragmentManager();

            /*Intent intent = getIntent();
            String brandIndumento = intent.getStringExtra("brandIndumento");
            ArrayList<String> coloriSelezionati = intent.getStringArrayListExtra("coloriSelezionati");
            String stagioneSelezionata = intent.getStringExtra("stagioneSelezionata");
            String occasioneSelezionata = intent.getStringExtra("occasioneSelezionata");
            String tipoSelezionato = intent.getStringExtra("tipoSelezionato");
            Bitmap immagine = (Bitmap) intent.getParcelableExtra("immagine");

             */
    }

    public void inserisciCapoClicked(View v) {
        attivatoFrammento = true;
        contenitoreFrammento.setVisibility(View.VISIBLE);
        FragmentInserisciCapo ic = new FragmentInserisciCapo();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.contenitoreFrammento, ic, "InserisciCapo");
        ft.commit();
    }

    public void rimuoviFrammentoClicked(View v){
        FragmentTransaction ft = fm.beginTransaction();
        Fragment ic = fm.findFragmentByTag("InserisciCapo");
        if(ic != null)
            ft.remove(ic);
        else
            Toast.makeText(this, "Inserire prima un FragmentA", Toast.LENGTH_SHORT).show();
        contenitoreFrammento.setVisibility(View.GONE);
        ft.commit();
    }
}