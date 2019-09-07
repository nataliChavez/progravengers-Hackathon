package com.example.morpheus.proyectohackathon.Resources;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.morpheus.morpheus.WebService.Peticion;

import org.apache.http.HttpEntity;

import static com.example.morpheus.proyectohackathon.Resources.Constantes.MULTIPART_FORMDATA;


public class Prueba {

    public Prueba() {
    }

    public static class POST {
        private Context context;
        private String url;
        private HttpEntity httpEntity;


        public POST(Context context, String url, HttpEntity httpEntity) {
            this.context = context;
            this.url = url;
            this.httpEntity = httpEntity;
        }

        public void getResponse(final Peticion.OnPeticionListener<String> listener) {
            RequestQueue queue = Volley.newRequestQueue(this.context);
            StringRequest request = new StringRequest(1, this.url, new Response.Listener<String>() {
                public void onResponse(String response) {
                    listener.onSuccess(response);
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    listener.onFailed(error.toString(), error.networkResponse != null ? error.networkResponse.statusCode : 0);
                }
            }) {
                public String getBodyContentType() {
                    return MULTIPART_FORMDATA;
                }

            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1.0F));
            queue.add(request);
        }
    }
}
