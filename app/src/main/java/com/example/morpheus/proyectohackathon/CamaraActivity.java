package com.example.morpheus.proyectohackathon;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class CamaraActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST =1 ;
    private Button btn_hacerfoto;
    private ImageView img;


    private static final int TAKE_PICTURE = 0;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camara_activity);
        img = findViewById(R.id.imgMostrar);
        btn_hacerfoto = findViewById(R.id.btn_camara);
        //Añadimos el Listener Boton
        btn_hacerfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String filePath = Environment.getExternalStorageDirectory() + "imagen.jpg";

                Uri.fromFile(new File(filePath));

                //Validación de acuerdo al OS.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = Uri.parse(filePath);
                } else {
                    imageUri = Uri.fromFile(new File(filePath));
                }

                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);


                Intent cameraIntent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case CAMERA_REQUEST:

                Uri uri = imageUri;
               Log.i("requestCode",requestCode +"");
                Log.i("resultCode",resultCode +"");
                Log.i("imageUri",data +"");
                if (requestCode == Activity.RESULT_OK) {

                    getContentResolver().notifyChange(uri, null);

                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, uri);

                        img.setImageBitmap(bitmap);
                        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Error al cargar", Toast.LENGTH_SHORT).show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }


}