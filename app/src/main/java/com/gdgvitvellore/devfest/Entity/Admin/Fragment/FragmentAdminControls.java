package com.gdgvitvellore.devfest.Entity.Admin.Fragment;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import org.json.JSONException;
import org.json.JSONObject;


public class FragmentAdminControls extends Fragment/*  implements QRCodeReaderView.OnQRCodeReadListener, View.OnClickListener*/{

    private static final String TAG = "TAG";
    private Button btnStartStop ;
    private TextView tvJsonView ;
    //private QRCodeReaderView qrCodeReaderView ;
    private Boolean isCameraActive = false ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStartStop = (Button) view.findViewById(R.id.fragment_admin_btn_qr_start_stop) ;
        tvJsonView  = (TextView) view.findViewById(R.id.fragment_admin_tv_json) ;
        //qrCodeReaderView = (QRCodeReaderView) view.findViewById(R.id.fragment_admin_qrcodereader_view) ;
        init() ;
    }

    private void init() {
        /*qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setQRDecodingEnabled(true) ;
        qrCodeReaderView.setAutofocusInterval(2000L);

        qrCodeReaderView.setTorchEnabled(false);

        qrCodeReaderView.setBackCamera();*/
    }

    /*@Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.i(TAG, "onQRCodeRead: " + text);

        tvJsonView.setText(text);
        try {
            JSONObject qrJson = new JSONObject(text) ;
            String emailId = qrJson.getString("emailID") ;
            String data = qrJson.getString("data") ;

            *//**
             * Make API function call
             * @ConnectAPI.java#qrCodeScan method line number 185
             * *//*

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fragment_admin_btn_qr_start_stop){
            if(isCameraActive){
                btnStartStop.setText("Start Decoding");
                qrCodeReaderView.setQRDecodingEnabled(false);
            }else {
                btnStartStop.setText("Stop Decoding");
                qrCodeReaderView.setQRDecodingEnabled(true);
            }
            isCameraActive = !isCameraActive ;
        }
    }*/
}
