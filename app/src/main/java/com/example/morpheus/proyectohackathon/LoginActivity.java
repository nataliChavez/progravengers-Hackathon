package com.example.morpheus.proyectohackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageSwitcher imageSwitcher;
    private Button btnRegistrarse;
    //private int [] slider = {R.};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        btnRegistrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegistrarse:

                break;


        }

    }
}
