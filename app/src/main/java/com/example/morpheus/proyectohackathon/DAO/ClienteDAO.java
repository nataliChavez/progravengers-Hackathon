package com.example.morpheus.proyectohackathon.DAO;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.morpheus.proyectohackathon.Resources.Constantes;
import com.example.morpheus.proyectohackathon.Resources.Prueba;
import com.morpheus.morpheus.WebService.Peticion;

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
    public void registrarCuenta(Context context, JSONObject json, final DAO.OnResultadoConsulta<JSONObject>listener){
        String url = Constantes.HOST_PUERTO+"bbva/createaccount";
        Prueba.POST post = new Prueba.POST(context,url,json);
        post.getResponse(new Peticion.OnPeticionListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                    int codigo;
                    JSONObject jsonData;

                try {
                    codigo = jsonObject.getInt("codigo");


                    if (codigo == 1){
                        jsonData = jsonObject.getJSONObject("data");
                        listener.consultaSuccess(jsonData);

                    }else{
                        listener.consultaSuccess(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.consultaSuccess(null);
                }
            }

            @Override
            public void onFailed(String s, int i) {
                Log.i("respuesta",s);
                listener.consultaFailed(s, i);
            }
        });
    }

}
