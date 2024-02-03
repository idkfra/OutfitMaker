package com.example.outfitmakerfake.AreaUtente;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.outfitmakerfake.R;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FragmentAreaAdmin extends Fragment {
    Button btn_disabilita_app, btn_abilita_app;



    TextView numeroUtentiTV, numeroOutfitTv, numeroCapiTv;




    boolean appDisattiva = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean appDisattiva = false;


    }

    // Questo metodo imposta il valore della variabile booleana a true
    public void setAppDisattivatrue() {
        appDisattiva = true;
    }

    // Questo metodo imposta il valore della variabile booleana a false
    public void setAppDisattivafalse() {
        appDisattiva= false;
    }

    // Questo metodo restituisce il valore della variabile booleana

    public FragmentAreaAdmin(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.frammento_areautente_admin, container, false);

        numeroUtentiTV=v.findViewById(R.id.numUtenti);
        numeroCapiTv=v.findViewById(R.id.numeroCapi);
        numeroOutfitTv=v.findViewById(R.id.numeroOutfit);
        btn_disabilita_app=v.findViewById(R.id.btn_disabilita_app);
        btn_abilita_app=v.findViewById(R.id.btn_riattiva_app);

        //Home home=new Home();
        setTextNumberDocuments("utenti", numeroUtentiTV);
        setTextNumberDocuments("capi", numeroCapiTv);
        setTextNumberDocuments("outfit", numeroOutfitTv);
        FragmentAreaAdmin fragmentAreaAdmin=new FragmentAreaAdmin();

        btn_disabilita_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppDisattivatrue();
                Log.d("bottone", "il bottone disattivo è settato a true: " + appDisattiva);
            }
        });

        btn_abilita_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppDisattivafalse();
                Log.d("bottone", "il bottone disattivo è settato a false");
            }
        });

        return v;
    }


    public Task<Long> getNumberOfDocuments(String raccolta){
        CollectionReference reference = FirebaseFirestore.getInstance().collection(raccolta);

        TaskCompletionSource taskCompletionSource=new TaskCompletionSource<>();

        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("numeroutenti", "è entrato nell'if");

                QuerySnapshot querySnapshot = task.getResult();
                long numdocuments = querySnapshot.size();

                Log.d("numeroutenti", "num" + numdocuments);

                taskCompletionSource.setResult(numdocuments);


            } else {
                Log.d("numeroutenti", "problemino" + task.getException());
                Exception exception = task.getException();
                taskCompletionSource.setException(exception);
            }
        });
        return taskCompletionSource.getTask();
    }


    private void setTextNumberDocuments(String collezione, TextView textView){
        getNumberOfDocuments(collezione).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                long numeroDoc=task.getResult();
                Log.d("numerodoc", "il numero di " + collezione + "è " + numeroDoc);
                textView.setText("Numero " + collezione + ": " + numeroDoc);
            }else{
                Exception exception=task.getException();
                Log.d("numerodoc", "eccezione: " + exception);
            }
        });
    }


}




