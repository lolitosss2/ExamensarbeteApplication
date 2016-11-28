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

/**
 * Created by Lolita on 2016-11-01.
 */
public class FeelingsSecondActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    private SeekBar seekBarLoneliness;
    private SeekBar seekBarAnger;
    private SeekBar seekBarSelfRespect;

    private TextView textViewLoneliness;
    private TextView textViewAnger;
    private TextView textViewSelfRespect;

    private Button ButtonNext;

    public static TextView Date;
    private DateFormat dateFormat;
    private Date date;
    private String currentDateTimeString;

    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feelingssecond);

        gestureObject = new GestureDetectorCompat(this, new FeelingsSecondActivity.LearnGesture());

        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Feelings");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        dateFormat = new SimpleDateFormat("MM");
        date = new Date();

        Date = (TextView) findViewById(R.id.textViewCurrentDateField);

        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Date.setText(currentDateTimeString);

        seekBarLoneliness = (SeekBar) findViewById(R.id.seekBarLonelinessField);
        seekBarAnger = (SeekBar) findViewById(R.id.seekBarAngerField);
        seekBarSelfRespect = (SeekBar) findViewById(R.id.seekBarSelfRespectField);


        textViewLoneliness = (TextView) findViewById(R.id.textViewLonelinessPer);
        textViewAnger = (TextView) findViewById(R.id.textViewAngerPer);
        textViewSelfRespect = (TextView) findViewById(R.id.textViewSelfRespectPer);

        ButtonNext = (Button) findViewById(R.id.buttonNext3);

        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeelingsSecondActivity.this, DrugsandAlcoholActivity.class));
            }
        });


        String text = seekBarLoneliness.getProgress() + " % " /*+ seekBar.getMax()*/;
        textViewLoneliness.setText(text);

        seekBarLoneliness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                textViewLoneliness.setText(resultText);
                recordFeeling(resultText,"Loneliness");
            }
        });

        String text2 = seekBarAnger.getProgress() + " % " /*+ seekBar.getMax()*/;
        textViewAnger.setText(text2);

        seekBarAnger.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                textViewAnger.setText(resultText);
                recordFeeling(resultText,"Anger");
            }
        });

        String text3 = seekBarSelfRespect.getProgress() + " % " /*+ seekBar.getMax()*/;
        textViewSelfRespect.setText(text3);

        seekBarSelfRespect.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                textViewSelfRespect.setText(resultText);
                recordFeeling(resultText,"Self-respect");
            }
        });
    }

    private void recordFeeling(String percentage, String feeling) {
        percentage = stripNonDigits(percentage);
        if (!TextUtils.isEmpty(percentage)) {
            if (mAuth.getCurrentUser() != null) {

                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference cureent_user_db = mDatabase.child(user_id)
                        .child(feeling)
                        .child(dateFormat.format(date))
                        .child(currentDateTimeString);
                cureent_user_db.setValue(percentage);

            } else {
                //TODO
                Toast.makeText(FeelingsSecondActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
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
            case R.id.action_notes:
                //TODO
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
    private void sendEmail() {
        startActivity(new Intent(FeelingsSecondActivity.this,SendEmailActivity.class));
    }

    private void settingsInfo() {
        startActivity(new Intent(FeelingsSecondActivity.this,SettingsActivity.class));
    }


    private void statisticsInfo() {
        startActivity(new Intent(FeelingsSecondActivity.this, StatisticsActivity.class));
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
        startActivity(new Intent(FeelingsSecondActivity.this, Statistics.class));
    }

    private void previousPage() {
        startActivity(new Intent(FeelingsSecondActivity.this, FeelingsActivity.class));
    }

    private void homePage() {
        startActivity(new Intent(FeelingsSecondActivity.this, HealthApp.class));
    }

    private void profileInfo() {
        startActivity(new Intent(FeelingsSecondActivity.this, UserProfile.class));
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(FeelingsSecondActivity.this, MainActivity.class));
        finish();
    }

    public static String stripNonDigits(
            final CharSequence input){
        final StringBuilder sb = new StringBuilder(
                input.length());
        for(int i = 0; i < input.length(); i++){
            final char c = input.charAt(i);
            if(c > 47 && c < 58){
                sb.append(c);
            }
        }
        return sb.toString();
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
                Intent intent = new Intent(FeelingsSecondActivity.this,FeelingsActivity.class);
                //finish();
                startActivity(intent);

            }else
            if(event2.getX()<event1.getX()){

                Intent intent = new Intent(FeelingsSecondActivity.this,DrugsandAlcoholActivity.class);
                //finish();
                startActivity(intent);



            }
            return true;

        }
    }

}
