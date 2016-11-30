package db.hfad.com.healthapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by Lolita on 2016-11-28.
 */
public class YourStrategiesActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourstrategies);

        TextView tvNamesList = (TextView) findViewById(R.id.tvNamesList);
        tvNamesList.setText(Html.fromHtml(getString(R.string.names)));



       /* TextView tvStrategysList = (TextView) findViewById(R.id.tvStrategyList);
        tvNamesList.setText(Html.fromHtml(getString(R.string.strategies)));*/
    }
}
