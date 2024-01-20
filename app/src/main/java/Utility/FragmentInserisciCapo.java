package Utility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import Storage.ArmadioDAO;
import Storage.ArmadioService;
import Storage.UtenteDAO;

import java.util.ArrayList;
import java.util.List;

public class FragmentInserisciCapo extends Fragment {
    private static final int REQUEST_CODE = 14;
    public Capo capo;
    Button buttonInserisciCapo;
    ImageView immagineCapo;
    Button buttonCreaIndumento;
    GridLayout gridLayout;
    RadioGroup radioGroupStag;
    RadioGroup radioGroupOccasione;
    RadioGroup radioGroupTipo;
    TextInputLayout nomeEditTextLayout;
    TextInputEditText nomeEditText;

    ArmadioService armadioService;
    String id_indumento;
    ArmadioDAO armadioDAO;

    String tipoSelezionato = "";
    String stagioneSelezionata = "";
    String occasioneSelezionata = "";
    Bitmap immagine = null;

    public FragmentInserisciCapo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frammeto_inserisci_capo, container, false);
        immagineCapo = v.findViewById(R.id.immagineCapo);
        buttonInserisciCapo = v.findViewById(R.id.btnImmagineCapo);
        buttonCreaIndumento = v.findViewById(R.id.btnCreaIndumento);

        gridLayout = v.findViewById(R.id.gridLayoutColore);
        radioGroupStag = v.findViewById(R.id.radioGroupStag);
        radioGroupOccasione = v.findViewById(R.id.radioGroupOccasione);
        radioGroupTipo = v.findViewById(R.id.radioGroup);
        nomeEditTextLayout = v.findViewById(R.id.nomeEditText);
        //nomeEditText = v.findViewById(R.id.editTextEmailLogin);

        armadioService = new ArmadioService(new ArmadioDAO());
        armadioDAO = new ArmadioDAO();

        id_indumento = armadioDAO.generateUniqueIndumentoId();

        buttonInserisciCapo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CODE);
            }
        });

        buttonCreaIndumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int childCount = gridLayout.getChildCount();
                List<String> coloriSelezionati = new ArrayList<>();

                for (int i = 0; i < childCount; i++) {
                    View childView = gridLayout.getChildAt(i);

                    if (childView instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) childView;

                        if (checkBox.isChecked()) {
                            String color = checkBox.getText().toString();
                            coloriSelezionati.add(color);
                        }
                    }
                }

                if (coloriSelezionati.isEmpty()) {
                    Toast.makeText(getContext(), "Inserire il colore", Toast.LENGTH_SHORT).show();
                    Log.d("INDUMENTO", "Errore colore");
                    return;
                }

                String brandIndumento = nomeEditText.getText().toString();

                if (TextUtils.isEmpty(brandIndumento)) {
                    Toast.makeText(getContext(), "Inserire il nome del Brand", Toast.LENGTH_SHORT).show();
                    Log.d("INDUMENTO", "Errore nome");
                    return;
                } else if (brandIndumento.length() < 2 || brandIndumento.length() > 15) {
                    Toast.makeText(getContext(), "Nome troppo lungo o troppo corto", Toast.LENGTH_SHORT).show();
                    Log.d("INDUMENTO", "Errore nome");
                    return;
                }

                int selectedRadioButtonIdStag = radioGroupStag.getCheckedRadioButtonId();

                if (selectedRadioButtonIdStag != -1) {
                    RadioButton selectedRadioButtonStag = getView().findViewById(selectedRadioButtonIdStag);
                    stagioneSelezionata = selectedRadioButtonStag.getText().toString();
                } else {
                    Toast.makeText(getContext(), "Inserire la Stagionalità", Toast.LENGTH_SHORT).show();
                    Log.d("INDUMENTO", "Errore stagion");
                    return;
                }

                int selectedRadioButtonIdOcc = radioGroupOccasione.getCheckedRadioButtonId();

                if (selectedRadioButtonIdOcc != -1) {
                    RadioButton selectedRadioButtonOcc = getView().findViewById(selectedRadioButtonIdOcc);
                    occasioneSelezionata = selectedRadioButtonOcc.getText().toString();
                } else {
                    Toast.makeText(getContext(), "Inserire l'Occasione", Toast.LENGTH_SHORT).show();
                    Log.d("INDUMENTO", "Errore occasione");
                    return;
                }

                int selectedRadioButtonIdTipo = radioGroupTipo.getCheckedRadioButtonId();

                if (selectedRadioButtonIdTipo != -1) {
                    RadioButton selectedRadioButtonTipo = getView().findViewById(selectedRadioButtonIdTipo);
                    tipoSelezionato = selectedRadioButtonTipo.getText().toString();
                } else {
                    Toast.makeText(getContext(), "Inserire la Tipologia", Toast.LENGTH_SHORT).show();
                    Log.d("INDUMENTO", "Errore tipologia");
                    return;
                }

                Drawable drawable = immagineCapo.getDrawable();
                if (drawable instanceof BitmapDrawable) {
                    immagine = ((BitmapDrawable) drawable).getBitmap();
                } else {
                    immagine = BitmapFactory.decodeResource(getResources(), R.drawable.felpa);
                }
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("INDUMENTO", "entro in onActivityResult");
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("INDUMENTO", "entra in nell'if di onActivityResult");
            Bitmap foto = (Bitmap) data.getExtras().get("data");
            immagineCapo.setImageBitmap(foto);
        } else {
            Log.d("INDUMENTO", "entra nell'else di onActivityResult");
            super.onActivityResult(requestCode, resultCode, data);
        }
        Log.d("INDUMENTO", "esco da if/else di onActivityResult");
    }

    private float calcolaDimensioneImmagine(Bitmap immagine) {
        return immagine.getByteCount() / (1024f * 1024f);
    }
}