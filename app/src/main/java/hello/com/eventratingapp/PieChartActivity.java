package hello.com.eventratingapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    private static final String TAG = "";
    float analyticalRatingCount = 0;

    ArrayList<String> roundCompetitorsAnalyticalRatingArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart);

        PieChart pieChart = findViewById(R.id.pieChart);

        Intent intent = getIntent();
        roundCompetitorsAnalyticalRatingArrayList = intent.getStringArrayListExtra("roundCompetitorsAnalyticalRatingArrayList");

        ArrayList<PieEntry> ratings = new ArrayList<>();

        if (roundCompetitorsAnalyticalRatingArrayList.isEmpty()) {
            Log.d(TAG, "--------------------------------------------------------------------------------------");
        }
        else{
            Log.d(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        if (!roundCompetitorsAnalyticalRatingArrayList.isEmpty()) {

            for (int i=0; i<10; i++) {

                analyticalRatingCount = Float.valueOf(roundCompetitorsAnalyticalRatingArrayList.get(i));
                ratings.add(new PieEntry(analyticalRatingCount, String.valueOf(i+1)));

            }

        }

        PieDataSet pieDataSet = new PieDataSet(ratings, "Ratings");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(12f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.setHoleRadius(40f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Ratings Pie Chart");
        pieChart.animate();

    }
}
