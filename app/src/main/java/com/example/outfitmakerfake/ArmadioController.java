package com.example.outfitmakerfake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.outfitmakerfake.LoginController;
import com.example.outfitmakerfake.R;
import com.google.firebase.auth.FirebaseAuth;

public class ArmadioController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.armadio);
    }

}
