package com.gdgvitvellore.devfest.Boundary.Handlers.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gdgvitvellore.devfest.Boundary.Handlers.AppController;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Prince Bansal Local on 14-10-2016.
 */

public class ImageDownloadService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;
    private static final String TAG = ImageDownloadService.class.getSimpleName();

    public ImageDownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String[] links = intent.getStringArrayExtra("links");
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        for (int i = 0; i < links.length; i++) {
            //   try {
            //String urlString= URLEncoder.encode(links[i],"utf-8");
            StringRequest stringRequest = new StringRequest(Request.Method.GET, links[i],
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i(TAG, "onResponse: " + response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            AppController.getInstance().addToRequestQueue(stringRequest);
                /*URL url = new URL("http://gdgvitvellore.com/images/logo-big.png");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.connect();
                InputStream input = new BufferedInputStream(connection.getInputStream());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte data[] = new byte[2048];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, count);
                }
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                input.close();

                Bundle resultData = new Bundle();
                resultData.putInt("progress", 100);
                resultData.putInt("position",i);
                resultData.putByteArray("result", byteArrayOutputStream.toByteArray());

                receiver.send(UPDATE_PROGRESS, resultData);
*/

            /*} catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }


}
