package com.example.morpheus.proyectohackathon.Resources;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.morpheus.morpheus.WebService.Peticion;

import org.apache.http.HttpEntity;
import org.json.JSONObject;

import java.util.Map;


public class Prueba {

    public Prueba() {
    }
    public static class POST {
        private Context context;
        private String url;
        private JSONObject jsonObject;

        public POST(Context context, String url, JSONObject jsonObject) {
            this.context = context;
            this.url = url;
            this.jsonObject = jsonObject;
        }

        public void getResponse(final Peticion.OnPeticionListener<JSONObject> listener) {
            RequestQueue queue = Volley.newRequestQueue(this.context);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            listener.onSuccess(response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            listener.onFailed(error.toString(), error.networkResponse != null ? error.networkResponse.statusCode : 0);
                        }
                    }){
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1.0F));
            queue.add(request);
        }
    }
}
