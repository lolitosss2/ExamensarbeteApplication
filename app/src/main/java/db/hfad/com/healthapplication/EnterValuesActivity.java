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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;


/**
 * Created by Lolita on 2016-10-30.
 */
public class EnterValuesActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entervalues);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        Date = (TextView)findViewById(R.id.textViewCurrentDateField);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
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

        //TODO numbers or smtg behind these faces or what?

        Smiley1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
            }
        });
        Smiley9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterValuesActivity.this, SelfHarmActivity.class));
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
        }
