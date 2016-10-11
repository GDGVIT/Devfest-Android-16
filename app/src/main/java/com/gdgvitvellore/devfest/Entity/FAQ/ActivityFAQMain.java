package com.gdgvitvellore.devfest.Entity.FAQ;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gdgvitvellore.devfest.Control.Customs.QuestionsAdapter;
import com.gdgvitvellore.devfest.Entity.Actors.FAQ;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityFAQMain  extends AppCompatActivity implements RecognitionListener {

    private static final String TAG = "TAG";
    private ArrayList<FAQ> questionList = new ArrayList<>();
    private RecyclerView rvExpanded ;
    private QuestionsAdapter questionsAdapter ;

    private TextToSpeech textToSpeech ;
    private SpeechRecognizer speechRecognizer = null ;
    private Intent recognizerIntent;

    private boolean isOn = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_mrmojo);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_faq_fab_voice);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isOn || speechRecognizer != null){
                    speechRecognizer.startListening(recognizerIntent);
                }
                else
                    speechRecognizer.stopListening();

                isOn = !isOn ;
            }
        });

        rvExpanded = (RecyclerView) findViewById(R.id.content_scrolling_recyclerView) ;

        questionsAdapter = new QuestionsAdapter(questionList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()) ;
        rvExpanded.setLayoutManager(layoutManager);
        rvExpanded.setAdapter(questionsAdapter);

        getArrayData() ;
        voiceInit() ;
    }

    private void voiceInit() {

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this) ;
        speechRecognizer.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true) ;
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        /*recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);*/

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR)
                    textToSpeech.setLanguage(Locale.ENGLISH) ;
            }
        });
    }

    private void getArrayData() {
        questionList.add(new FAQ("Question 1"));
        questionList.add(new FAQ("Question 2"));
        questionList.add(new FAQ("Question 3"));
        questionList.add(new FAQ("Question 4"));
        questionList.add(new FAQ("Question 5"));
        questionList.add(new FAQ("Question 6"));
        questionList.add(new FAQ("Question 7"));
        questionList.add(new FAQ("Question 8"));
        questionList.add(new FAQ("Question 9"));
        questionList.add(new FAQ("Question 10"));
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
}