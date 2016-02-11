package com.fyp.knode.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;

import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Johnny on 08/02/2016.
 */
public class ContactsFragment extends ListFragment {
    public static final String TAG = ContactsFragment.class.getSimpleName();
    protected List<ParseUser> mUser;
    protected ParseRelation<ParseUser> mContactRelation;
    protected ParseUser mCurrentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();
        mContactRelation = mCurrentUser.getRelation(Constants.KEY_CONTACT_RELATION);
        getActivity().setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseUser> query = mContactRelation.getQuery();
        query.addAscendingOrder(Constants.KEY_USERNAME);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> contacts, ParseException e) {
               getActivity().setProgressBarIndeterminateVisibility(false);
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
                                    android.R.layout.simple_list_item_1,
                                    usernames);
                    setListAdapter(adapter);
                } else {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }
}
