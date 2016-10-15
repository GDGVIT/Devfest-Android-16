package com.gdgvitvellore.devfest.Boundary.Handlers.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.gdgvitvellore.devfest.Boundary.Customs.InputStreamVolleyRequest;
import com.gdgvitvellore.devfest.Boundary.Handlers.AppController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
            try {
                String correctLink=links[i].replace("htp","http");
                RequestFuture<byte[]> future = RequestFuture.newFuture();
                InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, correctLink, future, future, null);
                request.setRetryPolicy(new DefaultRetryPolicy(10000, 2, 1));
                AppController.getInstance().getRequestQueue().add(request);

                byte[] response = future.get(180, TimeUnit.SECONDS); // Blocks for at most 10 seconds.
                Bundle resultData = new Bundle();
                resultData.putInt("progress", 100);
                resultData.putInt("position", i);
                resultData.putByteArray("result", response);
                receiver.send(UPDATE_PROGRESS, resultData);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Bundle resultData = new Bundle();
                resultData.putInt("progress", 100);
                resultData.putInt("position", i);
                resultData.putByteArray("result", null);
                receiver.send(UPDATE_PROGRESS, resultData);
                break;
            }
            // Exception handling
            catch (ExecutionException e) {
                // Exception handling
                Bundle resultData = new Bundle();
                resultData.putInt("progress", 100);
                resultData.putInt("position", i);
                resultData.putByteArray("result", null);
                receiver.send(UPDATE_PROGRESS, resultData);
                e.printStackTrace();
                break;
            } catch (TimeoutException t) {
                t.printStackTrace();
                Bundle resultData = new Bundle();
                resultData.putInt("progress", 100);
                resultData.putInt("position", i);
                resultData.putByteArray("result", null);
                receiver.send(UPDATE_PROGRESS, resultData);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                Bundle resultData = new Bundle();
                resultData.putInt("progress", 100);
                resultData.putInt("position", i);
                resultData.putByteArray("result", null);
                receiver.send(UPDATE_PROGRESS, resultData);
                break;
            }
        }
    }


}
