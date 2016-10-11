package com.gdgvitvellore.devfest.Entity.FAQ;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
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

public class ActivityFAQMain  extends AppCompatActivity implements RecognitionListener {

    private static final String TAG = "TAG";
    private ArrayList<FAQ> questionList = new ArrayList<>();
    private RecyclerView rvExpanded ;
    private QuestionsAdapter questionsAdapter ;

    private TextToSpeech textToSpeech ;
    private SpeechRecognizer speechRecognizer = null ;
    private Intent recognizerIntent;

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
                Log.i(TAG, "onClick:");
            }
        });

        rvExpanded = (RecyclerView) findViewById(R.id.content_scrolling_recyclerView) ;

        questionsAdapter = new QuestionsAdapter(questionList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()) ;
        rvExpanded.setLayoutManager(layoutManager);
        rvExpanded.setAdapter(questionsAdapter);

        getArrayData() ;

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

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}