package hello.com.eventratingapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {

    private static final String TAG = "";
    float analyticalRatingCount = 0;

    ArrayList<String> roundCompetitorsAnalyticalRatingArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_chart);

        BarChart barChart = findViewById(R.id.barChart);

        Intent intent = getIntent();
        roundCompetitorsAnalyticalRatingArrayList = intent.getStringArrayListExtra("roundCompetitorsAnalyticalRatingArrayList");

        ArrayList<BarEntry> ratings = new ArrayList<>();

        if (roundCompetitorsAnalyticalRatingArrayList.isEmpty()) {
            Log.d(TAG, "--------------------------------------------------------------------------------------");
        }
        else{
            Log.d(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        if (!roundCompetitorsAnalyticalRatingArrayList.isEmpty()) {

            for (int i=0; i<10; i++) {

                analyticalRatingCount = Float.valueOf(roundCompetitorsAnalyticalRatingArrayList.get(i));
                ratings.add(new BarEntry(i, analyticalRatingCount));

            }

        }

        BarDataSet barDataSet = new BarDataSet(ratings, "Ratings");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(12f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Rating Bar Chart");
        barChart.getDescription().setTextColor(Color.BLACK);
        barChart.getDescription().setTextSize(12f);
        barChart.setDrawBarShadow(false);
        barChart.animateY(2000);

    }
}
