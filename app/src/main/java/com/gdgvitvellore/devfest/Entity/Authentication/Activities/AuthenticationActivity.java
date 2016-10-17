package com.gdgvitvellore.devfest.Entity.Authentication.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgvitvellore.devfest.Boundary.API.ConnectAPI;
import com.gdgvitvellore.devfest.Boundary.Handlers.DataHandler;
import com.gdgvitvellore.devfest.Control.Animations.Authentication.BackgroundCircularReveal;
import com.gdgvitvellore.devfest.Control.Animations.Main.DrawerCircularReveal;
import com.gdgvitvellore.devfest.Control.Animations.Main.ObjectAnimations;
import com.gdgvitvellore.devfest.Control.Contracts.ErrorDefinitions;
import com.gdgvitvellore.devfest.Control.Contracts.Status;
import com.gdgvitvellore.devfest.Control.Exceptions.BindingException;
import com.gdgvitvellore.devfest.Control.Utils.ViewUtils;
import com.gdgvitvellore.devfest.Entity.Actors.LoginResult;
import com.gdgvitvellore.devfest.Entity.Main.Activities.MainActivity;
import com.gdgvitvellore.devfest.gdgdevfest.R;

/**
 * Created by AravindRaj on 11-10-2016.
 */

public class AuthenticationActivity extends AppCompatActivity implements ConnectAPI.ServerAuthenticateListener,
        View.OnClickListener, ViewUtils {

    private TextInputEditText email, password;
    private TextInputLayout emailLayout, passwordLayout;
    private LinearLayout bgSplash;
    private Button signin;
    private TextView guestLogin;
    private String emailInput, passInput;

    private ConnectAPI connectAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        init();
        setInit();
    }


    private void init() {
        email = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);
        guestLogin = (TextView) findViewById(R.id.guest_login);

        emailLayout = (TextInputLayout) findViewById(R.id.email_input_layout);
        passwordLayout = (TextInputLayout) findViewById(R.id.password_input_layout);

        bgSplash = (LinearLayout) findViewById(R.id.bg_splash);

        signin = (Button) findViewById(R.id.login);

        connectAPI = new ConnectAPI(this);
    }

    private void setInit() {
        signin.setOnClickListener(this);
        guestLogin.setOnClickListener(this);
        connectAPI.setServerAuthenticateListener(this);
        if (Build.VERSION.SDK_INT >= 21) {
            ViewTreeObserver viewTreeObserver = bgSplash.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Animator reveal = BackgroundCircularReveal.circularRevealSplash(bgSplash, 0, 0);
                        reveal.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                bgSplash.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        reveal.start();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            bgSplash.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            bgSplash.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }

                    }
                });
            }

        } else {
            bgSplash.setVisibility(View.VISIBLE);
        }
    }

    private void getCredentials() {
        emailInput = email.getText().toString();
        passInput = password.getText().toString();
    }


    @Override
    public void onRequestInitiated(int code) {

        if (code == ConnectAPI.LOGIN_CODE) {
            email.setClickable(false);
            password.setClickable(false);
        }

    }

    @Override
    public void onRequestCompleted(int code, Object result) {

        if (code == ConnectAPI.LOGIN_CODE) {
            LoginResult loginResult = (LoginResult) result;
            Log.d("LOGIN RESPONSE", result.toString());
            if (loginResult != null) {
                if (loginResult.getStatus() == ErrorDefinitions.CODE_SUCCESS) {
                    Log.d("LOGIN", "SUCCESS");
                    DataHandler.getInstance(this).saveUser(loginResult.getUser());
                    DataHandler.getInstance(this).saveTeam(loginResult.getTeam());
                    DataHandler.getInstance(this).saveSlotLastUsed(loginResult.getUser().getSlotLastTime());
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("status", Status.LOGGED_IN);
                    startActivity(intent);
                    DataHandler.getInstance(this).saveLoggedIn(true);
                    finish();
                } else {
                    showMessage(ErrorDefinitions.getMessage(loginResult.getStatus()));
                    signin.setText("Sign in");
                    signin.setClickable(true);
                }
            }
        }
    }

    @Override
    public void onRequestError(int code, String message) {

        if (code == ConnectAPI.LOGIN_CODE) {
            //if email id not registered
            emailLayout.setError("Enter valid Email");
            requestFocus(email);
            //if password is incorrect
            passwordLayout.setError("Enter valid Password");
            requestFocus(password);
            signin.setText("Sign in");
            signin.setClickable(true);
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

        switch (id) {
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("status", Status.GUEST_USER);
        startActivity(intent);
        finish();
    }

    private void login() {

        getCredentials();
        disable();
        try {
            connectAPI.login(emailInput, passInput);
        } catch (BindingException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, emailInput.toString()+passInput.toString(),Toast.LENGTH_LONG).show();
    }

    private void disable() {
        signin.setClickable(false);
        signin.setText("Signing in...");
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(bgSplash, message, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void showMessage(String message, boolean showAction) {

    }

    @Override
    public void showErrorDialog() {

    }
}
