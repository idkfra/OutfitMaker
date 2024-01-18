package com.example.outfitmakerfake;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import Utility.FragmentInserisciCapo;

public class ArmadioController extends AppCompatActivity {
    FragmentManager fm;
    FrameLayout contenitoreFrammento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.armadio);

        contenitoreFrammento = findViewById(R.id.contenitoreFrammento);

        fm = getSupportFragmentManager();

    }

    public void inserisciCapoClicked(View v) {
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