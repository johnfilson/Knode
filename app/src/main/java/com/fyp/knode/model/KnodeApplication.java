package com.fyp.knode.model;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;


/**
 * Created by Johnny on 31/01/2016.
 * A custom application class is a best place custom command just like using API's
 */
public class KnodeApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "ZDvY8hbxoMQtAe3FPvLiYv9BO";
    private static final String TWITTER_SECRET = "2eoCW3AZW0UnUvYX65RwoEtEg5t24ivmDpbrjGdn6S3QHMclwq";

    @Override
    public void onCreate(){
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Event.class);
        Parse.initialize(this, "hczfRUwNdaPUyaO45MFdp4DIuZFP7nkSoQ8u8Pa0", "HGojUVFCm1NsTMVPIdrly7qpv6YSTmb3DBbYT7X9");
        FacebookSdk.sdkInitialize(getApplicationContext());
        ParseFacebookUtils.initialize(this);
        ParseTwitterUtils.initialize(TWITTER_KEY, TWITTER_SECRET);
    }
}
