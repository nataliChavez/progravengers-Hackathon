package com.example.morpheus.proyectohackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    //DECLARACION DE VARIABLES
    private Handler handler;
    private ImageView imgZoom;
    private Animation animacionZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iniciarApp();
    }

    public void iniciarApp()
    {
        //CAMBIAR EL COLOR DE LA BARRA DE NAVEGACION
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        //ASIGNACIÓN DE CONTROLES
        imgZoom = findViewById(R.id.imgSplasScreen);
        animacionZoom = AnimationUtils.loadAnimation(this, R.anim.animacion_escala);
        //INICIACIÓN DE ANIMACIÓN
        imgZoom.startAnimation(animacionZoom);

        //ESPERA EN ESTA ACTIVAR EL TIEMPO INDICADO (4000 MS) E INICIA LA OTRA ACTIVIDAD
        handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        },4000);

    }
}
