package com.example.outfitmakerfake.Armadio;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.outfitmakerfake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import Storage.Armadio.ArmadioDAO;
import Storage.Armadio.ArmadioService;

public class FragmentModificaCapo extends Fragment {
    ArmadioService armadioService;
    ArmadioDAO armadioDAO;

    TextInputEditText nome_nuovo;
    GridLayout gridLayout_nuovo;
    RadioGroup stagionalita_nuova;
    RadioGroup occasione_nuova;
    RadioGroup tipologia_nuova;
    Button btn_modifica_capo_armadio;
    ProgressBar progressBar;

    String nome_brand = "";
    String tipologia = "";
    String stagionalita = "";
    String occasione = "";

    public FragmentModificaCapo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frammento_modifica_capo, container, false);

        nome_nuovo = v.findViewById(R.id.nome_frammento_mc);
        gridLayout_nuovo = v.findViewById(R.id.grid_layout_frammento_mc);
        stagionalita_nuova = v.findViewById(R.id.stagionaltita_frammento_mc);
        occasione_nuova = v.findViewById(R.id.occasione_frammento_mc);
        tipologia_nuova = v.findViewById(R.id.tipologia_frammento_mc);
        btn_modifica_capo_armadio = v.findViewById(R.id.btn_modifica_capo_armadio);
        progressBar = v.findViewById(R.id.progressBar_modifica_capo);

        armadioService = new ArmadioService(new ArmadioDAO());

        btn_modifica_capo_armadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                int childCount = gridLayout_nuovo.getChildCount();
                List<String> coloriSelezionati = new ArrayList<>();

                for (int i = 0; i < childCount; i++) {
                    View childView = gridLayout_nuovo.getChildAt(i);

                    if (childView instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) childView;

                        if (checkBox.isChecked()) {
                            Log.d("INDUMENTO", "Entro nell'if per prendere i colori");

                            String color = checkBox.getText().toString();
                            coloriSelezionati.add(color);
                            Log.d("INDUMENTO", "Colore: " + color);
                        }
                    }
                }
                if (coloriSelezionati.isEmpty()) {
                    Log.d("INDUMENTO", "Errore colore");
                    Toast.makeText(getContext(), "Inserire il colore", Toast.LENGTH_SHORT).show();
                    return;
                }

                //NOME BRAND
                nome_brand = nome_nuovo.getText().toString();
                if (TextUtils.isEmpty(nome_brand)) {
                    Log.d("INDUMENTO", "Errore nome");
                    Toast.makeText(getContext(), "Inserire il nome del Brand", Toast.LENGTH_SHORT).show();
                    return;
                } else if (nome_brand.length() < 2 || nome_brand.length() > 15) {
                    Log.d("INDUMENTO", "Errore nome");
                    Toast.makeText(getContext(), "Nome troppo lungo o troppo corto", Toast.LENGTH_SHORT).show();
                    return;
                }

                //STAGIONALITA
                int selectedRadioButtonIdStag = stagionalita_nuova.getCheckedRadioButtonId();
                if(selectedRadioButtonIdStag != 1){
                    RadioButton stagionalitaSelezionata = getView().findViewById(selectedRadioButtonIdStag);
                    stagionalita = stagionalitaSelezionata.getText().toString();
                } else {
                    Toast.makeText(getContext(), "Inserire la stagionalità", Toast.LENGTH_SHORT).show();
                    Log.d("INDUMENTO", "Errore stagione");
                    return;
                }

                //OCCASIONE
                int selectedRadioButtonIdOcc = occasione_nuova.getCheckedRadioButtonId();
                if(selectedRadioButtonIdOcc != 1) {
                    RadioButton occasioneSelezionata = getView().findViewById(selectedRadioButtonIdOcc);
                    occasione = occasioneSelezionata.getText().toString();
                } else {
                    Toast.makeText(getContext(), "Inserire l'occasione", Toast.LENGTH_SHORT).show();
                    Log.d("INDUMENTO", "Errore occasione");
                    return;
                }

                //TIPOLOGIA
                int selectedRadioButtonIdTip = tipologia_nuova.getCheckedRadioButtonId();
                if(selectedRadioButtonIdTip != 1){
                    RadioButton tipologiaSelezionata = getView().findViewById(selectedRadioButtonIdTip);
                    tipologia = tipologiaSelezionata.getText().toString();
                } else {
                    Toast.makeText(getContext(), "Inserire la tipologia", Toast.LENGTH_SHORT).show();
                    Log.d("INDUMENTO", "Errore occasione");
                    return;
                }

                armadioService.modificaCapo(nome_brand, coloriSelezionati, tipologia, stagionalita, occasione).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if(task.isSuccessful()) {
                            boolean modificaIndumento = task.getResult();
                            Log.d("INDUMENTO", "creazioneIndumento = " + modificaIndumento);


                            if(modificaIndumento) {
                                Toast.makeText(getContext(), "Indumento modificato con successo", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getContext(), ArmadioController.class);
                                startActivity(i);
                            } else {
                                Log.d("INDUMENTO", "Entra in else di creazioneIndumento");
                                Toast.makeText(getContext(), "Errore nella creazione dell'Indumento", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Exception exception = task.getException();
                            Log.d("INDUMENTO", "else in onComplete exception: " + exception);
                            Toast.makeText(getContext(), "Errore: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return v;
    }
}