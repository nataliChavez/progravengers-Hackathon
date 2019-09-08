package com.example.morpheus.proyectohackathon.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.morpheus.proyectohackathon.R;

public class MenuInicial extends Fragment implements View.OnClickListener {


    Button btnLocalizacion,btnPerfil;
    FragmentManager fragmentMang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_inicial_fragment,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentMang = getActivity().getSupportFragmentManager();
        btnLocalizacion = view.findViewById(R.id.btnSucursales);
        btnPerfil = view.findViewById(R.id.btnPerfil);

        btnPerfil.setOnClickListener(this);
        btnLocalizacion.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        Fragment fragment = null;
        switch (v.getId()){

            case R.id.btnSucursales:

                fragment = new localizacionFragment();
                fragmentMang.beginTransaction().replace(R.id.contentPrincial, fragment).commit();


                break;

            case R.id.btnPerfil:

                fragment = new perfilFragment();
                fragmentMang.beginTransaction().replace(R.id.contentPrincial, fragment).commit();

                break;



        }


    }
}
