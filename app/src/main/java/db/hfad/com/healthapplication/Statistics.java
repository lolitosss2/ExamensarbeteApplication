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


import com.dzaitsev.android.widget.RadarChartView;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Paint.Style.FILL;



/**
 * Created by Lolita on 2016-11-02.
 */
public class Statistics extends AppCompatActivity{


    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    private BarChart barchart;
    private BarChart barchartLoneliness;


    private ArrayList<BarEntry> barEntries;
    private ArrayList<BarEntry> barEntriesLoneliness;
    private BarDataSet barDataSet;
    private BarDataSet barDataSetLoneliness;
    private ArrayList<String> theDates;
    private BarData theData;
    private BarData theDataLoneliness;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        TabHost th = (TabHost)findViewById(R.id.tabhost);

        th.setup();
        TabHost.TabSpec specs = th.newTabSpec("Tab1");
        specs.setContent(R.id.tab1);
        specs.setIndicator("RadarDiagram");
        th.addTab(specs);

        specs = th.newTabSpec("Tab2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("Sadness");
        th.addTab(specs);

        specs = th.newTabSpec("Tab3");
        specs.setContent(R.id.tab3);
        specs.setIndicator("Loneliness");
        th.addTab(specs);

        database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        final Map<String, Float> axis = new LinkedHashMap<>(10);

        axis.put("Confusion", 0F);
        axis.put("Happiness", 0F);
        axis.put("Indifference", 0F);
        axis.put("Anger", 0F);
        axis.put("Fear", 0F);
        axis.put("Sadness", 0F);
        axis.put("Inlove", 0F);
        axis.put("Dizziness", 0F);
        axis.put("Crying", 0F);


        // Set your data to the view
        final RadarChartView chartView = (RadarChartView) findViewById(R.id.chartView);
        chartView.setAxis(axis);
        chartView.setAxisMax(100.0F);         // set max value for the chart
        chartView.setAutoSize(true);             // auto balance the chart
        chartView.setCirclesOnly(true);          // if you want circles instead of polygons
        chartView.setChartStyle(FILL);           // chart drawn with this style will be filled not stroked


        mDatabase = FirebaseDatabase.getInstance().getReference().child("EnterValues").child(mCurrentUser.getUid()).child("11");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> list = new ArrayList<String>(); // Result will be holded Here
                for (DataSnapshot data : snapshot.getChildren()) {
                    list.add(String.valueOf(data.getValue())); //add result into array list
                }
                System.out.println(list);
                for (int i = 0; i < list.size(); i++) {

                    if (list.get(i).contains("sad")) {
                        chartView.addOrReplace("Sadness", 14.0F);
                    }
                    else if (list.get(i).contains("indifferent")) {
                        chartView.addOrReplace("Indifference", 1.0F);
                    }
                    else if (list.get(i).contains("dizzy")) {
                        chartView.addOrReplace("Dizziness", 1.0F);
                    }
                    else if (list.get(i).contains("scared")) {
                        chartView.addOrReplace("Fear", 1.0F);
                    }
                    else if (list.get(i).contains("happy")) {
                        chartView.addOrReplace("Happiness", 1.0F);
                    }
                    else if (list.get(i).contains("confused")) {
                        chartView.addOrReplace("Confusion", 10.0F);
                    }
                    else if (list.get(i).contains("crying")) {
                        chartView.addOrReplace("Crying", 18.0F);
                    }
                    else if (list.get(i).contains("inlove")) {
                        chartView.addOrReplace("Inlove", 1.0F);
                    }
                    else if (list.get(i).contains("angry")) {
                        chartView.addOrReplace("Anger", 1.0F);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        barchart = (BarChart)findViewById(R.id.bargraph);

        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(48f,0));
        barEntries.add(new BarEntry(25f,1));
        barEntries.add(new BarEntry(66f,2));
        barEntries.add(new BarEntry(0f,4));
        barEntries.add(new BarEntry(0f,5));

        barDataSet = new BarDataSet(barEntries,"Dates");

        theDates = new ArrayList<>();
        theDates.add("August");
        theDates.add("September");
        theDates.add("October");
        theDates.add("November");
        theDates.add("December");
        theDates.add("January");

        theData = new BarData(theDates,barDataSet);

        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        barchart.animateY(3000);
        barchart.setTouchEnabled(true);
        barchart.setDragEnabled(true);
        barchart.setScaleEnabled(true);


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
                barEntries.add(new BarEntry(average, 3));
                barchart.setData(theData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        barchartLoneliness = (BarChart)findViewById(R.id.bargraphLoneliness);

        barEntriesLoneliness = new ArrayList<>();
        barEntriesLoneliness.add(new BarEntry(0f,0));
        barEntriesLoneliness.add(new BarEntry(12f,1));
        barEntriesLoneliness.add(new BarEntry(10f,2));
        barEntriesLoneliness.add(new BarEntry(0f,4));
        barEntriesLoneliness.add(new BarEntry(0f,5));

        barDataSetLoneliness = new BarDataSet(barEntriesLoneliness,"Dates");

        theDates = new ArrayList<>();
        theDates.add("August");
        theDates.add("September");
        theDates.add("October");
        theDates.add("November");
        theDates.add("December");
        theDates.add("January");

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
                barEntriesLoneliness.add(new BarEntry(average, 3));
                barchartLoneliness.setData(theDataLoneliness);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
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
        startActivity(new Intent(Statistics.this, DrugsandAlcoholActivity.class));
    }

    private void showDiagram() {
        startActivity(new Intent(Statistics.this, Statistics.class));
    }

    private void homePage() {
        startActivity(new Intent(Statistics.this, HealthApp.class));
    }

    private void profileInfo() {
        startActivity(new Intent(Statistics.this, UserProfile.class));
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(Statistics.this, MainActivity.class));
        finish();
    }




}
