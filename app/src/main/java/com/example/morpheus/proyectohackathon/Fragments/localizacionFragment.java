package com.example.morpheus.proyectohackathon.Fragments;




import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.morpheus.proyectohackathon.DAO.DAO;
import com.example.morpheus.proyectohackathon.DAO.LocalizacionDAO;
import com.example.morpheus.proyectohackathon.Models.LocalizacionBBVA;
import com.example.morpheus.proyectohackathon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class localizacionFragment extends Fragment implements OnMapReadyCallback{
    //DECLARACION DE VARIABLES
    GoogleMap googleMap;
    SupportMapFragment supportMapFragment;
    private LocalizacionDAO localizacionDAO = LocalizacionDAO.getInstance();
    private List<LocalizacionBBVA> localizacionList = new ArrayList<>();
    private List<LatLng> puntosGPSCajeros=new ArrayList<>();
    private Marker CajeroMarker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_localizacion,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapLocalizacion);
        supportMapFragment.getMapAsync(this);
        listarUbicacionCajeros();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.442074, -99.201024), 12));
    }

    public void listarUbicacionCajeros()
    {
        Log.i("Respuesta", "hola esto aqui");
        localizacionDAO.LocalizacionCajeros(getContext(), new DAO.OnResultadoListaConsulta<LocalizacionBBVA>() {
            @Override
            public void consultaSuccess(List<LocalizacionBBVA> t) {
                Log.i("RespuestaLista", "" +  t.size());
                if(t != null)
                {
                    Log.i("Respuesta", "" +  t.size());
                    localizacionList = t;
                    try
                    {
                        for (int i = 0; i < localizacionList.size(); i++)
                        {

                            puntosGPSCajeros.add(new LatLng(t.get(i).getLatitud(), t.get(i).getLongitud())); //LLENAMOS LA LISTA QUE PERMITE DIBUJAR LOS PUNTOS EN EL MAPA
                            dibujarPuntosCajeros();
                            Log.i("RespuestaUbicacion", puntosGPSCajeros.size() + "");

                        }

                    }
                    catch (Exception e)
                    {
                        Log.i("RespuestaUbicacion", "Hola estoy en el catch");
                        e.printStackTrace();
                        Log.i("respuesta",e + "");
                    }
                }
            }

            @Override
            public void consultaFailed(String error, int codigo) {

            }
        });
    }

    public void dibujarPuntosCajeros()
    {
        if(localizacionList != null)
        {
            for (int i = 1; i < localizacionList.size(); i++)
            {
                CajeroMarker = googleMap.addMarker(new MarkerOptions()
                        .position(puntosGPSCajeros.get(i))
                        .title(localizacionList.get(i).getName())
                        .snippet(localizacionList.get(i).getAddress())
                        .draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_gps_cajero)));


            }
            }
        }
    }



