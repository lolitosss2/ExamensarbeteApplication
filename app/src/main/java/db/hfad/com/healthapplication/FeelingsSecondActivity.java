package db.hfad.com.healthapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Lolita on 2016-11-01.
 */
public class FeelingsSecondActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;


    private SeekBar seekBarLoneliness;
    private SeekBar seekBarAnger;
    private SeekBar seekBarSelfRespect;

    private TextView textViewLoneliness;
    private TextView textViewAnger;
    private TextView textViewSelfRespect;

    private Button ButtonNext;

    public static TextView Date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feelingssecond);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        Date = (TextView) findViewById(R.id.textViewCurrentDateField);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
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
            }
        });
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
                //TODO
                return true;
            case R.id.action_calender:
                //TODO
                return true;
            case R.id.action_help:
                //TODO
                return true;
            case R.id.action_sendEmail:
                //TODO
                return true;
            case R.id.action_statistics:
                logout();
                return true;
            case R.id.action_settings:
                //TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

}
