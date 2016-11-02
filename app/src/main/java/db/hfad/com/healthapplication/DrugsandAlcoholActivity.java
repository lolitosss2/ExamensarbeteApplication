package db.hfad.com.healthapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Lolita on 2016-11-01.
 */
public class DrugsandAlcoholActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;



    public static TextView Date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugsandalcohol);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        Date = (TextView) findViewById(R.id.textViewCurrentDateField);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Date.setText(currentDateTimeString);
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
        return true;
    }
    /*
     * Do some action based on user request
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
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
        startActivity(new Intent(DrugsandAlcoholActivity.this, Statistics.class));
    }

    private void homePage() {
        startActivity(new Intent(DrugsandAlcoholActivity.this, HealthApp.class));
    }

    private void profileInfo() {
        startActivity(new Intent(DrugsandAlcoholActivity.this, UserProfile.class));

    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(DrugsandAlcoholActivity.this, MainActivity.class));
        finish();
    }
}
