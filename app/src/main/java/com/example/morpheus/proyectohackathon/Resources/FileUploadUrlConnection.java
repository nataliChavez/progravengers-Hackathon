package com.example.morpheus.proyectohackathon.Resources;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileUploadUrlConnection extends AsyncTask<String, String, String> {
    private Context context;
    private String url;
    private List<File> files;

    public FileUploadUrlConnection(Context context, String url, List<File> files) {
        this.context = context;
        this.url = url;
        this.files = files;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);

        HttpClientParams connectionParams = new HttpClientParams();

        try {
            Part[] parts = new Part[files.size()];

            for (int i = 0; i < files.size(); i++) {
                Part part = new FilePart(files.get(i).getName(), files.get(i));
                parts[i] = part;
            }

            MultipartRequestEntity entity = new MultipartRequestEntity(parts, connectionParams);
            post.setRequestHeader("multipart/form-data","imgs");
            post.setRequestEntity(entity);

            int statusCode = client.executeMethod(post);
            String response = post.getResponseBodyAsString();

            Log.i("response",String.valueOf(statusCode));

            if (statusCode == 200) {
                return response;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
