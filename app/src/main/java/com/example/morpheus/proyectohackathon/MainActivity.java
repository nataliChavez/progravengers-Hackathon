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
                String url = Constantes.HOST_PUERTO + "aws/isperson";
                carpeta = new File(Environment.getExternalStorageDirectory().getPath() + "/imagenesBBVA/1567844443.png");
                HashMap<String, String> params = new HashMap<String, String>();


                MultipartRequest mr = new MultipartRequest(url, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("respuesta",error.toString());

                    }
                }, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("respuesta",response);

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

    private void uploadFoto(File file,String url) {

        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(url);
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody foto = new FileBody(file, "multipart/form-data");
        mpEntity.addPart("img", foto);
        httppost.setEntity(mpEntity);
        try {
            httpclient.execute(httppost);
            httpclient.getConnectionManager().shutdown();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
