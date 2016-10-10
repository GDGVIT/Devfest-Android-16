


package com.gdgvitvellore.devfest.Boundary.API;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gdgvitvellore.devfest.Boundary.Handlers.AppController;
import com.gdgvitvellore.devfest.Boundary.Handlers.DataHandler;
import com.gdgvitvellore.devfest.Control.Contracts.APIContract;
import com.gdgvitvellore.devfest.Control.Contracts.ErrorDefinitions;
import com.gdgvitvellore.devfest.Control.Customs.CustomTypeAdapter;
import com.gdgvitvellore.devfest.Entity.Actors.APIAssignedResult;
import com.gdgvitvellore.devfest.Entity.Actors.FAQResult;
import com.gdgvitvellore.devfest.Entity.Actors.LoginResult;
import com.gdgvitvellore.devfest.Entity.Actors.LogoutResult;
import com.gdgvitvellore.devfest.Entity.Actors.SlotsResult;
import com.gdgvitvellore.devfest.Entity.Actors.SpeakersResult;
import com.gdgvitvellore.devfest.Entity.Actors.TimelineResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * This is API Boundary class. All network calls should be made in this class with uniform format.
 * All calls are made using Volley and will be asynchronous. Synchronous calls can be used when necessary.
 * No UI elements should be altered here.
 * Every response will make the callback to the corresponding calling class using the interface {@link ServerAuthenticateListener}
 * Make the instance of {@link ConnectAPI} in your View and set the {@link ServerAuthenticateListener} callback.
 */


public class ConnectAPI {

    //Constants
    public static final int LOGIN_CODE = 1;
    public static final int LOGOUT_CODE = 2;
    public static final int TIMELINE_CODE = 3;
    public static final int SPEAKERS_CODE = 4;
    public static final int FAQ_CODE = 5;
    public static final int APIS_CODE = 6;
    public static final int SLOTS_CODE = 7;

    private static final String TAG = ConnectAPI.class.getSimpleName();
    private long REQUEST_TIMEOUT = 30000;

    private AppController appController;
    private ServerAuthenticateListener mServerAuthenticateListener;
    private DataHandler dataHandler;

    Context context;
    /**
     * Constructor for ConnectAPI
     * Initialize all class attributes here.
     */
    public ConnectAPI(Context context) {
        appController = AppController.getInstance();
        dataHandler = DataHandler.getInstance(appController.getApplicationContext());
        this.context=context;
    }

    /**
     * Used for login through the API
     * @param email The email address of the user
     * @param password Password of the user
     */

    public void login(final String email, final String password) {

        String url = APIContract.getLoginUrl();
        mServerAuthenticateListener.onRequestInitiated(LOGIN_CODE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "Login:onResponse: " + response);
                        try {
                            if(validateResponse(response)) {
                                LoginResult loginResult = CustomTypeAdapter.typeRealmString().fromJson(response, LoginResult.class);
                                DataHandler.getInstance(context).saveUser(loginResult.getUser());
                                DataHandler.getInstance(context).saveTeam(loginResult.getTeam());
                                mServerAuthenticateListener.onRequestCompleted(LOGIN_CODE, loginResult);
                            }else{
                                mServerAuthenticateListener.onRequestError(LOGIN_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mServerAuthenticateListener.onRequestError(LOGIN_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "Login:error:"+error.getMessage());
                mServerAuthenticateListener.onRequestError(LOGIN_CODE, error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return APIContract.getLoginParams(email,password);
            }
        };
        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy((int) REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(postRequest);
    }







    public void timeline(final String email, final String auth_token) {

        String url = APIContract.getTimelineUrl();
        mServerAuthenticateListener.onRequestInitiated(TIMELINE_CODE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "TimelineResult:onResponse: " + response);
                        try {
                            if (validateResponse(response)) {
                                TimelineResult timelineResult = CustomTypeAdapter.typeRealmString().fromJson(response, TimelineResult.class);
                                DataHandler.getInstance(context).saveTimeline(timelineResult.getTimeline());

                                mServerAuthenticateListener.onRequestCompleted(TIMELINE_CODE, timelineResult);
                            } else {
                                mServerAuthenticateListener.onRequestError(TIMELINE_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mServerAuthenticateListener.onRequestError(TIMELINE_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "TimelineResult:error:" + error.getMessage());
                mServerAuthenticateListener.onRequestError(TIMELINE_CODE, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return APIContract.getTimelineParams(email, auth_token);
            }
        };

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy((int) REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(postRequest);
    }



    public void speakers(final String email, final String auth_token) {

        String url = APIContract.getSpeakersUrl();
        mServerAuthenticateListener.onRequestInitiated(SPEAKERS_CODE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "SpeakersResult:onResponse: " + response);
                        try {
                            if (validateResponse(response)) {
                                SpeakersResult speakersResult = CustomTypeAdapter.typeRealmString().fromJson(response, SpeakersResult.class);
                                DataHandler.getInstance(context).saveSpeakers(speakersResult.getSpeakers());

                                mServerAuthenticateListener.onRequestCompleted(SPEAKERS_CODE, speakersResult);
                            } else {
                                mServerAuthenticateListener.onRequestError(SPEAKERS_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mServerAuthenticateListener.onRequestError(SPEAKERS_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "SpeakersResult:error:" + error.getMessage());
                mServerAuthenticateListener.onRequestError(SPEAKERS_CODE, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return APIContract.getSpeakersParams(email, auth_token);
            }
        };

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy((int) REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(postRequest);
    }







    public void faq(final String email, final String auth_token) {

        String url = APIContract.getFAQUrl();
        mServerAuthenticateListener.onRequestInitiated(FAQ_CODE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "FAQResult:onResponse: " + response);
                        try {
                            if (validateResponse(response)) {
                                FAQResult faqResult = CustomTypeAdapter.typeRealmString().fromJson(response, FAQResult.class);
                                //DataHandler.getInstance(context).saveFAQ(faqResult.getFaqs());

                                mServerAuthenticateListener.onRequestCompleted(FAQ_CODE, faqResult);
                            } else {
                                mServerAuthenticateListener.onRequestError(FAQ_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mServerAuthenticateListener.onRequestError(FAQ_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "FAQResult:error:" + error.getMessage());
                mServerAuthenticateListener.onRequestError(FAQ_CODE, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return APIContract.getFAQParams(email, auth_token);
            }
        };

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy((int) REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(postRequest);
    }


    public void apis(final String email, final String auth_token) {

        String url = APIContract.getAPIAssignedUrl();
        mServerAuthenticateListener.onRequestInitiated(APIS_CODE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "APIResult:onResponse: " + response);
                        try {
                            if (validateResponse(response)) {
                                APIAssignedResult apiAssignedResult = CustomTypeAdapter.typeRealmString().fromJson(response, APIAssignedResult.class);
                                DataHandler.getInstance(context).saveApi(apiAssignedResult.getApis());

                                mServerAuthenticateListener.onRequestCompleted(APIS_CODE, apiAssignedResult);
                            } else {
                                mServerAuthenticateListener.onRequestError(APIS_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mServerAuthenticateListener.onRequestError(APIS_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "APIResult:error:" + error.getMessage());
                mServerAuthenticateListener.onRequestError(APIS_CODE, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return APIContract.getAPIParams(email, auth_token);
            }
        };

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy((int) REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(postRequest);
    }








    public void slots(final String email, final String auth_token,final String number_of_tries,final String slots) {

        String url = APIContract.getSlotUrl();
        mServerAuthenticateListener.onRequestInitiated(SLOTS_CODE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "SLOTSResult:onResponse: " + response);
                        try {
                            if (validateResponse(response)) {
                                SlotsResult slotsResult = CustomTypeAdapter.typeRealmString().fromJson(response, SlotsResult.class);
                                DataHandler.getInstance(context).saveSlots(slotsResult.getSlots());

                                mServerAuthenticateListener.onRequestCompleted(SLOTS_CODE, slotsResult);
                            } else {
                                mServerAuthenticateListener.onRequestError(SLOTS_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mServerAuthenticateListener.onRequestError(SLOTS_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "SLOTSResult:error:" + error.getMessage());
                mServerAuthenticateListener.onRequestError(SLOTS_CODE, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return APIContract.getSlotParams(email, auth_token,number_of_tries,slots);
            }
        };

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy((int) REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(postRequest);
    }








    public void logout(final String email, final String auth_token) {

        String url = APIContract.getLogoutUrl();
        mServerAuthenticateListener.onRequestInitiated(LOGOUT_CODE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "LOGOUTResult:onResponse: " + response);
                        try {
                            if (validateResponse(response)) {
                                LogoutResult logoutResult = CustomTypeAdapter.typeRealmString().fromJson(response, LogoutResult.class);
                                DataHandler.getInstance(context).saveLogout(logoutResult);

                                mServerAuthenticateListener.onRequestCompleted(LOGOUT_CODE, logoutResult);
                            } else {
                                mServerAuthenticateListener.onRequestError(LOGOUT_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mServerAuthenticateListener.onRequestError(LOGOUT_CODE, ErrorDefinitions.getMessage(ErrorDefinitions.CODE_WRONG_FORMAT));
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "LOGOUTResult:error:" + error.getMessage());
                mServerAuthenticateListener.onRequestError(LOGOUT_CODE, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return APIContract.getLogoutParams(email,auth_token);
            }
        };

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy((int) REQUEST_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(postRequest);
    }









    /**
         * Validates whether the response from API is not empty and is in JSON format.
         * @param response Response string from API
         * @return true/false
         */

    private boolean validateResponse(String response) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }
        try {
            JSONObject j = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * Use this method to set the callback in the view.
     * @param listener Reference to the view or class that implemented {@link ServerAuthenticateListener} interface
     */

    public void setServerAuthenticateListener(ServerAuthenticateListener listener) {
        mServerAuthenticateListener = listener;
    }

    /**
     * This interface consists of 3 callback methods.
     */

    public interface ServerAuthenticateListener {

        /**
         * Called when the network request starts.
         * @param code Event code which specifies, call to which API has been made.
         */
        void onRequestInitiated(int code);

        /**
         * Called when the request is successfully completed and returns the validated response.
         * @param code Event code which specifies, call to which API has been made.
         * @param result Result Object which needs to be casted to specific class as required
         */
        void onRequestCompleted(int code, Object result);

        /**
         * Called when unexpected error occurs.
         * @param code Event code which specifies, call to which API has been made.
         * @param message Error description
         */
        void onRequestError(int code, String message);

    }


}