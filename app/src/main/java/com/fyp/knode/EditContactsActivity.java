package com.fyp.knode;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;

import com.fyp.knode.KnodeConstants.Constants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class EditContactsActivity extends ListActivity {

    public static final String TAG = EditContactsActivity.class.getSimpleName();
    protected List<ParseUser> mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend);



    }

    @Override
    protected void onResume() {
        super.onResume();
    setProgressBarIndeterminateVisibility(true);
        ParseQuery<ParseUser>query = ParseUser.getQuery();
        query.orderByAscending(Constants.KEY_USERNAME);
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if(e ==null){
                    mUser = objects;
                    String[] usernames = new String[mUser.size()];
                    int i = 0;
                    for(ParseUser user : mUser){
                        usernames [i] = user.getUsername();
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditContactsActivity.this,
                            android.R.layout.simple_list_item_checked,
                            usernames);
                    setListAdapter(adapter);
                }
                else{
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
}
