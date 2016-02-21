package com.fyp.knode.ui;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class ChatActivity extends ListActivity {
    public static final String TAG = ChatActivity.class.getSimpleName();
    protected List<ParseUser> mUser;
    protected ParseRelation<ParseUser> mContactRelation;
    protected ParseUser mCurrentUser;
    protected MenuItem mSendMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
                    mUser = contacts;
                    String[] usernames = new String[mUser.size()];
                    int i = 0;
                    for (ParseUser user : mUser) {
                        usernames[i] = user.getUsername();
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (getListView().getContext(),
                                    android.R.layout.simple_list_item_checked,
                                    usernames);
                    setListAdapter(adapter);
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
            case R.id.action_logout:
                   break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (l.getCheckedItemCount() > 0) {

            mSendMenuItem.setVisible(true);
        } else {
            mSendMenuItem.setVisible(false);

        }
    }
}
