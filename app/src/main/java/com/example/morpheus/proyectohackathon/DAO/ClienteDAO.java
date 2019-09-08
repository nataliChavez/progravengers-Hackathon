package com.example.morpheus.proyectohackathon.DAO;

import android.content.Context;
import android.util.Log;

import com.example.morpheus.proyectohackathon.Resources.Constantes;
import com.example.morpheus.proyectohackathon.Resources.Prueba;
import com.morpheus.morpheus.WebService.Peticion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ClienteDAO {

    private  static ClienteDAO clienteDAO;

    public static ClienteDAO getInstance(){
        if (clienteDAO == null){
            clienteDAO = new ClienteDAO();
        }

        return clienteDAO;
    }


    //PERMITE REGISTRAR UNA CUENTA DE UN CLIENTE NUEVO
    public void registrarCuenta(Context context, String json, final DAO.OnResultadoConsulta<JSONObject>listener){
        String url = Constantes.HOST_PUERTO+"";

        HashMap params = new HashMap();
        Peticion.POST post = new Peticion.POST(context,url,params);
        post.getResponse(new Peticion.OnPeticionListener<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    listener.consultaSuccess(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.consultaSuccess(null);
                }

            }

            @Override
            public void onFailed(String s, int i) {
                listener.consultaFailed(s, i);

            }
        });

    }


    public void iniciarSesion(Context context, String imagen1, String imagen2,String nombre1, String nombre2, final  DAO.OnResultadoConsulta<JSONObject>listener){

        String url = Constantes.HOST_PUERTO+"validate/base64";

        HashMap params = new HashMap();
        params.put("imagen1", imagen1);
        params.put("nombre1",nombre1);
        params.put("imagen2",imagen2);
        params.put("nombre2",nombre2);


        Peticion.POST post = new Peticion.POST(context,url,params);
        post.getResponse(new Peticion.OnPeticionListener<String>() {
            @Override
            public void onSuccess(String s) {

                Log.i("respuesta",s);

                JSONObject jsonObject;
                try
                {
                    jsonObject = new JSONObject(s);
                    if (jsonObject.length() > 0) {

                        listener.consultaSuccess(jsonObject);

                    }else {

                        listener.consultaSuccess(null);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.consultaSuccess(null);
                }

            }

            @Override
            public void onFailed(String s, int i) {

            }
        });



    }

}
