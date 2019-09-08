package com.example.morpheus.proyectohackathon.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.morpheus.proyectohackathon.Models.LocalizacionBBVA;
import com.example.morpheus.proyectohackathon.R;

import java.util.List;

public class LocalizacionAdapter {
    //DECLARACION DE VARIABLES
    private List<LocalizacionBBVA> list;
    private Context context;

    public LocalizacionAdapter(Context context, List<LocalizacionBBVA> list)
    {
        this.list = list;
    }

    public int getCount()
    {
        return list.size();
    }

    public LocalizacionBBVA getItem(int i)
    {
        return list.get(i);
    }


    public long getItemId(int i)
    {
        return list.get(i).getPersonalVenta_id();
    }

    public View getView(int i, View view, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View vista = view;
        vista = inflater.inflate(R.layout., parent, false);

        TextView textView = vista.findViewById(R.id.txtNombreLocalizacion);
        textView.setText(list.get(i).getPersonalVenta_nombre());

        return vista;
    }
}
