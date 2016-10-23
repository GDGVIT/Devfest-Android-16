package com.gdgvitvellore.devfest.Entity.FAQ.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gdgvitvellore.devfest.Boundary.API.ConnectAPI;
import com.gdgvitvellore.devfest.Boundary.Handlers.DataHandler;
import com.gdgvitvellore.devfest.Control.Contracts.ErrorDefinitions;
import com.gdgvitvellore.devfest.Control.Customs.FAQExpandableAdapter;
import com.gdgvitvellore.devfest.Control.Customs.FaqFragmentManager;
import com.gdgvitvellore.devfest.Control.Exceptions.BindingException;
import com.gdgvitvellore.devfest.Control.Utils.ViewUtils;
import com.gdgvitvellore.devfest.Entity.Actors.ChatbotResult;
import com.gdgvitvellore.devfest.Entity.Actors.FAQ;
import com.gdgvitvellore.devfest.Entity.Actors.FAQResult;
import com.gdgvitvellore.devfest.Entity.Main.Activities.MainActivity;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FAQFragment extends Fragment implements
        RecognitionListener, View.OnClickListener, ConnectAPI.ServerAuthenticateListener, ViewUtils {

    private static final String TAG = FAQFragment.class.getSimpleName();
    private static final int REQUEST_AUDIO_PERMS = 1;

    private List<FAQ> faqList;
    private FAQExpandableAdapter faqExpandableAdapter;

    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer = null;
    private Intent recognizerIntent;

    private RecyclerView rvExpanded;
    private FloatingActionButton voiceFab;
    private EditText etQuestion;
    private ProgressDialog progressDialog;
    private CoordinatorLayout root;

    private boolean isOn = false;
    private int stateId = 101;

    public String AUDIO_PERMS = Manifest.permission.RECORD_AUDIO;
    private String email, auth;

    private ConnectAPI connectAPI;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);
        init(rootView);
        setInit();
        return rootView;
    }

    private void init(View rootView) {
        voiceFab = (FloatingActionButton) rootView.findViewById(R.id.activity_faq_fab_voice);
        rvExpanded = (RecyclerView) rootView.findViewById(R.id.content_scrolling_recyclerView);
        progressDialog = new ProgressDialog(getContext());
        root = (CoordinatorLayout) rootView.findViewById(R.id.fragment_faq_root);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());

        etQuestion = (EditText) rootView.findViewById(R.id.fragment_faq_et_question);
        connectAPI = new ConnectAPI(getActivity());

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        if (!MainActivity.ISGUEST) {
            email = DataHandler.getInstance(getContext()).getUser().getEmail();
            auth = DataHandler.getInstance(getContext()).getUser().getAuthToken();
        }

    }

    private void setInit() {

        connectAPI.setServerAuthenticateListener(this);
        etQuestion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(etQuestion.getCompoundDrawables()[DRAWABLE_RIGHT]!=null)
                    if(event.getRawX() >= (etQuestion.getRight() - etQuestion.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        etQuestion.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
        etQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        etQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    stateId = 201;
                    voiceFab.setImageResource(R.drawable.ic_done);
                    etQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_clear,0);
                    //change button
                } else {
                    stateId = 101;
                    //set it back to microphone

                    etQuestion.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    voiceFab.setImageResource(R.drawable.ic_mic_black_24px);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        voiceFab.setOnClickListener(this);

        rvExpanded.setLayoutManager(new LinearLayoutManager(getContext()));

        /**Speech code*/
        speechRecognizer.setRecognitionListener(this);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getContext().getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR)
                    textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });
    }

    private void setData() {
        faqList = DataHandler.getInstance(getContext()).getFAQ();
        if (faqList != null && faqList.size() > 0) {
            faqExpandableAdapter = new FAQExpandableAdapter(getContext(), faqList);
            rvExpanded.setAdapter(faqExpandableAdapter);
        } else {
            connectAPI.faq(email, auth, MainActivity.ISGUEST);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setData();
    }


    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.i(TAG, "onReadyForSpeech: ");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech: ");
    }

    @Override
    public void onRmsChanged(float v) {
        Log.i(TAG, "onRmsChanged: ");
    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        try {
            Log.i(TAG, "onBufferReceived: " + new String(bytes, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(TAG, "onEndOfSpeech: ");
    }

    @Override
    public void onError(int i) {
        Log.i(TAG, "onError: " + i);
    }

    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> matches = bundle
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        for (String result : matches)
            Log.i(TAG, "A FINAL RESULTS: " + result);
        /*TODO: Select the best text and make network call*/

        etQuestion.setText(matches.get(0));
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        String text;

        ArrayList<String> matches = bundle
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        Log.i(TAG, "onPartialResults: sieze"+matches.size());
        for (String result : matches) {

            text = result.toLowerCase();
            etQuestion.setText(text);
            Log.i(TAG, "onPartialResults: " + text);

        }
    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    public void displayChatbotResponse(ChatbotResult chatbotResult) {

        if (chatbotResult.getStatus() == ErrorDefinitions.CODE_SUCCESS) {
            Log.i(TAG, "displayResponse: " + chatbotResult.getAnswer());

            AlertDialog dialogSuccess = new AlertDialog.Builder(getActivity())
                    .create();
            dialogSuccess.setCancelable(false);
            dialogSuccess.setTitle(etQuestion.getText().toString());
            dialogSuccess.setMessage(chatbotResult.getAnswer());
            dialogSuccess.setButton(DialogInterface.BUTTON_POSITIVE, "Got it!", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogSuccess.show();

        } else {
            AlertDialog dialogFailure = new AlertDialog.Builder(getActivity())
                    .create();
            dialogFailure.setCancelable(false);
            dialogFailure.setTitle("Error");
            dialogFailure.setMessage("Sorry we couldn't really get the answer for you.");
            dialogFailure.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again!", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogFailure.show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_faq_fab_voice:

                if (stateId == 101) {
                    if (!hasPermissionsGranted(AUDIO_PERMS)) {
                        requestAudioPermissions();
                    } else {
                        if (!isOn || speechRecognizer != null) {
                            Log.i(TAG, "onClick: IS ON NOW");
                                speechRecognizer.startListening(recognizerIntent);

                            voiceFab.setImageResource(R.drawable.ic_record_voice_over);
                        } else {
                            Log.i(TAG, "onClick: IS OFF NOW");

                            assert speechRecognizer != null;
                            speechRecognizer.stopListening();
                            voiceFab.setImageResource(R.drawable.ic_mic_black_24px);
                        }

                        isOn = !isOn;
                    }
                } else if (stateId == 201) {
                    try {
                        connectAPI.chatBot(email, etQuestion.getText().toString());
                    } catch (BindingException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }


    private boolean hasPermissionsGranted(String permission) {
        return ActivityCompat.checkSelfPermission(this.getActivity(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestAudioPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), AUDIO_PERMS)) {
            showPermissionRationale();
        } else {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{AUDIO_PERMS}, REQUEST_AUDIO_PERMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_AUDIO_PERMS) {
            if (grantResults.length == 1) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        showErrorDialog(getString(R.string.permission_request));
                        break;
                    }
                }
            } else {
                showErrorDialog(getString(R.string.permission_request));
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onRequestInitiated(int code) {
        progressDialog.setMessage("Loading FAQs...");
        progressDialog.show();
    }

    @Override
    public void onRequestCompleted(int code, Object result) {

        progressDialog.cancel();
        if (code == ConnectAPI.FAQ_CODE) {
            FAQResult faqresult = (FAQResult) result;
            Log.d("FAQ:", result.toString());
            if (faqresult != null) {

                if (faqresult.getStatus() == ErrorDefinitions.CODE_SUCCESS) {
                    DataHandler.getInstance(getActivity()).saveFAQ(faqresult.getFaqs());
                    Log.d("FAQ from Realm: ", DataHandler.getInstance(getActivity()).getFAQ().toString());
                    setData();
                } else {
                    showMessage(faqresult.getMessage());
                }
            }
        } else if (code == ConnectAPI.CHATBOT_CODE) {
            ChatbotResult chatbotResult = (ChatbotResult) result;
            if (chatbotResult != null) {
                if (chatbotResult.getStatus() == ErrorDefinitions.CODE_SUCCESS) {
                    displayChatbotResponse(chatbotResult);
                }
            }
        }
    }

    @Override
    public void onRequestError(int code, String message) {
        progressDialog.cancel();
        if (code == ConnectAPI.FAQ_CODE) {
            showMessage(message);
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message, boolean showAction) {

    }

    @Override
    public void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    private void showPermissionRationale() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Access to microphone")
                .setMessage(R.string.permission_request)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ActivityCompat.requestPermissions(getActivity(), new String[]{AUDIO_PERMS},
                                REQUEST_AUDIO_PERMS);
                    }
                });
        builder.create().show();
    }
}