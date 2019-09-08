package com.example.morpheus.proyectohackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{
    private Button btnIniciarSesion;
    private ImageSwitcher imageSwitcher;
    private int [] slider = {R.drawable.mexico_1, R.drawable.guanajuato,R.drawable.mexico_2, R.drawable.guanajuato_2};
    private int positon;
    private static final int DURACION = 6000;
    private Timer timer = null;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
    public void onClick(View v) {

    }
}
