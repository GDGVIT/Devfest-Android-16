package com.gdgvitvellore.devfest.Entity.FAQ.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdgvitvellore.devfest.Boundary.API.ConnectAPI;
import com.gdgvitvellore.devfest.Boundary.Handlers.DataHandler;
import com.gdgvitvellore.devfest.Control.Contracts.ErrorDefinitions;
import com.gdgvitvellore.devfest.Control.Customs.FAQExpandableAdapter;
import com.gdgvitvellore.devfest.Entity.Actors.FAQ;
import com.gdgvitvellore.devfest.Entity.Actors.FAQResult;
import com.gdgvitvellore.devfest.Entity.Events.ChatBotApiResponse;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

public class FAQFragment extends Fragment implements RecognitionListener, View.OnClickListener, ConnectAPI.ServerAuthenticateListener {

    private static final int REQUEST_AUDIO_PERMS = 1;
    private static final String TAG = "TAG";
    private ArrayList<FAQ> questionList = new ArrayList<>();
    private RecyclerView rvExpanded ;
    private FAQExpandableAdapter faqExpandableAdapter ;

    private TextToSpeech textToSpeech ;
    private SpeechRecognizer speechRecognizer = null ;
    private Intent recognizerIntent;

    private boolean isOn = false ;
    private FloatingActionButton voiceFab;

    public String[] AUDIO_PERMS = {
            Manifest.permission.RECORD_AUDIO,
    } ;

    android.app.FragmentManager fragmentManager ;

    private ConnectAPI connectAPI;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_faq,container,false);
        init(rootView);
        setInit();
        fetchData();
        return rootView;
    }

    private void fetchData() {

        connectAPI.faq(DataHandler.getInstance(getActivity()).getUserMail(),DataHandler.getInstance(getActivity()).getAuthToken());

    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
        setData();

    }

    private void init(View rootView) {
        voiceFab = (FloatingActionButton) rootView.findViewById(R.id.activity_faq_fab_voice);
        rvExpanded = (RecyclerView) rootView.findViewById(R.id.content_scrolling_recyclerView) ;

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext()) ;

        connectAPI = new ConnectAPI(getActivity());
        connectAPI.setServerAuthenticateListener(this);
    }

    private void setInit() {

        fragmentManager = new android.app.FragmentManager() {
            @Override
            public FragmentTransaction beginTransaction() {
                return null;
            }

            @Override
            public boolean executePendingTransactions() {
                return false;
            }

            @Override
            public android.app.Fragment findFragmentById(int i) {
                return null;
            }

            @Override
            public android.app.Fragment findFragmentByTag(String s) {
                return null;
            }

            @Override
            public void popBackStack() {

            }

            @Override
            public boolean popBackStackImmediate() {
                return false;
            }

            @Override
            public void popBackStack(String s, int i) {

            }

            @Override
            public boolean popBackStackImmediate(String s, int i) {
                return false;
            }

            @Override
            public void popBackStack(int i, int i1) {

            }

            @Override
            public boolean popBackStackImmediate(int i, int i1) {
                return false;
            }

            @Override
            public int getBackStackEntryCount() {
                return 0;
            }

            @Override
            public BackStackEntry getBackStackEntryAt(int i) {
                return null;
            }

            @Override
            public void addOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {

            }

            @Override
            public void removeOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {

            }

            @Override
            public void putFragment(Bundle bundle, String s, android.app.Fragment fragment) {

            }

            @Override
            public android.app.Fragment getFragment(Bundle bundle, String s) {
                return null;
            }

            @Override
            public android.app.Fragment.SavedState saveFragmentInstanceState(android.app.Fragment fragment) {
                return null;
            }

            @Override
            public boolean isDestroyed() {
                return false;
            }

            @Override
            public void dump(String s, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strings) {

            }
        };

        voiceFab.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()) ;
        rvExpanded.setLayoutManager(layoutManager);




        /**Speech code*/
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
        /*questionsAdapter = new QuestionsAdapter(questionList);
        rvExpanded.setAdapter(questionsAdapter);*/

        faqExpandableAdapter = new FAQExpandableAdapter(getContext(), questionList) ;
        rvExpanded.setAdapter(faqExpandableAdapter);
    }


    private void getArrayData() {
        questionList.add(new FAQ("What is a hackathon?", "A hackathon is an intense session that brings together computer programmers, designers, and specialists to build a product within a stipulated period of time."));
        questionList.add(new FAQ("Who should attend?", "Anybody who has a knack for solving problems, likes to code, design and develop unique ideas and models are welcome to participate at the hackathon."));
        questionList.add(new FAQ("What is the maximum team size?", "A maximum of 3 members per team will be taken on a first come first serve basis. A total of 60 teams will be admitted to the hackathon."));
        questionList.add(new FAQ("What is the judging criteria?", "The complete end product that has been developed over the period of the hackathon is judged to see whether any new innovative idea has been brought to the table. The design and implementation of the code are also taken into account. The presentation of the product is also an important factor."));
        questionList.add(new FAQ("What should I bring?", "All the participants are required to bring their laptops and any material that the team requires to use during the hackathon. No material for the hack will be provided."));
        questionList.add(new FAQ("How do I form a team?", "If you do not already have a team you can come to the event and we will assign you to a team to participate for the hackathon."));
        questionList.add(new FAQ("Will there be provision for food?", "Yes, dinner that includes pizzas from dominos will be provided free of cost. The breakfast and lunch on 16th will not be provided till further notice."));
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
            Log.i(TAG, "A FINAL RESULTS: " + result);
        /*TODO: Select the best text and make network call*/

        ConnectAPI connectAPI = new ConnectAPI(this.getActivity()) ;
        connectAPI.chatBot(matches.get(0));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void displayResponse(ChatBotApiResponse chatBotApiResponse){

        if (chatBotApiResponse.getResponseCode() == 200){
            Log.i(TAG, "displayResponse: " + chatBotApiResponse.getResponseAnswer());

        }else {
            //Error


        }

    }


    @Override
    public void onPause() {
        speechRecognizer.destroy();
        super.onPause();
    }

    @Override
    public void onStop() {

        EventBus.getDefault().unregister(this);
        super.onStop();
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
            case R.id.activity_faq_fab_voice:
                if(!hasPermissionsGranted(AUDIO_PERMS)){
                    requestAudioPermissions();
                }else {
                    if(!isOn || speechRecognizer != null){
                        Log.i(TAG, "onClick: IS ON NOW");
                        speechRecognizer.startListening(recognizerIntent);
                    }
                    else {
                        Log.i(TAG, "onClick: IS OFF NOW");

                        assert speechRecognizer != null;
                        speechRecognizer.stopListening();
                    }

                    isOn = !isOn ;
                }
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int flag = 0 ;

        if (requestCode == REQUEST_AUDIO_PERMS) {
            if (grantResults.length == AUDIO_PERMS.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        ErrorDialog.newInstance(getString(R.string.permission_request))
                                .show(fragmentManager, TAG);
                        break;
                    }
                    flag++ ;
                }
            } else {
                ErrorDialog.newInstance(getString(R.string.permission_request))
                        .show(fragmentManager, TAG);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean hasPermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this.getActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestAudioPermissions() {
        if (shouldShowRequestPermissionRationale(AUDIO_PERMS)) {
            new ConfirmationDialog().show(fragmentManager, TAG);
        } else {
            ActivityCompat.requestPermissions(this.getActivity(), AUDIO_PERMS, REQUEST_AUDIO_PERMS);
        }
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), permission)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestInitiated(int code) {

    }

    @Override
    public void onRequestCompleted(int code, Object result) {
        if (code == ConnectAPI.FAQ_CODE){
            FAQResult faqresult = (FAQResult) result;
            Log.d("FAQ:", result.toString());
            if (faqresult != null){

                if (faqresult.getStatus() == ErrorDefinitions.CODE_SUCCESS){
                    DataHandler.getInstance(getActivity()).saveFAQ(faqresult.getFaqs());
                    Log.d("FAQ from Realm: ", DataHandler.getInstance(getActivity()).getFAQ().toString());
                }
                else {
//                    showMessage(ErrorDefinitions.getMessage(faqresult.getStatus()));
                }

            }
        }
    }

    @Override
    public void onRequestError(int code, String message) {

    }

    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message) {
            ErrorDialog dialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Activity activity = getActivity();
            return new AlertDialog.Builder(activity)
                    .setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .create();

        }

    }

    public static class ConfirmationDialog extends DialogFragment {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Activity parent = getActivity();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.permission_request)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_AUDIO_PERMS);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                    .create();
        }

    }
}