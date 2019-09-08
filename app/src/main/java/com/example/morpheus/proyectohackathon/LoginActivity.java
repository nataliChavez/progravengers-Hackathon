package com.example.morpheus.proyectohackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.morpheus.proyectohackathon.Fragments.Camara;

import java.util.Timer;
import java.util.TimerTask;


public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{
    private Button btnIniciarSesion;
    private ImageSwitcher imageSwitcher;
    private Button btnRegistrarse;
    private  Intent intent = null;

    private int [] slider = {R.drawable.mexico_1, R.drawable.guanajuato,R.drawable.mexico_2, R.drawable.guanajuato_2};
    private int positon;
    private static final int DURACION = 6000;
    private Timer timer = null;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorAzulOscuro));

        btnRegistrarse.setOnClickListener(this);
        imageSwitcher = findViewById(R.id.imageSwitcher);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        imageSwitcher.setInAnimation(fadeIn);
        imageSwitcher.setOutAnimation(fadeOut);

        btnIniciarSesion.setOnClickListener(this);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory()
        {
            public View makeView()
            {
                ImageView imageView = new ImageView(LoginActivity.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                return imageView;
            }
        });

        startSlider();


    }

    public void  startSlider()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        imageSwitcher.setImageResource(slider[positon]);
                        positon++;
                        if (positon == slider.length)
                            positon = 0;
                    }
                });
            }
        }, 0, DURACION);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegistrarse:
                 intent = new Intent(LoginActivity.this,RegistroActivity.class);
                startActivity(intent);
                break;

            case R.id.btnIniciarSesion:
                intent = new Intent(LoginActivity.this, PantallaPrincipalActivity.class);
                startActivity(intent);
                break;


            case R.id.btnIniciarSesion:

                FragmentManager fragmentMang = getSupportFragmentManager();
                Fragment   fragmento = new Camara();
                fragmentMang.beginTransaction().replace(R.id.contentLogin, fragmento).commit();

                break;

        }
    }
}
