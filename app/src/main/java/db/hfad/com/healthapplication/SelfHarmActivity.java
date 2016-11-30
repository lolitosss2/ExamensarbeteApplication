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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static db.hfad.com.healthapplication.FeelingsActivity.stripNonDigits;

/**
 * Created by Lolita on 2016-10-30.
 */
public class SelfHarmActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;


    private SeekBar seekBar;
    private SeekBar seekBar2;
    private TextView textView;
    private TextView textView2;

    private Button ButtonNext;

    public static TextView Date;
    private DateFormat dateFormat;
    private Date date;
    private String currentDateTimeString;

    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfharm);

        //gestureObject = new GestureDetectorCompat(this, new SelfHarmActivity.LearnGesture());



        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("SelfHarm");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        dateFormat = new SimpleDateFormat("MM");
        date = new Date();

        Date = (TextView) findViewById(R.id.textViewCurrentDateField);

        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Date.setText(currentDateTimeString);

        seekBar = (SeekBar) findViewById(R.id.seekBarQuestion1Field);
        textView = (TextView) findViewById(R.id.textViewQuestion1Per);

        seekBar2 = (SeekBar) findViewById(R.id.seekBarQuestion3Field);
        textView2 = (TextView) findViewById(R.id.textViewQuestion3Per);

        ButtonNext = (Button)findViewById(R.id.buttonNext0);



        //current position of progressBar
        String text = seekBar.getProgress() + " % " /*+ seekBar.getMax()*/;
        textView.setText(text);

        //seekBar Listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;


            // true if process was changed by user
            // false it process was changed by system
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            // when thumb were stopped, then react
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // show result in Textview
                String resultText = progress + " %" /*+ seekBar.getMax()*/;
                textView.setText(resultText);
                recordFeeling(resultText,"Hurt");
            }
        });

        String text2 = seekBar2.getProgress() + " % " /*+ seekBar.getMax()*/;
        textView2.setText(text2);

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;


            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String resultText = progress + " %" /*+ seekBar.getMax()*/;
                textView2.setText(resultText);
                recordFeeling(resultText,"Thoughts");
            }
        });

        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelfHarmActivity.this, FeelingsActivity.class));
            }
        });
    }

    private void recordFeeling(String percentage, String hurt) {
        percentage = stripNonDigits(percentage);
        if (!TextUtils.isEmpty(percentage)) {
            if (mAuth.getCurrentUser() != null) {

                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference cureent_user_db = mDatabase.child(user_id)
                        .child(hurt)
                        .child(dateFormat.format(date))
                        .child(currentDateTimeString);
                cureent_user_db.setValue(percentage);

            } else {
                //TODO
                Toast.makeText(SelfHarmActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
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
        MenuItem item = menu.findItem(R.id.action_next);
        item.setVisible(false);
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
            case R.id.action_previous:
                previousPage();
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
            case R.id.action_emergency:
                emergencyInfo();
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

    private void emergencyInfo() {
        startActivity(new Intent(SelfHarmActivity.this,EmergencyActivity.class));
    }

    private void takeNote() {
        startActivity(new Intent(SelfHarmActivity.this,NotesActivity.class));
    }

    private void sendEmail() {
        startActivity(new Intent(SelfHarmActivity.this,SendEmailActivity.class));
    }

    private void settingsInfo() {
        startActivity(new Intent(SelfHarmActivity.this,SettingsActivity.class));
    }

    private void showDiagram() {
        startActivity(new Intent(SelfHarmActivity.this, Statistics.class));
    }

    private void statisticsInfo() {
        startActivity(new Intent(SelfHarmActivity.this, StatisticsActivity.class));
    }

    private void calendarInfo() {
        Calendar today = Calendar.getInstance();

        Uri uriCalendar = Uri.parse("content://com.android.calendar/time/" + String.valueOf(today.getTimeInMillis()));
        Intent intentCalendar = new Intent(Intent.ACTION_VIEW,uriCalendar);

        //Use the native calendar app to view the date
        startActivity(intentCalendar);
        //startActivity(new Intent(HealthApp.this, CalendarActivity.class));
    }

    private void previousPage() {
        startActivity(new Intent(SelfHarmActivity.this, EnterValuesActivity.class));
    }

    private void homePage() {
        startActivity(new Intent(SelfHarmActivity.this, HealthApp.class));
    }

    private void profileInfo() {
        startActivity(new Intent(SelfHarmActivity.this, UserProfile.class));
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(SelfHarmActivity.this, MainActivity.class));
        finish();
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }*/
   /* class LearnGesture extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            if(event2.getX() > event1.getX()){

                Intent intent = new Intent(SelfHarmActivity.this,EnterValuesActivity.class);
                //finish();
                startActivity(intent);
            }else
            if(event2.getX()<event1.getX()){

                Intent intent = new Intent(SelfHarmActivity.this,FeelingsActivity.class);
                //finish();
                startActivity(intent);



            }
            return true;

        }
    }*/

}


