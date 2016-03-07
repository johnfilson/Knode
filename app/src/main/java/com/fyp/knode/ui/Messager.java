package com.fyp.knode.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fyp.knode.Adapter.SectionsPagerAdapter;
import com.fyp.knode.EditContactsActivity;
import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.R;
import com.fyp.knode.SlidingTabStrip.SlidingTabLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Messager extends AppCompatActivity {

    private static final String TAG = Messager.class.getSimpleName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    CharSequence mTitles[] = {"Messager", "My Contacts"};
    int mNumberOfLabs = 2;
    SlidingTabLayout mTabs;

    public static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 6;

    protected Uri mMediaUri;

    protected DialogInterface.OnClickListener mDialogListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkWriteExternalPermission();

                    switch (which) {
                        case 0:
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            mMediaUri = getOutputMediaFileUri(Constants.MEDIA_TYPE_IMAGE);
                            if(mMediaUri == null){
                                Toast.makeText(Messager.this, R.string.error_with_external_storage, Toast.LENGTH_LONG).show();
                            }else {
                                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                                startActivityForResult(takePicture, Constants.KEY_TAKE_PICTURE_RESPONSE);
                            }
                            break;
                        case 1:
                            Intent takeVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            mMediaUri = getOutputMediaFileUri(Constants.MEDIA_TYPE_VIDEO);
                            if(mMediaUri == null){
                                Toast.makeText(Messager.this, R.string.error_with_external_storage, Toast.LENGTH_LONG).show();
                            }else {
                                takeVideo.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                                takeVideo.putExtra(MediaStore.EXTRA_DURATION_LIMIT,30);
                                //the video is kept in low quality because of retr
                                takeVideo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);
                                startActivityForResult(takeVideo, Constants.KEY_TAKE_VIDEO_RESPONES);
                            }
                            break;
                        case 2:
                            Intent choosePicture = new Intent(Intent.ACTION_GET_CONTENT);
                            choosePicture.setType("image/*");
                            startActivityForResult(choosePicture,Constants.KEY_PICK_PICTURE);
                            break;
                        case 3:
                            Intent chooseVideo = new Intent(Intent.ACTION_GET_CONTENT);
                            chooseVideo.setType("video/*");
                            Toast.makeText(Messager.this, R.string.select_video_max_warning, Toast.LENGTH_SHORT).show();
                            startActivityForResult(chooseVideo, Constants.KEY_PICK_VIDEO);
                            break;
                    }
                }
                private Uri getOutputMediaFileUri (int mediaType){

                    if(isExternalStorageAvailable()){
                        String appName = Messager.this.getString(R.string.app_name);
                        File mediaStrorage = new File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                appName);
                        if(!mediaStrorage.exists()){
                            if(!mediaStrorage.mkdir()){
                                Log.e(TAG, "Fail to created Directory");
                                return null;
                            }
                        }
                        File mediafile;
                        Date now = new Date();
                        String timestamp =
                                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(now);

                        String mediaPath = mediaStrorage.getPath() + File.separator;

                        if(mediaType == Constants.MEDIA_TYPE_IMAGE){
                            mediafile = new File(mediaPath +"IMG_"+ timestamp + ".jpg");
                        }else if(mediaType == Constants.MEDIA_TYPE_VIDEO) {
                            mediafile = new File(mediaPath +"VID_"+ timestamp + ".mp4");
                        }else {
                            return null;
                        }
                        Log.d(TAG, "File:  "+ Uri.fromFile(mediafile));
                        return Uri.fromFile(mediafile);
                    }else {
                        return null;
                    }
                }
                private Boolean isExternalStorageAvailable(){
                    String state = Environment.getExternalStorageState();

                    if(state.equals((Environment.MEDIA_MOUNTED))){
                        return true;
                    }else{return false;}
                }
            };

    private boolean checkWriteExternalPermission() {
        if (ContextCompat.checkSelfPermission(Messager.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // We do not have permission to write
            // Should we show an explanation? This method only returns true if the user has previously denied a request.
            if (ActivityCompat.shouldShowRequestPermissionRationale(Messager.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Explain to the user why we need the permission, then prompt for it.
                // You can do this how you want, I just like snackbars :)
                showWriteToStorageSnackbar();
            } else {

                // No explanation needed, we can request the permission.

                requestWritePermissionWithCallback();

                // PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            }
            // We don't have permission right now, but the user has been prompted.
            return false;
        }
        return true;
    }

    private void showWriteToStorageSnackbar() {
        Snackbar.make(mViewPager, "Write to storage is required to store and access photos/videos.",
                Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestWritePermissionWithCallback();
                    }
                }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted, yay!

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showWriteToStorageSnackbar();
                }
                break;
            default:
                Log.e(TAG, "Got request code: " + requestCode + " which is not used in switch.");
                break;
        }
    }

    private void requestWritePermissionWithCallback() {
        ActivityCompat.requestPermissions(Messager.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
    }

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this,
                mTitles ,mNumberOfLabs,getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Assigning the Sliding Tab Layout View
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        //making the tabs even

        mTabs.setViewPager(mViewPager);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_logout :
                ParseUser.logOut();
                navigateToLogIn();
                break;
            case R.id.action_add_contacts:
                Intent intent = new Intent(this, EditContactsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_camera:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(R.array.camera_choices, mDialogListener);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode== Constants.KEY_PICK_PICTURE || requestCode == Constants.KEY_PICK_VIDEO){
                if(data == null){
                    Toast.makeText(Messager.this, R.string.knode_general_error, Toast.LENGTH_SHORT).show();
                }else {
                    mMediaUri = data.getData();
                }
                Log.i(TAG,"Mediastore ===" + mMediaUri);
                if(requestCode == Constants.KEY_PICK_VIDEO){

                    int filesize = 0;
                    InputStream inputStream = null;
                    try {
                        inputStream = getContentResolver().openInputStream(mMediaUri);
                    }catch (FileNotFoundException e){
                        Toast.makeText(Messager.this, R.string.knode_selectedvideo_error, Toast.LENGTH_SHORT).show();
                        return;
                    }catch (IOException e){
                        Toast.makeText(Messager.this, R.string.knode_selectedvideo_error, Toast.LENGTH_SHORT).show();
                        return;
                    }///we call this finally to close the stream and after everything is complete.
                    finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(filesize >= Constants.FILE_SIZE_LIMIT){
                        Toast.makeText(Messager.this, R.string.file_overlimit_error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }else {
                Intent mediaScan = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScan.setData(mMediaUri);

                sendBroadcast(mediaScan);
            }
            Intent chatIntent = new Intent(this, ChatActivity.class);
            chatIntent.setData(mMediaUri);

            String fileType;
            if(requestCode == Constants.KEY_PICK_PICTURE || requestCode == Constants.KEY_TAKE_PICTURE_RESPONSE){
                fileType = Constants.TYPE_IMAGE;
            }else {
                fileType = Constants.TYPE_VIDEO;
            }

            chatIntent.putExtra(Constants.KEY_FILE_TYPE, fileType );
            startActivity(chatIntent);

        }else if(resultCode != RESULT_CANCELED){
            Toast.makeText(Messager.this, R.string.knode_general_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

