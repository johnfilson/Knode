package com.fyp.knode.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyp.knode.Adapter.MessagerAdapter;
import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Johnny on 08/02/2016.
 */
public class MessagerFragment extends android.support.v4.app.ListFragment {
    protected List<ParseObject> mMessager;
    public static final String TAG = ContactsFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(Constants.CLASS_MESSAGES);
        query.whereEqualTo(Constants.KEY_RECEIVER_ID, ParseUser.getCurrentUser().getObjectId());
        query.addDescendingOrder(Constants.KEY_CREATE_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null) {
                    mMessager = objects;

                    String[] usernames = new String[mMessager.size()];
                    int i = 0;
                    for (ParseObject message : mMessager) {
                        usernames[i] = message.getString(Constants.KEY_SENDER_NAME);
                        i++;
                    }
                    MessagerAdapter adapter = new MessagerAdapter(getListView().getContext(),
                            mMessager);
                    setListAdapter(adapter);
                }
                else {
                    Log.e(TAG, e.getMessage());
                }

            }
        });
    }
}
