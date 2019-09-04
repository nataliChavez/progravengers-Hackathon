package com.example.morpheus.proyectohackathon.DAO;

import android.content.Context;
import android.util.Log;

import com.example.morpheus.proyectohackathon.Models.LocalizacionBBVA;
import com.morpheus.morpheus.WebService.Peticion;

import org.json.JSONArray;

import static com.example.morpheus.proyectohackathon.Resources.Constantes.CARPETA_BBVA;
import static com.example.morpheus.proyectohackathon.Resources.Constantes.HOST_PUERTO;

public class LocalizacionDAO {

public  static LocalizacionDAO localizacionDAO;

    public LocalizacionDAO() {
    }

    //CREAR INSTANCIA DE LA CLASE
    public static LocalizacionDAO getInstance()
    {
        if(localizacionDAO == null)
        {
            localizacionDAO = new LocalizacionDAO();
        }
        return localizacionDAO;
    }

public void LocalizacionCajeros(Context context, final DAO.OnResultadoListaConsulta<LocalizacionBBVA>listener)
{
    String url = HOST_PUERTO + CARPETA_BBVA + "atms";

    Peticion.GET get = new Peticion.GET(context,url);

    get.getResponse(new Peticion.OnPeticionListener<String>() {
        @Override
        public void onSuccess(String s) {
            JSONArray jsonArray;
            
            if(s.length() >0)
            {


            }

        }

        @Override
        public void onFailed(String s, int i) {

        }
    });
}
}
