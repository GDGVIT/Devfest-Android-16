package com.gdgvitvellore.devfest.Entity.Timeline.Fragments;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.gdgvitvellore.devfest.Control.Customs.SvgDecoder;
import com.gdgvitvellore.devfest.Control.Customs.SvgDrawableTranscoder;
import com.gdgvitvellore.devfest.Control.Customs.SvgSoftwareLayerSetter;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.io.InputStream;

/**
 * Created by AravindRaj on 10-10-2016.
 */

/**
 * This fragment displays image about a particular Phase in DevFest Timeline.
 */

public class TimelineDisplayFragment extends Fragment {


    private static final String TAG = TimelineDisplayFragment.class.getSimpleName();
    private ImageView displayImage;
    private String imageURL;

    public static TimelineDisplayFragment getNewInstance(){
        return new TimelineDisplayFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * View is created for the Fragment and Image is set.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_timeline_display,container, false);
        displayImage = (ImageView) view.findViewById(R.id.pagerImage);
        if(displayImage!=null){
            setImage(imageURL);
        }
        return view;
    }

    /**
     * Used to set the image from url received from the server to ImageView using Glide library.
     * @param url resource from where image can be fetched.
     */

    public void setImage(String url){
        Log.i(TAG, "setImage: "+url);
        GenericRequestBuilder requestBuilder = Glide.with(getContext())
                .using(Glide.buildStreamModelLoader(Uri.class, getContext()), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_gdg_icon)
                .error(R.drawable.ic_gdg)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());

        Uri uri = Uri.parse(url);
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(displayImage);
    }

    public void setImageUrl(String url){
        this.imageURL=url;
    }

}
