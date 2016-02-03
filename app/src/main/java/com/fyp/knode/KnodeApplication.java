package com.fyp.knode;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;


/**
 * Created by Johnny on 31/01/2016.
 * A custom application class is a best place custom command just like using API's
 */
public class KnodeApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "hczfRUwNdaPUyaO45MFdp4DIuZFP7nkSoQ8u8Pa0", "HGojUVFCm1NsTMVPIdrly7qpv6YSTmb3DBbYT7X9");
        FacebookSdk.sdkInitialize(getApplicationContext());
        ParseFacebookUtils.initialize(this);
        ParseObject testObject = ParseObject.create("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
}
