package com.gdgvitvellore.devfest.Entity.Authentication.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgvitvellore.devfest.Boundary.API.ConnectAPI;
import com.gdgvitvellore.devfest.gdgdevfest.R;

/**
 * Created by AravindRaj on 11-10-2016.
 */

public class AuthenticationActivity extends AppCompatActivity implements ConnectAPI.ServerAuthenticateListener, View.OnClickListener {

    TextInputEditText email, password;
    TextInputLayout emailLayout, passwordLayout;
    Button signin;
    TextView guestLogin;
    String emailInput, passInput;

    ConnectAPI connectAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        init();
        initListeners();
    }

    private void getCredentials() {
        emailInput = email.getText().toString();
        passInput = password.getText().toString();
    }

    private void initListeners() {
        signin.setOnClickListener(this);
        guestLogin.setOnClickListener(this);
    }

    private void init() {
        email = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);
        guestLogin = (TextView) findViewById(R.id.guest_login);

        emailLayout = (TextInputLayout) findViewById(R.id.email_input_layout);
        passwordLayout = (TextInputLayout) findViewById(R.id.password_input_layout);

        signin = (Button) findViewById(R.id.login);

        connectAPI = new ConnectAPI(this);
    }

    @Override
    public void onRequestInitiated(int code) {

        if (code == ConnectAPI.LOGIN_CODE){
            email.setClickable(false);
            password.setClickable(false);
        }

    }

    @Override
    public void onRequestCompleted(int code, Object result) {

        if (code == ConnectAPI.LOGIN_CODE){
            Toast.makeText(this, result.toString(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestError(int code, String message) {

        if(code == ConnectAPI.LOGIN_CODE){
            //if email id not registered
            emailLayout.setError("Enter valid Email");
            requestFocus(email);
            //if password is incorrect
            passwordLayout.setError("Enter valid Password");
            requestFocus(password);
        }

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){
            case R.id.login:
                login();
                break;
            case R.id.guest_login:
                guestLogin();
                break;
            default:
                break;
        }

    }

    private void guestLogin() {
        connectAPI.login("guest","guest");
    }

    private void login() {
        getCredentials();
        connectAPI = new ConnectAPI(this);
        connectAPI.login("email","pass");
//        Toast.makeText(this, emailInput.toString()+passInput.toString(),Toast.LENGTH_LONG).show();
    }
}
