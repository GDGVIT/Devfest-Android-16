package com.gdgvitvellore.devfest.Entity.FAQ;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdgvitvellore.devfest.Control.Customs.QuestionsAdapter;
import com.gdgvitvellore.devfest.Entity.Actors.Faq;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

public class FAQFragment extends Fragment implements RecognitionListener, View.OnClickListener {

    private static final String TAG = "TAG";
    private ArrayList<Faq> questionList = new ArrayList<>();
    private RecyclerView rvExpanded ;
    private QuestionsAdapter questionsAdapter ;

    private TextToSpeech textToSpeech ;
    private SpeechRecognizer speechRecognizer = null ;
    private Intent recognizerIntent;

    private boolean isOn = false ;
    private FloatingActionButton voiceFab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_faq,container,false);
        init(rootView);
        setInit();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setData();
    }

    private void init(View rootView) {
        voiceFab = (FloatingActionButton) rootView.findViewById(R.id.activity_faq_fab_voice);
        rvExpanded = (RecyclerView) rootView.findViewById(R.id.content_scrolling_recyclerView) ;

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext()) ;

    }

    private void setInit() {
        voiceFab.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()) ;
        rvExpanded.setLayoutManager(layoutManager);

        speechRecognizer.setRecognitionListener(this);

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getContext().getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true) ;
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR)
                    textToSpeech.setLanguage(Locale.ENGLISH) ;
            }
        });

        getArrayData();
    }

    private void setData() {
        questionsAdapter = new QuestionsAdapter(questionList);
        rvExpanded.setAdapter(questionsAdapter);
    }


    private void getArrayData() {
        questionList.add(new Faq("Question 1"));
        questionList.add(new Faq("Question 2"));
        questionList.add(new Faq("Question 3"));
        questionList.add(new Faq("Question 4"));
        questionList.add(new Faq("Question 5"));
        questionList.add(new Faq("Question 6"));
        questionList.add(new Faq("Question 7"));
        questionList.add(new Faq("Question 8"));
        questionList.add(new Faq("Question 9"));
        questionList.add(new Faq("Question 10"));
    }


    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        try {
            Log.i(TAG, "onBufferReceived: "+new String(bytes,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> matches = bundle
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        for (String result : matches)
            Log.i(TAG, "onPartialResults: " + result);
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        String text ;

        ArrayList<String> matches = bundle
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        for (String result : matches) {

            text = result.toLowerCase();
            Log.i(TAG, "onPartialResults: " + text);
        }
    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                if(!isOn || speechRecognizer != null){
                    speechRecognizer.startListening(recognizerIntent);
                }
                else
                    speechRecognizer.stopListening();

                isOn = !isOn ;
                break;

        }
    }
}