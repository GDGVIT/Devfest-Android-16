package com.gdgvitvellore.devfest.Entity.SlotMachine.Fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bydavy.morpher.DigitalClockView;
import com.bydavy.morpher.font.DFont;
import com.gdgvitvellore.devfest.Boundary.API.ConnectAPI;
import com.gdgvitvellore.devfest.Boundary.Handlers.DataHandler;
import com.gdgvitvellore.devfest.Boundary.Handlers.Services.ImageDownloadService;
import com.gdgvitvellore.devfest.Control.Animations.SlotMachine.ObjectAnimations;
import com.gdgvitvellore.devfest.Control.Contracts.PrivateContract;
import com.gdgvitvellore.devfest.Control.Utils.NetworkUtils;
import com.gdgvitvellore.devfest.Control.Utils.ViewUtils;
import com.gdgvitvellore.devfest.Entity.Actors.BaseAPI;
import com.gdgvitvellore.devfest.Entity.Actors.SlotsResult;
import com.gdgvitvellore.devfest.Entity.Actors.User;
import com.gdgvitvellore.devfest.Entity.Authentication.Activities.AuthenticationActivity;
import com.gdgvitvellore.devfest.Entity.Main.Activities.MainActivity;
import com.gdgvitvellore.devfest.gdgdevfest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Shuvam Ghosh on 10/11/2016.
 *
 */

/**
 * Class for showing and implementing the interactive Slot Machine which the user can play with.
 *
 * The slot was intended to assign teams with APIs with which they as supposed to work as per the rules of the Hackathon
 * This class can be modified according to the requirements.
 */

public class SlotMachineFragment extends Fragment implements View.OnTouchListener, ConnectAPI.ServerAuthenticateListener, ViewUtils {

    private static final String TAG = SlotMachineFragment.class.getSimpleName();

    private ImageView trigger;
    private ImageView slot1, slot2, slot3;
    private LinearLayout triggerHolder, arrowLayout, apiNamesHolder;
    private DigitalClockView digitalClockView;
    private TextView apiName1, apiName2, apiName3;
    private FrameLayout root;
    private ProgressDialog progressDialog;

    private int min = 0;
    private int sec = 0;
    private PointF downPT = new PointF();
    private PointF startPT = new PointF();

    private CountDownTimer timer;
    private ConnectAPI connectAPI;

    private User user;
    private List<BaseAPI> apiList;
    private Bitmap[] bitmapArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_slot_machine, container, false);
        init(rootView);
        setInit();
        setData();
        return rootView;
    }

    private void init(View rootView) {
        trigger = (ImageView) rootView.findViewById(R.id.imageView);
        slot1 = (ImageView) rootView.findViewById(R.id.slot1);
        slot2 = (ImageView) rootView.findViewById(R.id.slot2);
        slot3 = (ImageView) rootView.findViewById(R.id.slot3);
        arrowLayout = (LinearLayout) rootView.findViewById(R.id.arrows_layout);
        triggerHolder = (LinearLayout) rootView.findViewById(R.id.trigger_holder);
        root = (FrameLayout) rootView.findViewById(R.id.root);
        progressDialog = new ProgressDialog(getContext());

        apiNamesHolder = (LinearLayout) rootView.findViewById(R.id.api_names_holder);
        apiName1 = (TextView) rootView.findViewById(R.id.api_name_1);
        apiName2 = (TextView) rootView.findViewById(R.id.api_name_2);
        apiName3 = (TextView) rootView.findViewById(R.id.api_name_3);

        digitalClockView = (DigitalClockView) rootView.findViewById(R.id.digitalClock);

        if (!MainActivity.ISGUEST) {
            user = DataHandler.getInstance(getContext()).getUser();
            if (user == null) {
                startActivity(new Intent(getActivity(), AuthenticationActivity.class));
                getActivity().finish();
            }
        }
        connectAPI = new ConnectAPI(getContext());
    }


    private void setInit() {
        digitalClockView.setFont(new DFont(70, 2));
        digitalClockView.setMorphingDuration(100);
        progressDialog.setCancelable(false);

        ObjectAnimations.triggerArrowAnimator(triggerHolder, 60).start();

        digitalClockView.setVisibility(View.INVISIBLE);

        trigger.setOnTouchListener(this);

        connectAPI.setServerAuthenticateListener(this);
        if (!NetworkUtils.hasConnectivity(getContext())) {
            trigger.setEnabled(false);
            showMessage("Connect to internet");
        }
    }

    /**
     * Getting the list pf API names and Setting the list of APIs
     * along with which a timer is set to disable the trigger for a certain amount of time starting from the current time immediately
     * after the slot machine stops
     */
    private void setData() {
        apiList = DataHandler.getInstance(getContext()).getAllApis();

        if (apiList != null && apiList.size() > 0) {
            prepareBitmaps();
            if (!(System.currentTimeMillis() - DataHandler.getInstance(getContext()).getSlotLastUsed() > PrivateContract.SLOT_WAIT_TIME)) {
                trigger.setEnabled(false);
                startWaitTimer(PrivateContract.SLOT_WAIT_TIME - (System.currentTimeMillis() - DataHandler.getInstance(getContext()).getSlotLastUsed()));
            } else {
                trigger.setEnabled(true);
            }
        } else {
            if (!MainActivity.ISGUEST)
                connectAPI.allApis(user.getEmail(), user.getAuthToken(), false);
            else
                connectAPI.allApis(null, null, true);
        }
    }

    /**
     * Randomly assigning API logos to the three slots.
     */
    private void prepareBitmaps() {
        trigger.setEnabled(false);
        bitmapArray = new Bitmap[apiList.size()];
        for (int i = 0; i < bitmapArray.length; i++) {
            bitmapArray[i] = BitmapFactory.decodeByteArray(apiList.get(i).getImage(), 0, apiList.get(i).getImage().length);
        }
        slot1.setImageBitmap(bitmapArray[new Random().nextInt(bitmapArray.length - 1)]);
        slot2.setImageBitmap(bitmapArray[new Random().nextInt(bitmapArray.length - 1)]);
        slot3.setImageBitmap(bitmapArray[new Random().nextInt(bitmapArray.length - 1)]);
        showMessage("Slot machine ready");
    }

    /**
     * This function
     * 1.) shows the sliding animation of logos in and out of the slots.
     * 2.) Uses CountDownTimer class to run for 10 secs and after every 100 milliseconds it randomly chooses an
     *      index from the apiList.
     *
     */

    private void animateSlots() {

        trigger.setEnabled(false);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimations.slotTranslateAnimation(slot1, 60),
                ObjectAnimations.slotTranslateAnimation(slot2, 80),
                ObjectAnimations.slotTranslateAnimation(slot3, 100),
                ObjectAnimations.slotTranslateAnimation(slot1, 60),
                ObjectAnimations.slotTranslateAnimation(slot2, 80),
                ObjectAnimations.slotTranslateAnimation(slot3, 100));
        set.start();


        CountDownTimer swapTimer = new CountDownTimer(10000, 100) {

            int i = new Random().nextInt(apiList.size() - 1);
            int j = new Random().nextInt(apiList.size() - 1);
            int k = new Random().nextInt(apiList.size() - 1);


            @Override
            public void onTick(long l) {

                if (l > 6000 && l <= 10000) //For the first 4 seconds
                {
                    i = i >= bitmapArray.length - 1 ? 0 : i + 1;
                    j = j >= bitmapArray.length - 1 ? 0 : j + 1;
                    k = k >= bitmapArray.length - 1 ? 0 : k + 1;
                    slot1.setImageBitmap(bitmapArray[i]);
                    slot2.setImageBitmap(bitmapArray[j]);
                    slot3.setImageBitmap(bitmapArray[k]);


                } else if (l <= 6000 && l > 2000) {
                    j = j >= bitmapArray.length - 1 ? 0 : j + 1;
                    k = k >= bitmapArray.length - 1 ? 0 : k + 1;
                    slot2.setImageBitmap(bitmapArray[j]);
                    slot3.setImageBitmap(bitmapArray[k]);

                } else {
                    k = k >= bitmapArray.length - 1 ? 0 : k + 1;
                    slot3.setImageBitmap(bitmapArray[k]);
                }
            }


            /**
             * Display the allotted API names below each slots after the slot machine stops
             */
            @Override
            public void onFinish() {
                startWaitTimer(PrivateContract.SLOT_WAIT_TIME);
                DataHandler.getInstance(getContext()).saveSlotLastUsed(System.currentTimeMillis());
                if (!MainActivity.ISGUEST)
                    connectAPI.slots(user.getEmail(), user.getAuthToken(), i == j + 1 && j + 1 == k + 1 ? true : false, "" + i + j + k);
                Log.i(TAG, "onFinish: " + i + ":" + j + ":" + k);
                apiName1.setText(apiList.get(i).getName());
                apiName2.setText(apiList.get(j).getName());
                apiName3.setText(apiList.get(k).getName());
                apiNamesHolder.setVisibility(View.VISIBLE);
                ObjectAnimations.fadeInAnimation(apiNamesHolder).start();
            }
        }.start();
    }


    /**
     * Fading in and out of the Digital Clock that appears after the stopping of Slot Machine.
     * Its waiting time is decided to be 30mins after the machine stops before which the trigger is locked.
     *
     * @param waitTime
     */
    private void startWaitTimer(long waitTime) {
        trigger.setEnabled(false);
        if (timer != null) {
            timer.cancel();
        }
        min = 0;
        sec = 0;

        ObjectAnimations.fadeInAnimation(digitalClockView).start();

        digitalClockView.setVisibility(View.VISIBLE);

        timer = new CountDownTimer(waitTime, 1000) {
            @Override
            public void onTick(long l) {

                min = (int) l / 1000 / 60;
                sec = ((int) l / 1000) % 60;
                digitalClockView.setTime((min / 10 != 0 ? min : "0" + min) + ":" + (sec / 10 != 0 ? sec : "0" + sec));
            }

            @Override
            public void onFinish() {
                digitalClockView.setTime("00:00");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimations.fadeOutAnimation(digitalClockView).start();
                    }
                }, 1000);
                if (NetworkUtils.hasConnectivity(getContext()))
                    trigger.setEnabled(true);
                else {
                    trigger.setEnabled(false);
                    showMessage("Connect to internet");
                }
            }
        }.start();
    }

    /**
     *
     * Handling touch and motion events of the slot trigger.
     * It includes:
     *          1. Initializing the initial position of the triger when the user touches it.
     *          2. Update the trigger position as it moves.
     *          3. Restricting the motion of the trigger only in the vertical orientation within a range of coordinates.
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int eid = event.getAction();
        switch (eid) {
            case MotionEvent.ACTION_DOWN:  //When the trigger is touched.
                ObjectAnimations.fadeOutAnimation(apiNamesHolder).start();
                ObjectAnimations.fadeOutAnimation(arrowLayout).start();
                downPT.x = event.getX();
                downPT.y = event.getY();
                startPT = new PointF(trigger.getX(), trigger.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                PointF mv = new PointF(event.getX() - downPT.x, event.getY() - downPT.y);
                Rect rec = new Rect();
                Rect rec2 = new Rect();
                triggerHolder.getLocalVisibleRect(rec2);
                trigger.getLocalVisibleRect(rec);
                if (startPT.y + mv.y >= rec2.top && startPT.y + mv.y <= rec2.height() - rec.height()) {
                    trigger.setY((int) (startPT.y + mv.y));
                    startPT = new PointF(trigger.getX(), trigger.getY());
                }
                break;
            case MotionEvent.ACTION_UP: //After the trigger is released.
                Animator animator = ObjectAnimations.flickerAnimation(trigger);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ObjectAnimations.fadeInAnimation(arrowLayout).start();  //Fading in of the arrows below the trigger
                        arrowLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animator.start();
                animateSlots();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtils.hasConnectivity(getContext()) && !digitalClockView.isMorphingAnimationRunning()) {
            trigger.setEnabled(true);
        } else {
            trigger.setEnabled(false);
            showMessage("Connect to internet");
        }
    }

    /**
     *
     * @param code Event code which specifies, call to which API has been made.
     */
    @Override
    public void onRequestInitiated(int code) {
        if (code == ConnectAPI.ALL_APIS_CODE) {
            progressDialog.setMessage("Fetching Slot...");
            progressDialog.show();
        } else if (code == ConnectAPI.SLOTS_CODE) {

        }
    }

    @Override
    public void onRequestCompleted(int code, Object result) {
        if (code == ConnectAPI.ALL_APIS_CODE) {
            startDownloadingImages((List<BaseAPI>) result);
        } else if (code == ConnectAPI.SLOTS_CODE) {
            SlotsResult slotsResult = (SlotsResult) result;
            if (slotsResult != null && slotsResult.getStatus() == 200) {
                DataHandler.getInstance(getContext()).saveSlot(slotsResult.getSlot());
                if (slotsResult.getSlot().getWinner() == "true") {
                    showMessage("You already won. Have fun!");
                }
            } else if (slotsResult.getStatus() == 400) {
                showMessage(slotsResult.getMessage());
            } else {
                showMessage(slotsResult.getMessage());
            }
        }
    }

    private void startDownloadingImages(List<BaseAPI> result) {
        apiList = result;
        showDownloadProgress();

        Intent intent = new Intent(getActivity(), ImageDownloadService.class);
        String[] links = new String[result.size()];
        for (int i = 0; i < result.size(); i++) {
            links[i] = result.get(i).getLogo();
        }
        intent.putExtra("links", links);
        intent.putExtra("receiver", new DownloadReceiver(new Handler()));
        getActivity().startService(intent);
    }

    private void showDownloadProgress() {
        progressDialog.cancel();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Slots...");
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
    }

    @Override
    public void onRequestError(int code, String message) {
        if (code == ConnectAPI.ALL_APIS_CODE) {
            progressDialog.cancel();
            showMessage(message);
        } else if (code == ConnectAPI.SLOTS_CODE) {
            showMessage(message);
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message, boolean showRetry) {
        if (showRetry) {

            Snackbar.make(root, message, Snackbar.LENGTH_SHORT).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDownloadingImages(apiList);
                }
            }).show();
        } else {
            Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErrorDialog() {

    }

    @SuppressLint("ParcelCreator")
    private class DownloadReceiver extends ResultReceiver {
        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == ImageDownloadService.UPDATE_PROGRESS) {
                Log.i(TAG, "onReceiveResult: " + resultData.toString());
                int progress = resultData.getInt("progress");
                int position = resultData.getInt("position");
                byte[] array = resultData.getByteArray("result");
                if (array != null) {
                    apiList.get(position).setImage(array);
                    int prog = (100 * (position + 1)) / apiList.size();
                    Log.i(TAG, "onReceiveResult: prog" + prog);
                    progressDialog.setProgress(prog);
                    if (position == apiList.size() - 1) {
                        progressDialog.cancel();
                        DataHandler.getInstance(getContext()).saveAllAPIs(apiList);
                        setData();
                    }
                } else {
                    showMessage("Error loading", true);
                    apiList = new ArrayList<>();
                    progressDialog.cancel();
                    trigger.setEnabled(false);
                }
            }
        }


    }
}
