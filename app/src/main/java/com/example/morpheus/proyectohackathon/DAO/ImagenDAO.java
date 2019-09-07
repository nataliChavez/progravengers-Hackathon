package com.example.morpheus.proyectohackathon.DAO;

import android.content.Context;

import com.example.morpheus.proyectohackathon.Resources.Constantes;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONObject;

public class ImagenDAO {

    private ImagenDAO imagenDAO;

    public static ImagenDAO getInstance(Context context, DAO.OnResultadoConsulta<JSONObject>listener){
        String url = Constantes.HOST_PUERTO+"aws/isperson";

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        






    }
}
