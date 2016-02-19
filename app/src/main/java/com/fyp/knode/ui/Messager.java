package com.fyp.knode.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.fyp.knode.Adapter.SectionsPagerAdapter;
import com.fyp.knode.EditContactsActivity;
import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.R;
import com.fyp.knode.SlidingTabStrip.SlidingTabLayout;
import com.parse.ParseUser;

public class Messager extends AppCompatActivity {

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

    public static final int MEDIA_TYPE_IMAGE = 4;
    public static final int MEDIA_TYPE_VIDEO = 5;

    protected Uri mMediaUri;

    protected DialogInterface.OnClickListener mDialogListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            Intent takepicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            mMediaUri.getOutputMediaUri(MEDIA_TYPE_IMAGE);
                            if(mMediaUri == null){
                                Toast.makeText(Messager.this, R.string.error_with_external_storage, Toast.LENGTH_LONG).show();
                            }else {
                                takepicture.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                                startActivityForResult(takepicture, Constants.KEY_TAKE_PICTURE_RESPONSE);
                            }
                            break;
                        case 1:

                            break;
                        case 2:
                            break;
                        case 3:
                            ;
                    }
                }
                private Uri getOutputMediaUri (int MediaTypeImage){
                    if(isExternalStorageAvailable()){
                        return null;
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
        mSectionsPagerAdapter = new SectionsPagerAdapter(this,mTitles ,mNumberOfLabs,getSupportFragmentManager());

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


    private void navigateToLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

