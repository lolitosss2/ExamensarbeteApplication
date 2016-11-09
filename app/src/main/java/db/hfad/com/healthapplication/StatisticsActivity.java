package db.hfad.com.healthapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Lolita on 2016-11-08.
 */

public class StatisticsActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    private BarChart barchartAnxiety;
    private ArrayList<BarEntry> barEntriesAnxiety;
    private BarDataSet barDataSetAnxiety;
    private ArrayList<String> theDates;
    private BarData theDataAnxiety;

    private BarChart barchartShame;
    private ArrayList<BarEntry> barEntriesShame;
    private BarDataSet barDataSetShame;
    private BarData theDataShame;

    private BarChart barchartEmptyness;
    private ArrayList<BarEntry> barEntriesEmptyness;
    private BarDataSet barDataSetEmptyness;
    private BarData theDataEmptyness;

    private BarChart barchartAnger;
    private ArrayList<BarEntry> barEntriesAnger;
    private BarDataSet barDataSetAnger;
    private BarData theDataAnger;

    private BarChart barchartSelfRespect;
    private ArrayList<BarEntry> barEntriesSelfRespect;
    private BarDataSet barDataSetSelfRespect;
    private BarData theDataSelfRespect;

    private BarChart barchartSadness;
    private ArrayList<BarEntry> barEntriesSadness;
    private BarDataSet barDataSetSadness;
    private BarData theDataSadness;

    private BarChart barchartLoneliness;
    private ArrayList<BarEntry> barEntriesLoneliness;
    private BarDataSet barDataSetLoneliness;
    private BarData theDataLoneliness;

    public static float Sadness;
    public static float Anxiety;
    public  static float Shame;
    public static float Emptyness;
    public  static float Loneliness;
    public static float Anger;
    public  static float SelfRespect;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondstatistics);

        TabHost th = (TabHost)findViewById(R.id.tabhostsecond);

        th.setup();
        TabHost.TabSpec specs = th.newTabSpec("Tab1");
        specs.setContent(R.id.tab1Anxiety);
        specs.setIndicator("Anx");
        th.addTab(specs);

        specs = th.newTabSpec("Tab2");
        specs.setContent(R.id.tab2Shame);
        specs.setIndicator("Shame");
        th.addTab(specs);

        specs = th.newTabSpec("Tab3");
        specs.setContent(R.id.tab3Emptyness);
        specs.setIndicator("Emptyness");
        th.addTab(specs);

        specs = th.newTabSpec("Tab4");
        specs.setContent(R.id.tab4Anger);
        specs.setIndicator("Anger");
        th.addTab(specs);

        specs = th.newTabSpec("Tab5");
        specs.setContent(R.id.tab5SelfRespect);
        specs.setIndicator("Self-Respect");
        th.addTab(specs);

        specs = th.newTabSpec("Tab6");
        specs.setContent(R.id.tab6Sadness);
        specs.setIndicator("Sadness");
        th.addTab(specs);

        specs = th.newTabSpec("Tab7");
        specs.setContent(R.id.tab7Loneliness);
        specs.setIndicator("Loneliness");
        th.addTab(specs);

        database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        barchartAnxiety = (BarChart)findViewById(R.id.bargraphAnxiety);

        barEntriesAnxiety = new ArrayList<>();
        barEntriesAnxiety.add(new BarEntry(48f,0));
        barEntriesAnxiety.add(new BarEntry(25f,1));
        barEntriesAnxiety.add(new BarEntry(66f,2));
        barEntriesAnxiety.add(new BarEntry(0f,4));
        barEntriesAnxiety.add(new BarEntry(0f,5));

        barDataSetAnxiety = new BarDataSet(barEntriesAnxiety,"Dates");

        theDates = new ArrayList<>();
        theDates.add("August");
        theDates.add("September");
        theDates.add("October");
        theDates.add("November");
        theDates.add("December");
        theDates.add("January");

        theDataAnxiety = new BarData(theDates,barDataSetAnxiety);

        barDataSetAnxiety.setColors(ColorTemplate.LIBERTY_COLORS);
        barchartAnxiety.animateY(3000);
        barchartAnxiety.setTouchEnabled(true);
        barchartAnxiety.setDragEnabled(true);
        barchartAnxiety.setScaleEnabled(true);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Feelings")
                .child(mCurrentUser.getUid())
                .child("Anxiety")
                .child("11");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> list = new ArrayList<String>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    list.add(String.valueOf(data.getValue())); //add result into array list
                }
                System.out.println(list);

                int sum = 0;
                float average = 0;

                for (int i = 0; i < list.size(); i++) {
                    sum = sum + Integer.parseInt(list.get(i));
                    average = sum / list.size();
                }

                Anxiety = average;

                barEntriesAnxiety.add(new BarEntry(average, 3));
                barchartAnxiety.setData(theDataAnxiety);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //SHAME

        barchartShame = (BarChart)findViewById(R.id.bargraphShame);

        barEntriesShame = new ArrayList<>();
        barEntriesShame.add(new BarEntry(48f,0));
        barEntriesShame.add(new BarEntry(25f,1));
        barEntriesShame.add(new BarEntry(66f,2));
        barEntriesShame.add(new BarEntry(0f,4));
        barEntriesShame.add(new BarEntry(0f,5));

        barDataSetShame = new BarDataSet(barEntriesShame,"Dates");

        theDataShame = new BarData(theDates,barDataSetShame);

        barDataSetShame.setColors(ColorTemplate.LIBERTY_COLORS);
        barchartShame.animateY(3000);
        barchartShame.setTouchEnabled(true);
        barchartShame.setDragEnabled(true);
        barchartShame.setScaleEnabled(true);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Feelings")
                .child(mCurrentUser.getUid())
                .child("Shame")
                .child("11");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> list = new ArrayList<String>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    list.add(String.valueOf(data.getValue())); //add result into array list
                }
                System.out.println(list);

                int sum = 0;
                float average = 0;

                for (int i = 0; i < list.size(); i++) {
                    sum = sum + Integer.parseInt(list.get(i));
                    average = sum / list.size();
                }
                Shame = average;
                barEntriesShame.add(new BarEntry(average, 3));
                barchartShame.setData(theDataShame);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //EMptyness


        barchartEmptyness = (BarChart)findViewById(R.id.bargraphEmptyness);

        barEntriesEmptyness = new ArrayList<>();
        barEntriesEmptyness.add(new BarEntry(48f,0));
        barEntriesEmptyness.add(new BarEntry(25f,1));
        barEntriesEmptyness.add(new BarEntry(66f,2));
        barEntriesEmptyness.add(new BarEntry(0f,4));
        barEntriesEmptyness.add(new BarEntry(0f,5));

        barDataSetEmptyness = new BarDataSet(barEntriesEmptyness,"Dates");

        theDataEmptyness = new BarData(theDates,barDataSetEmptyness);

        barDataSetEmptyness.setColors(ColorTemplate.LIBERTY_COLORS);
        barchartEmptyness.animateY(3000);
        barchartEmptyness.setTouchEnabled(true);
        barchartEmptyness.setDragEnabled(true);
        barchartEmptyness.setScaleEnabled(true);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Feelings")
                .child(mCurrentUser.getUid())
                .child("Emptyness")
                .child("11");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> list = new ArrayList<String>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    list.add(String.valueOf(data.getValue())); //add result into array list
                }
                System.out.println(list);

                int sum = 0;
                float average = 0;

                for (int i = 0; i < list.size(); i++) {
                    sum = sum + Integer.parseInt(list.get(i));
                    average = sum / list.size();
                }
                Emptyness = average;
                barEntriesEmptyness.add(new BarEntry(average, 3));
                barchartEmptyness.setData(theDataEmptyness);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Anger

        barchartAnger = (BarChart)findViewById(R.id.bargraphAnger);

        barEntriesAnger = new ArrayList<>();
        barEntriesAnger.add(new BarEntry(48f,0));
        barEntriesAnger.add(new BarEntry(25f,1));
        barEntriesAnger.add(new BarEntry(66f,2));
        barEntriesAnger.add(new BarEntry(0f,4));
        barEntriesAnger.add(new BarEntry(0f,5));

        barDataSetAnger = new BarDataSet(barEntriesAnger,"Dates");

        theDataAnger = new BarData(theDates,barDataSetAnger);

        barDataSetAnger.setColors(ColorTemplate.LIBERTY_COLORS);
        barchartAnger.animateY(3000);
        barchartAnger.setTouchEnabled(true);
        barchartAnger.setDragEnabled(true);
        barchartAnger.setScaleEnabled(true);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Feelings")
                .child(mCurrentUser.getUid())
                .child("Anger")
                .child("11");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> list = new ArrayList<String>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    list.add(String.valueOf(data.getValue())); //add result into array list
                }
                System.out.println(list);

                int sum = 0;
                float average = 0;

                for (int i = 0; i < list.size(); i++) {
                    sum = sum + Integer.parseInt(list.get(i));
                    average = sum / list.size();
                }
                Anger = average;
                barEntriesAnger.add(new BarEntry(average, 3));
                barchartAnger.setData(theDataAnger);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //SELFRESPECT


        barchartSelfRespect = (BarChart)findViewById(R.id.bargraphSelfrespect);

        barEntriesSelfRespect = new ArrayList<>();
        barEntriesSelfRespect.add(new BarEntry(48f,0));
        barEntriesSelfRespect.add(new BarEntry(25f,1));
        barEntriesSelfRespect.add(new BarEntry(66f,2));
        barEntriesSelfRespect.add(new BarEntry(0f,4));
        barEntriesSelfRespect.add(new BarEntry(0f,5));

        barDataSetSelfRespect = new BarDataSet(barEntriesSelfRespect,"Dates");

        theDataSelfRespect = new BarData(theDates,barDataSetSelfRespect);

        barDataSetSelfRespect.setColors(ColorTemplate.LIBERTY_COLORS);
        barchartSelfRespect.animateY(3000);
        barchartSelfRespect.setTouchEnabled(true);
        barchartSelfRespect.setDragEnabled(true);
        barchartSelfRespect.setScaleEnabled(true);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Feelings")
                .child(mCurrentUser.getUid())
                .child("Self-respect")
                .child("11");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> list = new ArrayList<String>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    list.add(String.valueOf(data.getValue())); //add result into array list
                }
                System.out.println(list);

                int sum = 0;
                float average = 0;

                for (int i = 0; i < list.size(); i++) {
                    sum = sum + Integer.parseInt(list.get(i));
                    average = sum / list.size();
                }
                SelfRespect = average;
                barEntriesSelfRespect.add(new BarEntry(average, 3));
                barchartSelfRespect.setData(theDataSelfRespect);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //Sadness


        barchartSadness = (BarChart)findViewById(R.id.bargraphSadness);

        barEntriesSadness = new ArrayList<>();
        barEntriesSadness.add(new BarEntry(48f,0));
        barEntriesSadness.add(new BarEntry(25f,1));
        barEntriesSadness.add(new BarEntry(66f,2));
        barEntriesSadness.add(new BarEntry(0f,4));
        barEntriesSadness.add(new BarEntry(0f,5));

        barDataSetSadness = new BarDataSet(barEntriesSadness,"Dates");

        theDataSadness = new BarData(theDates,barDataSetSadness);

        barDataSetSadness.setColors(ColorTemplate.LIBERTY_COLORS);
        barchartSadness.animateY(3000);
        barchartSadness.setTouchEnabled(true);
        barchartSadness.setDragEnabled(true);
        barchartSadness.setScaleEnabled(true);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Feelings")
                .child(mCurrentUser.getUid())
                .child("Sadness")
                .child("11");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> list = new ArrayList<String>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    list.add(String.valueOf(data.getValue())); //add result into array list
                }
                System.out.println(list);

                int sum = 0;
                float average = 0;

                for (int i = 0; i < list.size(); i++) {
                    sum = sum + Integer.parseInt(list.get(i));
                    average = sum / list.size();
                }
                Sadness = average;
                barEntriesSadness.add(new BarEntry(average, 3));
                barchartSadness.setData(theDataSadness);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //Loneliness

        barchartLoneliness= (BarChart)findViewById(R.id.bargraphLoneliness);

        barEntriesLoneliness = new ArrayList<>();
        barEntriesLoneliness.add(new BarEntry(48f,0));
        barEntriesLoneliness.add(new BarEntry(25f,1));
        barEntriesLoneliness.add(new BarEntry(66f,2));
        barEntriesLoneliness.add(new BarEntry(0f,4));
        barEntriesLoneliness.add(new BarEntry(0f,5));

        barDataSetLoneliness = new BarDataSet(barEntriesLoneliness,"Dates");

        theDataLoneliness = new BarData(theDates,barDataSetLoneliness);

        barDataSetLoneliness.setColors(ColorTemplate.LIBERTY_COLORS);
        barchartLoneliness.animateY(3000);
        barchartLoneliness.setTouchEnabled(true);
        barchartLoneliness.setDragEnabled(true);
        barchartLoneliness.setScaleEnabled(true);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Feelings")
                .child(mCurrentUser.getUid())
                .child("Loneliness")
                .child("11");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> list = new ArrayList<String>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    list.add(String.valueOf(data.getValue())); //add result into array list
                }
                System.out.println(list);

                int sum = 0;
                float average = 0;

                for (int i = 0; i < list.size(); i++) {
                    sum = sum + Integer.parseInt(list.get(i));
                    average = sum / list.size();
                }
                Loneliness= average;
                barEntriesLoneliness.add(new BarEntry(average, 3));
                barchartLoneliness.setData(theDataLoneliness);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.healthapp_menu, menu);
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
                //TODO
                return true;
            case R.id.action_calender:
                calendarInfo();
                return true;
            case R.id.action_help:
                //TODO
                return true;
            case R.id.action_sendEmail:
                //TODO
                return true;
            case R.id.action_statistics:
                showDiagram();
                return true;
            case R.id.action_settings:
                //TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        startActivity(new Intent(StatisticsActivity.this, DrugsandAlcoholActivity.class));
    }

    private void showDiagram() {
        startActivity(new Intent(StatisticsActivity.this, Statistics.class));
    }

    private void homePage() {
        startActivity(new Intent(StatisticsActivity.this, HealthApp.class));
    }

    private void profileInfo() {
        startActivity(new Intent(StatisticsActivity.this, UserProfile.class));
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(StatisticsActivity.this, MainActivity.class));
        finish();
    }
}
