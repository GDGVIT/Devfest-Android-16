package com.gdgvitvellore.devfest.Entity.Coupons.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.gdgvitvellore.devfest.gdgdevfest.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

import link.fls.swipestack.SwipeStack;

/**
 * Created by Shuvam Ghosh on 10/12/2016.
 */

public class CouponsFragment extends Fragment implements SwipeStack.SwipeStackListener {

    private SwipeStack swipeStack;
    private SwipeStackAdapter adapter;
    private ArrayList<String> data;
    private ImageView qrCodeImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_coupons,container,false);
        init(root);
        setInit();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
    }

    private void init(View root) {
        swipeStack = (SwipeStack)root.findViewById(R.id.swipeStack);
        qrCodeImage=(ImageView)root.findViewById(R.id.qr_code);
    }

    private void setInit() {
        swipeStack.setListener(this);
        swipeStack.setAllowedSwipeDirections(SwipeStack.SWIPE_DIRECTION_ONLY_RIGHT);
    }

    private void setData() {
        data = new ArrayList<String>();
        data.add("Dinner");
        data.add("Snacks");
        data.add("Breakfast");
        data.add("Lunch");
        adapter=new SwipeStackAdapter(data);
        swipeStack.setAdapter(adapter);
        updateQrCode(0);
    }

    private void updateQrCode(int pos) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(data.get(pos), BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCodeImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewSwipedToLeft(int position) {

    }

    @Override
    public void onViewSwipedToRight(int position) {
        adapter.add(data.get(position),data.size());
        adapter.notifyDataSetChanged();
        updateQrCode(position==data.size()-1?0:position+1);
    }

    @Override
    public void onStackEmpty() {

    }


    public class SwipeStackAdapter extends BaseAdapter {

        private List<String> mData;

        public SwipeStackAdapter(List<String> data) {
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.api_view,parent,false);
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coupons_item, parent, false);
            TextView textViewCard = (TextView) convertView.findViewById(R.id.apiName);
            textViewCard.setText(mData.get(position));
            return convertView;
        }
        public void add(String text, int position){
            mData.add(position,text);
        }
    }


}
