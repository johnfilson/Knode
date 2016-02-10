package com.fyp.knode;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fyp.knode.KnodeConstants.Constants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class EditContactsActivity extends ListActivity {

    public static final String TAG = EditContactsActivity.class.getSimpleName();
    protected List<ParseUser> mUser;
    protected ParseRelation<ParseUser>  mContactRelation;
    protected ParseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend);
        
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();
        mContactRelation =  mCurrentUser.getRelation(Constants.KEY_CONTACT_RELATION);

        setProgressBarIndeterminateVisibility(true);
        ParseQuery<ParseUser>query = ParseUser.getQuery();
        query.orderByAscending(Constants.KEY_USERNAME);
        query.setLimit(Constants.KEY_USER_LIMIT);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditContactsActivity.this,
                            android.R.layout.simple_list_item_checked,
                            usernames);
                    setListAdapter(adapter);
                    addUserFromCheckmarks();
                } else {
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditContactsActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle(R.string.something_went_wrong)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
    //I would add search functionality 
    private void addUserFromCheckmarks(){
        mContactRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> contacts, ParseException e) {
                if (e == null) {
                    for( int i = 0; i <mUser.size(); i++){
                        ParseUser user  = mUser.get(i);
                        for(ParseUser contact : contacts){
                            if(contact.getObjectId().equals(user.getObjectId())){
                                getListView().setItemChecked(i, true);
                            }
                        }
                    }
                }
                else {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mContactRelation.add(mUser.get(position));
        mCurrentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }
}
