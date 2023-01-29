package com.example.mobilebanking;

import static com.example.mobilebanking.MainActivity.pass;
import static com.example.mobilebanking.MainActivity.user;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MyHandlerThread extends HandlerThread {

    private Context context;
    private Handler mainHandler;
    String line;

    public MyHandlerThread(Context context, Handler mainHandler) {
        super("MyHandlerThread");
        this.context = context;
        this.mainHandler = mainHandler;
    }

    @Override
    public void run() {
        try{
            URL url = new URL("http://10.0.2.2:5000/loginAPI/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            Log.v("Login", "Valid Connection");

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username", user)
                            .appendQueryParameter("password", pass);

            String query = builder.build().getEncodedQuery();
            Log.v("Login", "Valid content values");

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            Log.v("Login", "Valid Output Stream");
            writer.write(query);
            writer.flush();
            writer.close();

            connection.connect();
            Log.v("Login", "Successful write");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Log.v("Login", "successful input stream");
            line = bufferedReader.readLine();
            Log.v("Login", line);
            line = line.trim();
            if(line.equals("Success")){
                mainHandler.sendEmptyMessage(0);
            } else{
                mainHandler.sendEmptyMessage(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Log.v("Login", "Finally");
        }
    }

}
