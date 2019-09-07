package com.example.morpheus.proyectohackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.morpheus.proyectohackathon.Fragments.localizacionFragment;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.morpheus.proyectohackathon.Fragments.Camara;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnRegistro, btnAbrirCamara,btnLocalizacion;

    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistro = findViewById(R.id.btnRegistro);
        btnLocalizacion = findViewById(R.id.btnLocalizacion);
        btnAbrirCamara  = findViewById(R.id.btnAbrirCamara);


        btnRegistro.setOnClickListener(this);
        btnLocalizacion.setOnClickListener(this);
        btnAbrirCamara.setOnClickListener(this);

        checkCameraPermission();



    }

    public String generarJSON(){
        JSONObject jsonGeneral = new JSONObject();
        JSONObject jsonPersona = new JSONObject();
        JSONObject jsonNacimiento = new JSONObject();
        JSONObject jsonDocumento = new JSONObject();
        JSONObject jsonDireccion = new JSONObject();
        JSONObject jsonContacto = new JSONObject();

        try {
            jsonPersona.put("nombre","Sandra Fabiola");
            jsonPersona.put("paterno","Ramirez");
            jsonPersona.put("materno","Martinez");
            jsonPersona.put("genero","FEMALE");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonNacimiento.put("fecha","23-08-1995");
            jsonNacimiento.put("pais","México");
            jsonNacimiento.put("estado","Guanajuato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonDocumento.put("tipo","CURP");
            jsonDocumento.put("clave","RAMS950823MGTMRN04");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonDireccion.put("calle","5 de Mayo");
            jsonDireccion.put("numero","2");
            jsonDireccion.put("colonia","San Pedro de los Natranjos");
            jsonDireccion.put("estado","GU");
            jsonDireccion.put("pais","MEX");
            jsonDireccion.put("codigo","38931");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonContacto.put("numero","4661165435");
            jsonContacto.put("correo","rfabi442@gmail.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonGeneral.put("persona",jsonPersona);
            jsonGeneral.put("nacimiento",jsonNacimiento);
            jsonGeneral.put("documento",jsonDocumento);
            jsonGeneral.put("direccion",jsonDireccion);
            jsonGeneral.put("contacto",jsonContacto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonGeneral.toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnRegistro:
                String json = generarJSON();
                Log.i("respuesta",json);
                break;

            case R.id.btnLocalizacion:
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = new localizacionFragment();
                fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                break;
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
