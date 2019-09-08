package com.example.morpheus.proyectohackathon.DAO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.example.morpheus.proyectohackathon.R;
import com.example.morpheus.proyectohackathon.Resources.Constantes;
import com.example.morpheus.proyectohackathon.Resources.Prueba;
import com.morpheus.morpheus.WebService.Peticion;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImagenDAO {

    private static ImagenDAO imagenDAO;
    HttpEntity httpEntity;

    public static ImagenDAO getInstance(){
        if (imagenDAO == null){
            imagenDAO = new ImagenDAO();
        }
        return imagenDAO;
    }
    public void verificarPersona(Context context, String fileName, DAO.OnResultadoConsulta<JSONObject>listener) {
        String url = Constantes.HOST_PUERTO + "aws/isperson";
        Log.i("url",url);


        Drawable drawable = context.getResources().getDrawable(R.drawable.melilentes_frontal);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] bitmapData = stream.toByteArray();

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      //  builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        if (bitmap != null){
            ContentType contentType = ContentType.create("multipart/form-data");
            builder.addBinaryBody("img", bitmapData, contentType, fileName);

            httpEntity = builder.build();
        }

    }
}
