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
 * Created by Lolita on 2016-10-30.
 */
public class SelfHarmActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    public static TextView Date;

    private SeekBar seekBar;
    private SeekBar seekBar2;
    private TextView textView;
    private TextView textView2;

    private Button ButtonNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfharm);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        Date = (TextView) findViewById(R.id.textViewCurrentDateField);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
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
            }
        });

        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelfHarmActivity.this, FeelingsActivity.class));
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
}


