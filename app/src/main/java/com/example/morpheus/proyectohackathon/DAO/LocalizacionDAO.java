package com.example.morpheus.proyectohackathon.DAO;

import android.content.Context;
import android.util.Log;

import com.example.morpheus.proyectohackathon.Models.HorariosCajeros;
import com.example.morpheus.proyectohackathon.Models.LocalizacionBBVA;
import com.morpheus.morpheus.WebService.Peticion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    Log.i("respuesta",url);

    Peticion.GET get = new Peticion.GET(context,url);

    get.getResponse(new Peticion.OnPeticionListener<String>() {
        @Override
        public void onSuccess(String s) {
            JSONObject jsonObject;
            JSONArray jsonArray;
            Log.i("RespuestaUbicacion", s);

            if(s.length() >0)
            {
                try {
                    jsonObject = new JSONObject(s);
                    int codigo = jsonObject.getInt("codigo");
                    if(codigo == 1)
                    {
                        Log.i("RespuestaUbicacion", jsonObject.toString());
                        jsonArray = jsonObject.getJSONArray("data");
                        Log.i("RespuestaUbicacion", "estoy aqui");

                        if(jsonArray.length() > 0)
                        {

                            List<LocalizacionBBVA> list = new ArrayList<>();
                            Log.i("RespuestaUbicacion", "estoy");


                            for(int i = 0; i < jsonArray.length(); i++)
                            {

                                JSONObject jsonPeq = jsonArray.getJSONObject(i);
                                JSONObject jsonCoordenadas = jsonPeq.getJSONObject("location");
                                double latitud = Double.parseDouble(jsonCoordenadas.getString("lat"));
                                double longitud = Double.parseDouble(jsonCoordenadas.getString("lon"));
                                LocalizacionBBVA localizacion = new LocalizacionBBVA(jsonPeq.getString("name"),jsonPeq.getString("adress"),
                                        jsonPeq.getString("postcode"),latitud,longitud);

                                list.add(localizacion);

                            }
                            listener.consultaSuccess(list);

                        }
                        else
                        {
                            listener.consultaSuccess(null);
                        }
                    }
                    else
                    {
                        listener.consultaSuccess(null);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    listener.consultaSuccess(null);
                }
            }

        }

        @Override
        public void onFailed(String s, int i) {
            listener.consultaFailed(s,i);
            Log.i("Respuesta","fallo");
        }
    });
}

public void localizacionSucursales(Context context, final DAO.OnResultadoListaConsulta<LocalizacionBBVA>listener)
{
    String url = HOST_PUERTO + CARPETA_BBVA + "atms";

    Peticion.GET get = new Peticion.GET(context,url);

    get.getResponse(new Peticion.OnPeticionListener<String>() {
        @Override
        public void onSuccess(String s) {
            JSONObject jsonObject;
            JSONArray jsonArray;

            if(s.length() >0)
            {
                try {
                    jsonObject = new JSONObject(s);
                    int codigo = jsonObject.getInt("codigo");
                    if(codigo == 1)
                    {
                        jsonArray = jsonObject.getJSONArray("data");

                        if(jsonArray.length() > 0)
                        {
                            List<LocalizacionBBVA> list = new ArrayList<>();
                            for(int i = 0; i < jsonArray.length(); i++ )
                            {
                                JSONObject jsonPeq = jsonArray.getJSONObject(i);
                                JSONObject jsonCoordenadas = jsonPeq.getJSONObject("location");
                                double latitud = Double.parseDouble(jsonCoordenadas.getString("lat"));
                                double longitud = Double.parseDouble(jsonCoordenadas.getString("lon"));
                                JSONObject jsonHorario = jsonPeq.getJSONObject("horarios");
                                JSONObject jsonOpeningHours = jsonHorario.getJSONObject("OpeningHours");
                                String openingTime = jsonOpeningHours.getString("OpeningTime");
                                String closingTime = jsonOpeningHours.getString("ClosingTime");
                                HorariosCajeros horario = new HorariosCajeros(jsonHorario.getString("Name"),
                                        openingTime,closingTime);
                                LocalizacionBBVA localizacion = new LocalizacionBBVA(jsonPeq.getString("name"),jsonPeq.getString("adress"),
                                        jsonPeq.getString("postcode"),jsonPeq.getString("movimientos"),horario, latitud,longitud);

                                list.add(localizacion);
                            }
                            listener.consultaSuccess(list);

                        }
                        else
                        {
                            listener.consultaSuccess(null);
                        }
                    }
                    else
                    {
                        listener.consultaSuccess(null);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    listener.consultaSuccess(null);
                }
            }

        }

        @Override
        public void onFailed(String s, int i) {
            listener.consultaFailed(s,i);

        }
    });



}
}
