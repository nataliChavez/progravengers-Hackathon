package com.example.morpheus.proyectohackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.morpheus.proyectohackathon.Fragments.Camara;

import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnRegistro, btnAbrirCamara;

    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistro = findViewById(R.id.btnRegistro);
        btnAbrirCamara  = findViewById(R.id.btnAbrirCamara);

        btnAbrirCamara.setOnClickListener(this);

        checkCameraPermission();

    }

    @Override
    public void onClick(View v) {



        switch (v.getId()){

            case R.id.btnAbrirCamara:

                fragment = new Camara();

                break;

        }


        if (fragment != null)
            getSupportFragmentManager().beginTransaction().replace(R.id.contentPrincial, fragment).addToBackStack(null).commit();

    }


    private void checkCameraPermission() {

    }
}
