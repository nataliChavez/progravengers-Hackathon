package com.example.morpheus.proyectohackathon.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

public class crearCuentaFragment extends Fragment {


    //INICIAR LA CLASE
    private static  crearCuentaFragment getInstance(){

        crearCuentaFragment fragment = new crearCuentaFragment();

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    //PERMITE GENERAR EL JSON CON LOS DATOS DEL CLIENTE
    public String generarJSON(String nombre,String apellidoP,String apellidoM,String genero,String fecha,String curp,String calle,String numero,
                              String colonia){
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


}
