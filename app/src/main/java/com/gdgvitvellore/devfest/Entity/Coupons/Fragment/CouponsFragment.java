package com.gdgvitvellore.devfest.Entity.Coupons.Fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.gdgvitvellore.devfest.Boundary.API.ConnectAPI;
import com.gdgvitvellore.devfest.Boundary.Handlers.DataHandler;
import com.gdgvitvellore.devfest.Control.Contracts.ErrorDefinitions;
import com.gdgvitvellore.devfest.Control.Utils.ViewUtils;
import com.gdgvitvellore.devfest.Entity.Actors.Coupon;
import com.gdgvitvellore.devfest.Entity.Actors.CouponResult;
import com.gdgvitvellore.devfest.gdgdevfest.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import io.realm.RealmList;
import link.fls.swipestack.SwipeStack;

/**
 * Created by Shuvam Ghosh on 10/12/2016.
 */

/**
 * This class is used to display all the coupons that the attendees get like Food, Goodies etc.
 */

public class CouponsFragment extends Fragment implements SwipeStack.SwipeStackListener, ConnectAPI.ServerAuthenticateListener ,
        ViewUtils{

    private SwipeStack swipeStack;
    private SwipeStackAdapter adapter;
    private List<Coupon> couponsList;
    private ImageView qrCodeImage;
    private ImageView apiImage;
    private TextView apiName;
    private ConnectAPI connectApi;
    private ProgressDialog progressDialog;

    private LinearLayout root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_coupons,container,false);
        init(root);
        setInit();
        fetchData();
        return root;
    }

    /**
     * This function is used to fetch the data from server through {@link ConnectAPI} instance.
     */

    private void fetchData() {
        connectApi.coupon(DataHandler.getInstance(getActivity()).getUser().getAuthToken());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
    }

    /**
     * This function is used to initialize all views in layout.
     * @param root layout in which these views can be found.
     */

    private void init(View root) {
        swipeStack = (SwipeStack)root.findViewById(R.id.swipeStack);
        qrCodeImage=(ImageView)root.findViewById(R.id.qr_code);
        apiName = (TextView) root.findViewById(R.id.apiName);
        apiImage = (ImageView) root.findViewById(R.id.apiStatus);
        root = (LinearLayout) root.findViewById(R.id.root);
        progressDialog = new ProgressDialog(getContext());
        connectApi = new ConnectAPI(getActivity());
    }

    /**
     * This function is used to initialize listeners.
     */

    private void setInit() {
        swipeStack.setListener(this);
        swipeStack.setAllowedSwipeDirections(SwipeStack.SWIPE_DIRECTION_ONLY_RIGHT);
        connectApi.setServerAuthenticateListener(this);
    }

    /**
     * This function is used to set data to view. Make request to server and fetch details if data not found.
     */

    private void setData() {
        //List<Coupon> coupons = DataHandler.getInstance(getContext()).getCoupons();
        /*if (coupons != null) {
            Log.i(TAG, "setData: ");
            couponsList = coupons;
            adapter=new SwipeStackAdapter(couponsList);
            swipeStack.setAdapter(adapter);

        } else {
        */    connectApi.coupon(DataHandler.getInstance(getActivity()).getUser().getAuthToken());
        //}

    }

    /**
     * A QR code is generated for each item. (Eg. Food, Goodies each will have one QR code)
     * @param pos
     */

    private void updateQrCode(int pos) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(couponsList.get(pos).getCouponCode(), BarcodeFormat.QR_CODE,200,200);
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

    /**
     * Initialize behaviour when a card is swiped right.
     * @param position
     */

    @Override
    public void onViewSwipedToRight(int position) {
        adapter.add(couponsList.get(position), couponsList.size());
        adapter.notifyDataSetChanged();
        updateQrCode(position==couponsList.size()-1?0:position+1);
    }

    @Override
    public void onStackEmpty() {

    }

    /**
     * Show progress dialog when request is made to the server.
     * @param code Event code which specifies, call to which API has been made.
     */

    @Override
    public void onRequestInitiated(int code) {

        if (code == ConnectAPI.COUPON_CODE) {
            progressDialog.setMessage("Loading coupons...");
            progressDialog.show();
        }

    }

    /**
     * Once the data is fetched from the server it's not saved in database since we need updated status every time.
     * @param code   Event code which specifies, call to which API has been made.
     * @param result Result Object which needs to be casted to specific class as required
     */

    @Override
    public void onRequestCompleted(int code, Object result) {

        progressDialog.cancel();
        if (code == ConnectAPI.COUPON_CODE) {
            CouponResult couponResult = (CouponResult) result;
            if (couponResult != null) {
                if (couponResult.getStatus() == ErrorDefinitions.CODE_SUCCESS) {
                    //DataHandler.getInstance(getActivity()).saveCoupon(couponResult.getCoupons());
                    setData(couponResult.getCoupons());
                } else {
                    showMessage(couponResult.getMessage());
                }
            }
        }

    }

    /**
     * List of coupons are fetched from the server and set to Swipe Stack (For stacked up cardview).
     * @param coupons
     */

    private void setData(RealmList<Coupon> coupons) {
        couponsList=coupons;
        adapter=new SwipeStackAdapter(coupons);
        swipeStack.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * On request failed message is shown to the user.
     * @param code    Event code which specifies, call to which API has been made.
     * @param message Error description
     */

    @Override
    public void onRequestError(int code, String message) {
        progressDialog.cancel();
        if (code == ConnectAPI.COUPON_CODE) {
            showMessage(message);
        }
    }

    /**
     * Adapter class for SwipeStack.
     */

    public class SwipeStackAdapter extends BaseAdapter {

        private List<Coupon> mData;

        public SwipeStackAdapter(List<Coupon> data) {
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Coupon getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Initialize local variables to views.
         * @param position position in Stack
         * @param convertView view layout where views can be found.
         * @param parent Parent viewGroup.
         * @return
         */

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.api_view,parent,false);
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_coupons_item, parent, false);
            TextView apiName = (TextView) convertView.findViewById(R.id.apiName);
            ImageView apiImage = (ImageView) convertView.findViewById(R.id.couponPicture);
            ImageView apiStatus = (ImageView) convertView.findViewById(R.id.apiStatus);

            //String url = mData.get(position).getCouponCode();

            apiName.setText(mData.get(position).getCouponName());
            //Glide.with(getContext()).load(url).asBitmap().into(apiImage);

            if (mData.get(position).isUsed()){
                apiStatus.setVisibility(View.VISIBLE);
            }else{
                apiStatus.setVisibility(View.GONE);
            }

            return convertView;
        }
        public void add(Coupon text, int position){
            mData.add(position,text);
        }
    }

    /**
     * To show message passed in Snackbar.
     * @param message String data which has to shown in Snackbar
     */

    @Override
    public void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message, boolean showAction) {

    }

    @Override
    public void showErrorDialog(String message) {

    }

}
