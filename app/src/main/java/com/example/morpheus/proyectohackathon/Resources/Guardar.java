package com.example.morpheus.proyectohackathon.Resources;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Guardar {
    private Context context;
    private String NombreCarpeta = "/Nuevacarpeta";
    private String NombreArchivo = "imagen";

    public void GuardarImagen(Context context, Bitmap ImageToSave) {
        this.context = context;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NombreCarpeta;
        String nombreImagen = ObtenerFechaYHora();
        File dir = new File(file_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, NombreArchivo + nombreImagen + ".jpg");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            CrearArxhivo(file);
            ImagenGuardada();
        }
        catch(FileNotFoundException e) {
            NoSePuedeGardar();
        }
        catch(IOException e) {
            NoSePuedeGardar();
        }
    }

    //Asegúrese de que el archivo esté creado y luego póngalo disponible
    private void CrearArxhivo(File file){
        MediaScannerConnection.scanFile(context,
                new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }
    private String ObtenerFechaYHora() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
    private void NoSePuedeGardar() {
        Toast.makeText(context, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }
    private void ImagenGuardada() {
        Toast.makeText(context, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }
}