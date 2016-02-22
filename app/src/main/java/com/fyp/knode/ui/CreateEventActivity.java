package com.fyp.knode.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fyp.knode.R;
import com.fyp.knode.model.Event;
import com.parse.ParseObject;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;



public class CreateEventActivity extends AppCompatActivity {

    @Bind(R.id.hashTagEditText) EditText mHashTag;
    @Bind(R.id.nameEventEditText) EditText mNameOfEvent;
    @Bind(R.id.attendees_maxEditText) EditText mAttendeesMax;
    @Bind(R.id.description_EditText) EditText mDescription;
    @Bind(R.id.peopleWhomAttendEditText) EditText mPeopleWhomAttend;
    @Bind(R.id.publishButton) Button mPublishButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPublishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfEvent = mNameOfEvent.getText().toString();
                String description = mDescription.getText().toString();
                String attendeeMax = mAttendeesMax.getText() + "";
                String hashTag = mHashTag.getText() + "";
                String peopleWhomMayAttend =mPeopleWhomAttend.getText().toString();
                if (nameOfEvent.isEmpty() || description.isEmpty() || attendeeMax.isEmpty()
                        || hashTag.isEmpty() || peopleWhomMayAttend.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                    builder.setMessage(R.string.createEvent_error_message)
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else{
                    ParseObject eventInfo = new ParseObject("EventObject");
                    eventInfo.put("organiserName", ParseUser.getCurrentUser().getUsername());
                    eventInfo.put("description", description);
                    eventInfo.put("eventName", nameOfEvent);
                    eventInfo.put("hashTag", hashTag);
                    eventInfo.put("maxAttend", attendeeMax);
                    eventInfo.put("peopleWhomMayAttend", peopleWhomMayAttend);
                    eventInfo.saveInBackground();
                    Intent intent = new Intent(CreateEventActivity.this, EventsListActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

}
