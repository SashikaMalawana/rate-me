package hello.com.eventratingapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;

public class RadarChartActivity extends AppCompatActivity {

    private static final String TAG = "";
    float analyticalRatingCount = 0;

    ArrayList<String> roundCompetitorsAnalyticalRatingArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radar_chart);

        RadarChart radarChart = findViewById(R.id.radarChart);

        Intent intent = getIntent();
        roundCompetitorsAnalyticalRatingArrayList = intent.getStringArrayListExtra("roundCompetitorsAnalyticalRatingArrayList");

        ArrayList<RadarEntry> ratings = new ArrayList<>();

        if (roundCompetitorsAnalyticalRatingArrayList.isEmpty()) {
            Log.d(TAG, "--------------------------------------------------------------------------------------");
        }
        else{
            Log.d(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        if (!roundCompetitorsAnalyticalRatingArrayList.isEmpty()) {

            for (int i=0; i<10; i++) {

                analyticalRatingCount = Float.valueOf(roundCompetitorsAnalyticalRatingArrayList.get(i));
                ratings.add(new RadarEntry(analyticalRatingCount));

            }

        }

        RadarDataSet radarDataSet = new RadarDataSet(ratings, "Ratings");
        radarDataSet.setColor(Color.RED);
        radarDataSet.setLineWidth(2f);
        radarDataSet.setValueTextColor(Color.RED);
        radarDataSet.setValueTextSize(14f);

        RadarData radarData = new RadarData();
        radarData.addDataSet(radarDataSet);

        String[] labels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        radarChart.getDescription().setText("Radar Chart Example");
        radarChart.setData(radarData);

    }
}
