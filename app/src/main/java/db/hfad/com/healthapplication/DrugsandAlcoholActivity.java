package db.hfad.com.healthapplication;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import android.widget.EditText;
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
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;




/**
 * Created by Lolita on 2016-11-01.
 */
public class DrugsandAlcoholActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private FirebaseDatabase database;
    private DatabaseReference mDatabaseDrugsAlcohol;

    private EditText alcoholQuantity;
    private EditText medicationName;
    private EditText medicationQuantity;
    private TextView AQ;
    private TextView MN;
    private TextView MQ;

    private DateFormat dateFormat;
    private Date date;
    private String currentDateTimeString;

    public static TextView Date;

    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugsandalcohol);

        //gestureObject = new GestureDetectorCompat(this, new DrugsandAlcoholActivity.LearnGesture());

        database = FirebaseDatabase.getInstance();
        mDatabaseDrugsAlcohol = FirebaseDatabase.getInstance().getReference().child("DrugsAlcohol");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        dateFormat = new SimpleDateFormat("MM");
        date = new Date();

        Date = (TextView) findViewById(R.id.textViewCurrentDateField);

        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Date.setText(currentDateTimeString);

        alcoholQuantity = (EditText) findViewById(R.id.editTextAlcoholField);
        medicationName = (EditText) findViewById(R.id.editTextMedicationNameField);
        medicationQuantity = (EditText) findViewById(R.id.editTextQuantityField);

        AQ = (TextView)findViewById(R.id.textViewaqField);
        MN = (TextView)findViewById(R.id.textViewmnField);
        MQ = (TextView)findViewById(R.id.textViewmqField);


    }

    private void calenderLog() {

        final String alcoQuantity = alcoholQuantity.getText().toString().trim();
        final String medicName = medicationName.getText().toString().trim();
        final String medicQuantity = medicationQuantity.getText().toString().trim();

        if (!TextUtils.isEmpty(alcoQuantity) && !TextUtils.isEmpty(medicName) && !TextUtils.isEmpty(medicQuantity)) {
            if (mAuth.getCurrentUser() != null) {
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference cureent_user_db = mDatabaseDrugsAlcohol.child(user_id)
                        .child(dateFormat.format(date))
                        .child(currentDateTimeString);

                cureent_user_db.child("AlcoholQuantity").setValue(alcoQuantity);
                cureent_user_db.child("MedicationName").setValue(medicName);
                cureent_user_db.child("Quantity").setValue(medicQuantity);


                long calID = 3;
                long startMillis = 0;
                long endMillis = 0;
                Calendar beginTime = Calendar.getInstance();
                startMillis = beginTime.getTimeInMillis();
                Calendar endTime = Calendar.getInstance();
                endMillis = endTime.getTimeInMillis()+60*60*1000;

                TimeZone tz = TimeZone.getDefault();

                ContentResolver cr = getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, startMillis);
                values.put(CalendarContract.Events.DTEND, endMillis);
                values.put(CalendarContract.Events.TITLE, "Drugs&Alcohol");
                values.put(CalendarContract.Events.DESCRIPTION, "Alcohol, Quantity: " + alcoQuantity +"\n"
                        + "Medication Name: "+ medicName +"\n"
                        + "Quantity: " + medicQuantity);
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, tz.getID());
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

                    // get the event ID that is the last element in the Uri
                    long eventID = Long.parseLong(uri.getLastPathSegment());


                    Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                    builder.appendPath("time");
                    ContentUris.appendId(builder, startMillis);
                    //Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
                    //startActivity(intent);

                    startActivity(new Intent(DrugsandAlcoholActivity.this, NotesActivity.class));
                }
            } else {
            Toast.makeText(DrugsandAlcoholActivity.this,"Please fill all fields",Toast.LENGTH_LONG).show();
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
        startActivity(new Intent(DrugsandAlcoholActivity.this,EmergencyActivity.class));
    }

    private void takeNote() {
        startActivity(new Intent(DrugsandAlcoholActivity.this,NotesActivity.class));
    }

    private void sendEmail() {
        startActivity(new Intent(DrugsandAlcoholActivity.this,SendEmailActivity.class));
    }

    private void settingsInfo() {
        startActivity(new Intent(DrugsandAlcoholActivity.this,SettingsActivity.class));
    }

    private void statisticsInfo() {
        startActivity(new Intent(DrugsandAlcoholActivity.this, StatisticsActivity.class));
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
        startActivity(new Intent(DrugsandAlcoholActivity.this, Statistics.class));
    }

    private void previousPage() {
        calenderLog();
        //startActivity(new Intent(DrugsandAlcoholActivity.this, Statistics.class));
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


    //saveData
    public void saveInfo(View view){
        SharedPreferences sharedPref = getSharedPreferences("AlcoholDrugs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("alcoholQuantity",alcoholQuantity.getText().toString());
        editor.putString("medicationName",medicationName.getText().toString());
        editor.putString("medicationQuantity",medicationQuantity.getText().toString());
        editor.apply();

        Toast.makeText(this,"Saved!",Toast.LENGTH_LONG).show();

        sharedPref = getSharedPreferences("AlcoholDrugs", Context.MODE_PRIVATE);
        String alcoQuantity = sharedPref.getString("alcoholQuantity","");
        String medicName = sharedPref.getString("medicationName","");
        String medicQuantity = sharedPref.getString("medicationQuantity","");

        alcoholQuantity.setVisibility(View.INVISIBLE);
        AQ.setText(alcoQuantity);
        AQ.setVisibility(View.VISIBLE);
        medicationName.setVisibility(View.INVISIBLE);
        MN.setText(medicName);
        MN.setVisibility(View.VISIBLE);
        medicationQuantity.setVisibility(View.INVISIBLE);
        MQ.setText(medicQuantity);
        MQ.setVisibility(View.VISIBLE);
    }

    //print out data
    public void displayData(View view){
        MN.setVisibility(View.INVISIBLE);
        medicationName.setVisibility(View.VISIBLE);
        //System.out.println(alcoQuantity + "" + medicName + "" + medicQuantity);
    }

    public void editData(View view){
        AQ.setVisibility(View.INVISIBLE);
        alcoholQuantity.setVisibility(View.VISIBLE);
    }

    public void edit2Data(View view){
        MQ.setVisibility(View.INVISIBLE);
        medicationQuantity.setVisibility(View.VISIBLE);
    }

  /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }*/
   /*   class LearnGesture extends GestureDetector.SimpleOnGestureListener {


      @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            if(event2.getX() > event1.getX()){
               // Intent intent = new Intent(FeelingsSecondActivity.this,DrugsandAlcoholActivity.class);
                //finish();
                //startActivity(intent);
                Intent intent = new Intent(DrugsandAlcoholActivity.this,FeelingsSecondActivity.class);
                //finish();
                startActivity(intent);

            }else
            if(event2.getX()<event1.getX()){


            }
            return true;

        }
    }*/

}
