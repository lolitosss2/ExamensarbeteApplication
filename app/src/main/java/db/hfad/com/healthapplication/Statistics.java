package db.hfad.com.healthapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;



import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;


import com.dzaitsev.android.widget.RadarChartView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
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

    private LineChart linechart;

    StatisticsActivity sa = new StatisticsActivity();


    public static float Anxiety;
    private PieChart mChart;
    // we're going to display pie chart for smartphones martket shares
    //private float[] yData = { 5, 10, 15, 30, 40 ,};
    private float[] yData = {};
    private String[] xData = { "Sadness", "Anxiety", "Shame", "Emptyness", "Loneliness", "Anger" , "Self-Respect" };



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
        specs.setIndicator("All feelings");
        th.addTab(specs);



        database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();


        final Map<String, Float> axis = new LinkedHashMap<>(10);

        /*axis.put("Confusion", 0F);
        axis.put("Happiness", 0F);
        axis.put("Indifference", 0F);
        axis.put("Anger", 0F);
        axis.put("Fear", 0F);
        axis.put("Sadness", 0F);
        axis.put("Inlove", 0F);
        axis.put("Dizziness", 0F);
        axis.put("Crying", 0F);*/


        axis.put("Interest", 0F);
        axis.put("Anger", 0F);
        axis.put("Contempt", 0F);
        axis.put("Disgust", 0F);
        axis.put("Fear", 0F);
        axis.put("Joy", 0F);
        axis.put("Sadness", 0F);
        axis.put("Surprise", 0F);
        axis.put("Shame", 0F);


        // Set your data to the view
        final RadarChartView chartView = (RadarChartView) findViewById(R.id.chartView);
        chartView.setAxis(axis);
        chartView.setAxisMax(100.0F);         // set max value for the chart
        chartView.setAutoSize(true);             // auto balance the chart
        chartView.setCirclesOnly(true);          // if you want circles instead of polygons
        chartView.setChartStyle(FILL);           // chart drawn with this style will be filled not stroked


        mDatabase = FirebaseDatabase.getInstance().getReference().child("EnterValues")
                .child(mCurrentUser.getUid())
                //.child("12");
                .child("11");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> list = new ArrayList<String>(); // Result will be holded Here
                for (DataSnapshot data : snapshot.getChildren()) {
                    list.add(String.valueOf(data.getValue())); //add result into array list
                }
                System.out.println(list);
                float sum = 0;
                float sum1 = 0;
                float sum2 = 0;
                float sum3 = 0;
                float sum4 = 0;
                float sum5 = 0;
                float sum6 = 0;
                float sum7 = 0;
                float sum8 = 0;

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).contains("Interest")) {
                        sum = sum +1;
                        chartView.addOrReplace("Interest", sum);
                       // chartView.addOrReplace("Sadness", sum);
                    }
                    else if (list.get(i).contains("Joy")) {
                        sum1 = sum1+1;
                        chartView.addOrReplace("Joy", sum1);
                        //chartView.addOrReplace("Indifference", sum1);
                    }
                    else if (list.get(i).contains("Surprise")) {
                        sum2 = sum2+1;
                        chartView.addOrReplace("Surprise", sum2);
                        //chartView.addOrReplace("Dizziness", sum2);
                    }
                    else if (list.get(i).contains("Anger")) {
                        sum3 = sum3+1;
                        chartView.addOrReplace("Anger", sum3);
                        //chartView.addOrReplace("Fear", sum3);
                    }
                    else if (list.get(i).contains("Fear")) {
                        sum4 = sum4+1;
                        chartView.addOrReplace("Fear", sum4);
                        //chartView.addOrReplace("Happiness", sum4);
                    }
                    else if (list.get(i).contains("Distress")) {
                        sum5 = sum5 + 1;
                        chartView.addOrReplace("Distress", sum5);
                        //chartView.addOrReplace("Confusion", sum5);
                    }
                    else if (list.get(i).contains("Shame")) {
                        sum6 = sum6 +1;
                        chartView.addOrReplace("Shame", sum6);
                        //chartView.addOrReplace("Crying", sum6);
                    }
                    else if (list.get(i).contains("Disgust")) {
                        sum7 = sum7 + 1;
                        chartView.addOrReplace("Disgust", sum7);
                        //chartView.addOrReplace("Inlove", sum7);
                    }
                    else if (list.get(i).contains("Love")) {
                        sum8 = sum8 +1;
                        chartView.addOrReplace("Love", sum8);
                        //chartView.addOrReplace("Anger", sum8);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

  //NEW CHANGES //////////////////////////////////////////////////////////////////////////////////////////////////////////////

      /*  mDatabase = FirebaseDatabase.getInstance().getReference().child("Feelings")
                .child(mCurrentUser.getUid())
                .child("Anxiety")
                //.child("12");
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        */






















            yData = new float[]{sa.Sadness,sa.Anxiety,sa.Shame,sa.Emptyness,sa.Loneliness,sa.Anger,sa.SelfRespect};
        //mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mChart = new PieChart(this);
        // add pie chart to main layout
        //mainLayout.addView(mChart);
        mChart = (PieChart)findViewById(R.id.PieChart);
       // mainLayout.setBackgroundColor(Color.parseColor("#55656C"));

        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Feelings");

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        //mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(Statistics.this,
                        xData[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        addData();

        // customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }


    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Feelings");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();

        /*linechart = (LineChart)findViewById(R.id.LineChart);

        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> yAXESsin = new ArrayList<>();
        ArrayList<Entry> yAXEScos = new ArrayList<>();
        double x = 0;
        int numDataPoints = 1000;


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

                //barEntriesAnxiety.add(new BarEntry(average, 3));
                //barchartAnxiety.setData(theDataAnxiety);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });




        for(int i=0;i<numDataPoints;i++){
            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            x = x + 0.1;
            yAXESsin.add(new Entry(sinFunction,i));
            yAXEScos.add(new Entry(cosFunction,i));
            xAXES.add(i, String.valueOf(x));
        }
        String[] xaxes = new String[xAXES.size()];
        for(int i=0; i<xAXES.size();i++){
            xaxes[i] = xAXES.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos,"cos");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(yAXESsin,"sin");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        linechart.setData(new LineData(xaxes,lineDataSets));

        linechart.setVisibleXRangeMaximum(65f);*/

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
    private void takeNote() {
        startActivity(new Intent(Statistics.this,NotesActivity.class));
    }

    private void sendEmail() {
        startActivity(new Intent(Statistics.this,SendEmailActivity.class));
    }

    private void settingsInfo() {
        startActivity(new Intent(Statistics.this,SettingsActivity.class));
    }

    private void statisticsInfo() {
        startActivity(new Intent(Statistics.this, StatisticsActivity.class));
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
