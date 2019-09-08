package com.example.morpheus.proyectohackathon.Resources;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.example.morpheus.proyectohackathon.R;


public class CrearProgressDialog {

    ProgressDialog progressDialog;

    public ProgressDialog CargarProgressDialog(Context context){

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.item_progress_dialog);

        return progressDialog;
    }

}
