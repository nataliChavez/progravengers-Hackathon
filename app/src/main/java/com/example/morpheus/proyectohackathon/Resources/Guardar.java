package com.example.morpheus.proyectohackathon.Resources;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Guardar {
    private Context context;
    private String NombreCarpeta = "/imagenesBVA";
    private String NombreArchivo = "imagen";

    public String GuardarImagen(Context context, Bitmap guardadImagen) {

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
            guardadImagen.compress(Bitmap.CompressFormat.JPEG, 30, fOut);
            fOut.flush();
            fOut.close();

            CrearArxhivo(file);

            ImagenGuardada();

        }
        catch(FileNotFoundException e) {

            NoSePuedeGardar();

            return null;

        } catch(IOException e) {

            NoSePuedeGardar();

            return null;
        }


        return file_path +"/"+NombreArchivo+""+nombreImagen+".jpg";
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