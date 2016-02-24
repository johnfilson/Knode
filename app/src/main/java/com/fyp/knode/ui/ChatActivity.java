package com.fyp.knode.ui;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.R;
import com.fyp.knode.helper.FileHelper;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    public static final String TAG = ChatActivity.class.getSimpleName();
    protected List<ParseUser> mContacts;
    protected ParseRelation<ParseUser> mContactRelation;
    protected ParseUser mCurrentUser;
    protected MenuItem mSendMenuItem;
    protected Uri mUri;
    protected String mFileType;
    protected ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        getSupportActionBar().setHomeButtonEnabled(true);

        mList = (ListView) findViewById(R.id.list);
        mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        mUri = getIntent().getData();
        mFileType = getIntent().getExtras().getString(Constants.KEY_FILE_TYPE);
    }

    @Override
    public void onResume() {
        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();
        mContactRelation = mCurrentUser.getRelation(Constants.KEY_CONTACT_RELATION);
       setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseUser> query = mContactRelation.getQuery();
        query.addAscendingOrder(Constants.KEY_USERNAME);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> contacts, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    mContacts = contacts;
                    String[] usernames = new String[mContacts.size()];
                    int i = 0;
                    for (ParseUser user : mContacts) {
                        usernames[i] = user.getUsername();
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (ChatActivity.this,
                                    android.R.layout.simple_list_item_checked,
                                    usernames);
                    mList.setAdapter(adapter);
                    mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if (mList.getCheckedItemCount() > 0) {
                                mSendMenuItem.setVisible(true);
                            } else {
                                mSendMenuItem.setVisible(false);
                            }
                        }
                    });
                } else {
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle(R.string.error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        mSendMenuItem = menu.getItem(0);
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
            case R.id.action_chat:
                ParseObject message = createMessage();
                if(message == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                    builder.setMessage(R.string.error_selecting_file)
                            .setTitle(R.string.error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    send(message);
                    finish();
                }
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }



    protected ParseObject createMessage(){
        ParseObject message = new ParseObject(Constants.CLASS_MESSAGES);
        message.put(Constants.KEY_SENDER_ID, ParseUser.getCurrentUser().getObjectId());
        message.put(Constants.KEY_SENDER_NAME, ParseUser.getCurrentUser().getUsername());
        message.put(Constants.KEY_RECEIVER_ID, getReceiverId());
        message.put(Constants.KEY_FILE_TYPE, mFileType);


        byte[] fileBytes = FileHelper.getByteArrayFromFile(this, mUri);

        if(fileBytes == null){
            return null;
        }else {
            if(mFileType.equals(Constants.TYPE_IMAGE)){
                fileBytes = FileHelper.reduceImageForUpload(fileBytes);
            }

            String fileName = FileHelper.getFileName(this, mUri, mFileType);
            ParseFile file = new ParseFile(fileName, fileBytes);
            message.put(Constants.KEY_FILE, file);


            return message;
        }

    }

    protected ArrayList<String> getReceiverId(){
    ArrayList<String> receiverIds= new ArrayList<String>();
        for(int i =0 ; i < mList.getCount(); i++){
            if(mList.isItemChecked(i)){
                receiverIds.add(mContacts.get(i).getObjectId());
            }
        }
        return receiverIds;
    }

    protected void send(ParseObject message){
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(ChatActivity.this, R.string.success_message_sent, Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                    builder.setMessage(R.string.error_sending_message)
                            .setTitle(R.string.error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
}
