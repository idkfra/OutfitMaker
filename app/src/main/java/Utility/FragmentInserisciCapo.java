package Utility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.outfitmakerfake.R;

public class FragmentInserisciCapo extends Fragment {
    private static final int REQUEST_CODE = 14;
    Button buttonInserisciCapo;
    ImageView immagineCapo;
    Button buttonCreaIndumento;

    public FragmentInserisciCapo() {
    }

    @Override
    public void onCreate(Bundle s){
        super.onCreate(s);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frammeto_inserisci_capo, container, false);
        immagineCapo = v.findViewById(R.id.immagineCapo);
        buttonInserisciCapo = v.findViewById(R.id.btnImmagineCapo);

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

            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            Bitmap foto = (Bitmap) data.getExtras().get("data");
            immagineCapo.setImageBitmap(foto);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
