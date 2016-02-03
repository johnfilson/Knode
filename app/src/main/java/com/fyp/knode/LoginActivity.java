package com.fyp.knode;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    protected TextView mSignUpTextView;
    protected EditText mUsername;
    protected EditText  mPassword;
    protected Button mLoginButton;
    ArrayList<String> userPermission = new ArrayList<>();
    protected String mFullname;
    private String mFBEmail;


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Facebook persmission
        userPermission.add("user_friends");
        userPermission.add("public_profile");
        userPermission.add("email");

        mSignUpTextView = (TextView) findViewById(R.id.signUpLabel);
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        mUsername = (EditText)findViewById(R.id.usernameField);
        mPassword = (EditText)findViewById(R.id.password);
        mLoginButton= (Button)findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText() + "" ;
                username= username.trim();
                password = password.trim();
                if (username.isEmpty() || password.isEmpty() ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(R.string.login_error_message)
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok,null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    // Login
                    setProgressBarIndeterminateVisibility(true);
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser User, ParseException e) {
                            setProgressBarIndeterminateVisibility(false);
                            if (e == null) {
                                successfulLogIn();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle(R.string.login_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void successfulLogIn() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //Facebook log on
    public void signIn(View v){

        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, userPermission, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user == null){
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                }
                else if(user.isNew()){
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    getUsersInformation();
                    user.setEmail(mFBEmail);
                    successfulLogIn();
                }
                else {
                    Log.d("MyApp", "User logged in through Facebook!");
                    getUsersInformation();
                    successfulLogIn();
                }
            }
        });
    }

    private void getUsersInformation(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("this is my response -->", response.toString());
                try {
                    mFullname = response.getJSONObject().getString("first_name") + " " +
                            response.getJSONObject().getString("last_name");
                    Log.d("this is my name ---->", mFullname);
                    mFBEmail = response.getJSONObject().getString("email");
                }catch (Exception e){
                    Log.d("Exeception ---->", e.getMessage());
                }

            }
        });

        Bundle bundler = new Bundle();
        bundler.putString("fields","first_name, last_name, gender, email");
        request.setParameters(bundler);
        request.executeAsync();

    }
}
