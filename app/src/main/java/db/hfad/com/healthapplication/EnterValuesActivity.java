package db.hfad.com.healthapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;


/**
 * Created by Lolita on 2016-10-30.
 */
public class EnterValuesActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    public static TextView Date;

    private Button Smiley1;
    private Button Smiley2;
    private Button Smiley3;
    private Button Smiley4;
    private Button Smiley5;
    private Button Smiley6;
    private Button Smiley7;
    private Button Smiley8;
    private Button Smiley9;

    private String feeling;
    private String currentDateTimeString;
    private DateFormat dateFormat;
    private Date date;

    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entervalues);

        gestureObject = new GestureDetectorCompat(this, new EnterValuesActivity.LearnGesture());

        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("EnterValues");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        dateFormat = new SimpleDateFormat("MM");
        date = new Date();
        //Log.d("Month",dateFormat.format(date));

        Date = (TextView)findViewById(R.id.textViewCurrentDateField);

        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Date.setText(currentDateTimeString);


        Smiley1 = (Button)findViewById(R.id.buttonSmiley1Field);
        Smiley2 = (Button)findViewById(R.id.buttonSmiley2Field);
        Smiley3 = (Button)findViewById(R.id.buttonSmiley3Field);
        Smiley4 = (Button)findViewById(R.id.buttonSmiley4Field);
        Smiley5 = (Button)findViewById(R.id.buttonSmiley5Field);
        Smiley6 = (Button)findViewById(R.id.buttonSmiley6Field);
        Smiley7 = (Button)findViewById(R.id.buttonSmiley7Field);
        Smiley8 = (Button)findViewById(R.id.buttonSmiley8Field);
        Smiley9 = (Button)findViewById(R.id.buttonSmiley9Field);

        Smiley1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = "happy";
                recordFeeling(feeling);
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = "sad";
                recordFeeling(feeling);
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = "indifferent";
                recordFeeling(feeling);
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = "confused";
                recordFeeling(feeling);
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = "crying";
                recordFeeling(feeling);
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = "dizzy";
                recordFeeling(feeling);
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = "inlove";
                recordFeeling(feeling);
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = "scared";
                recordFeeling(feeling);
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeling = "angry";
                recordFeeling(feeling);
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });


    }

    private void recordFeeling(String feeling) {
       if (!TextUtils.isEmpty(feeling)) {
            if (mAuth.getCurrentUser() != null) {

                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference cureent_user_db = mDatabase.child(user_id)
                        .child(dateFormat.format(date))
                        .child(currentDateTimeString);
                cureent_user_db.setValue(feeling);

            } else {

                Toast.makeText(EnterValuesActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
            }
        }
    }

     /*
     * Calling menu activity
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.healthapp_menu, menu);
        MenuItem item = menu.findItem(R.id.action_previous);
        item.setVisible(false);
        MenuItem item1 = menu.findItem(R.id.action_next);
        item1.setVisible(false);
        return true;
    }
    /*
     * Do some action based on user request
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_statisticseach:
                statisticsInfo();
                return true;
            case R.id.action_home:
                homePage();
                return true;
            case R.id.action_profile:
                profileInfo();
                return true;
            case R.id.action_logout:
                logout();
                return true;
            case R.id.action_appSettings:
                settingsInfo();
                return true;
            case R.id.action_calender:
                calendarInfo();
                return true;
            case R.id.action_notes:
                takeNote();
                return true;
            case R.id.action_sendEmail:
                sendEmail();
                return true;
            case R.id.action_statistics:
                showDiagram();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void takeNote() {
        startActivity(new Intent(EnterValuesActivity.this,NotesActivity.class));
    }

    private void sendEmail() {
        startActivity(new Intent(EnterValuesActivity.this,SendEmailActivity.class));
    }

    private void settingsInfo() {
        startActivity(new Intent(EnterValuesActivity.this,SettingsActivity.class));
    }

    private void statisticsInfo() {
        startActivity(new Intent(EnterValuesActivity.this, StatisticsActivity.class));
    }


    private void calendarInfo() {
        Calendar today = Calendar.getInstance();

        Uri uriCalendar = Uri.parse("content://com.android.calendar/time/" + String.valueOf(today.getTimeInMillis()));
        Intent intentCalendar = new Intent(Intent.ACTION_VIEW,uriCalendar);

        //Use the native calendar app to view the date
        startActivity(intentCalendar);
        //startActivity(new Intent(HealthApp.this, CalendarActivity.class));
    }

    private void showDiagram() {
        startActivity(new Intent(EnterValuesActivity.this, Statistics.class));
    }

    private void homePage() {
        startActivity(new Intent(EnterValuesActivity.this, HealthApp.class));
    }

    private void profileInfo() {
        startActivity(new Intent(EnterValuesActivity.this, UserProfile.class));
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(EnterValuesActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    class LearnGesture extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            if(event2.getX() > event1.getX()){

                Intent intent = new Intent(EnterValuesActivity.this,HealthApp.class);
                //finish();
                startActivity(intent);
            }else
            if(event2.getX()<event1.getX()){

                Intent intent = new Intent(EnterValuesActivity.this,SelfHarmActivity.class);
                //finish();
                startActivity(intent);
            }
            return true;

        }
    }

}
