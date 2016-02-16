package com.fyp.knode.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.fyp.knode.MainActivity;
import com.fyp.knode.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private static final  String TAG = LoginActivity.class.getSimpleName() ;
    protected TextView mSignUpTextView;
    protected EditText mUsername;
    protected EditText  mPassword;
    protected Button mLoginButton;
    ArrayList<String> userPermission = new ArrayList<>();
    protected String mFullname;
    private String mFBEmail;
    private TwitterLoginButton loginButton;
    ArrayList <String> getUserPermission = new ArrayList();



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);

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



        if (isNetworkAvailable()) {
            mSignUpTextView = (TextView) findViewById(R.id.signUpLabel);
            mSignUpTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
            mUsername = (EditText) findViewById(R.id.usernameField);
            mPassword = (EditText) findViewById(R.id.password);
            mLoginButton = (Button) findViewById(R.id.loginButton);
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = mUsername.getText().toString();
                    String password = mPassword.getText() + "";
                    username = username.trim();
                    password = password.trim();
                    if (username.isEmpty() || password.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage(R.string.login_error_message)
                                .setTitle(R.string.login_error_title)
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
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
            twitterLogIn();
        }
        else {
            Toast.makeText(this, R.string.no_network_available, Toast.LENGTH_LONG).show();
        }
    }

    private void twitterLogIn() {
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                Twitter.getInstance().core.getSessionManager().getActiveSession();
                TwitterSession session = result.data;


                getUserPermission.add(session.getUserId() + "");
                getUserPermission.add(session.getUserName());
                getUserPermission.add(session.getAuthToken() + "");
                getUserPermission.add("2eoCW3AZW0UnUvYX65RwoEtEg5t24ivmDpbrjGdn6S3QHMclwq");
                Log.i(TAG, getUserPermission +"" );
                ParseTwitterUtils.logIn(LoginActivity.this, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
                        } else if (user.isNew()) {
                            user.setUsername(getUserPermission.get(1));
                            successfulLogIn();
                            Log.d("MyApp", "User signed up and logged in through Twitter!");
                        } else {
                            user.setUsername(getUserPermission.get(1));
                            successfulLogIn();
                            Log.d("MyApp", "User logged in through Twitter!");
                        }
                    }
                });

            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }


    private void successfulLogIn() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //Facebook Log on and Sign up
    public void signInWithFB(View v){
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, userPermission, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                Log.i(TAG, user.getUsername());
                Log.i(TAG, userPermission + "");
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    getUsersInformation();
                    user.setUsername(mFullname.trim());
                    user.setEmail(mFBEmail);
                    successfulLogIn();
                } else {
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

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    
}
