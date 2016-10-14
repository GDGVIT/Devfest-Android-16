package com.gdgvitvellore.devfest.Boundary.Customs;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

public class InputStreamVolleyRequest extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;
   private Map<String, String> mParams;
    //create a static map for directly accessing headers
   public Map<String, String> responseHeaders ;

    public InputStreamVolleyRequest(int post, String mUrl,Response.Listener<byte[]> listener,
                                    Response.ErrorListener errorListener, HashMap<String, String> params) {
      // TODO Auto-generated constructor stub
       
        super(post, mUrl, errorListener);
         // this request would never use cache since you are fetching the file content from server
         setShouldCache(false);
         mListener = listener;
         mParams=params;
   }

   @Override
   protected Map<String, String> getParams()
         throws com.android.volley.AuthFailureError {
      return mParams;
   };

   
    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;

        //Pass the response data here
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}