package com.example.morpheus.proyectohackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.morpheus.proyectohackathon.DAO.ImagenDAO;
import com.example.morpheus.proyectohackathon.Fragments.localizacionFragment;

import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.morpheus.proyectohackathon.Fragments.Camara;
import com.example.morpheus.proyectohackathon.Resources.Constantes;
import com.example.morpheus.proyectohackathon.Resources.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnRegistro, btnAbrirCamara,btnLocalizacion;
    private final String boundary = "apiclient-" + System.currentTimeMillis();
    private final String mimeType = "multipart/form-data;boundary=" + boundary;
    private byte[] multipartBody;
    private Fragment fragment = null;

    private File carpeta = null;

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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnRegistro:
                String url = Constantes.HOST_PUERTO + "aws/isperson";
                carpeta = new File(Environment.getExternalStorageDirectory().getPath() + "/imagenesBBVA/1567844443.png");
                HashMap<String, String> params = new HashMap<String, String>();


                MultipartRequest mr = new MultipartRequest(url, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                    }
                }, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                    }
                },carpeta,params);

                Volley.newRequestQueue(this).add(mr);


                break;

            case R.id.btnLocalizacion:

                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = new localizacionFragment();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

                break;

            case R.id.btnAbrirCamara:

                FragmentManager fragmentMang = getSupportFragmentManager();
                Fragment   fragmento = new Camara();
                fragmentMang.beginTransaction().replace(R.id.contentPrincial, fragmento).commit();

                break;



        }





    }

    private void checkCameraPermission() {

    }

}
