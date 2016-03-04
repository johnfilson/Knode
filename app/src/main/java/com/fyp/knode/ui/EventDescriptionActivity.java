package com.fyp.knode.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EventDescriptionActivity extends AppCompatActivity {

    @Bind(R.id.eventTitle) TextView mNameOfEvent;
    @Bind(R.id.organiserNameLabel) TextView mOrganiserName;
    @Bind(R.id.descriptionLabel) TextView mDescription;
    @Bind(R.id.people_whom_may_attend_label) TextView mDescriptionAttendees;
    @Bind(R.id.hashTagTextView) TextView mHashtag;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        String organiserName = intent.getStringExtra(Constants.KEY_ORGANISER_NAME);
        String eventTitle = intent.getStringExtra(Constants.KEY_EVENT_NAME);
        String description = intent.getStringExtra(Constants.KEY_EVENT_DESCRIPTION);
        String descriptionOfAttendees= intent.getStringExtra(Constants.KEY_EVENT_PEOPLE_INTER);
        String hashTag = intent.getStringExtra(Constants.KEY_EVENT_HASHTAG);
        mOrganiserName.setText(organiserName);
        mNameOfEvent.setText(eventTitle);
        mDescription.setText(description);
        mDescriptionAttendees.setText(descriptionOfAttendees);
        mHashtag.setText("#"+hashTag);
    }

}
