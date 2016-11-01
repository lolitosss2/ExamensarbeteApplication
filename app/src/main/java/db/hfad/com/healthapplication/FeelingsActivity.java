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
public class FeelingsActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;


    private SeekBar seekBarSadness;
    private SeekBar seekBarAnxiety;
    private SeekBar seekBarShame;
    private SeekBar seekBarEmptyness;

    private TextView textViewSadness;
    private TextView textViewAnxiety;
    private TextView textViewShame;
    private TextView textViewEmptyness;

    private Button ButtonNext;

    public static TextView Date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feelings);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        Date = (TextView) findViewById(R.id.textViewCurrentDateField);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Date.setText(currentDateTimeString);

        seekBarSadness = (SeekBar)findViewById(R.id.seekBarSadnessField);
        textViewSadness = (TextView)findViewById(R.id.textViewSadnessPer);

        seekBarAnxiety = (SeekBar)findViewById(R.id.seekBarAnxietyField);
        textViewAnxiety = (TextView)findViewById(R.id.textViewAnxietyPer);

        seekBarShame = (SeekBar)findViewById(R.id.seekBarShameField);
        textViewShame = (TextView)findViewById(R.id.textViewShamePer);

        seekBarEmptyness = (SeekBar)findViewById(R.id.seekBarEmptynessField);
        textViewEmptyness = (TextView)findViewById(R.id.textViewEmptynessPer);

        ButtonNext = (Button)findViewById(R.id.buttonNext2);

        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeelingsActivity.this, FeelingsSecondActivity.class));
            }
        });


        String text = seekBarSadness.getProgress() + " % " /*+ seekBar.getMax()*/;
        textViewSadness.setText(text);

        seekBarSadness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                textViewSadness.setText(resultText);
            }
        });

        String textAnxiety = seekBarAnxiety.getProgress() + " % " /*+ seekBar.getMax()*/;
        textViewAnxiety.setText(textAnxiety);

        seekBarAnxiety.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                textViewAnxiety.setText(resultText);
            }
        });



        String textShame = seekBarShame.getProgress() + " % " /*+ seekBar.getMax()*/;
        textViewShame.setText(textShame);

        seekBarShame.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                textViewShame.setText(resultText);
            }
        });

        String textEmptyness = seekBarEmptyness.getProgress() + " % " /*+ seekBar.getMax()*/;
        textViewEmptyness.setText(textEmptyness);

        seekBarEmptyness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                textViewEmptyness.setText(resultText);
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
        return true;
    }
    /*
     * Do some action based on user request
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                nextPage();
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

    private void nextPage() {
        startActivity(new Intent(FeelingsActivity.this, FeelingsSecondActivity.class));
    }

    private void homePage() {
        startActivity(new Intent(FeelingsActivity.this, HealthApp.class));
    }

    private void profileInfo() {
        startActivity(new Intent(FeelingsActivity.this, UserProfile.class));

    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(FeelingsActivity.this, MainActivity.class));
        finish();
    }
}
