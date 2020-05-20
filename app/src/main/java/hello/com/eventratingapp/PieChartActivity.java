package hello.com.eventratingapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart);

        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> ratings = new ArrayList<>();
        ratings.add(new PieEntry(53, "1"));
        ratings.add(new PieEntry(21, "2"));
        ratings.add(new PieEntry(34, "3"));
        ratings.add(new PieEntry(53, "4"));
        ratings.add(new PieEntry(67, "5"));
        ratings.add(new PieEntry(43, "6"));
        ratings.add(new PieEntry(34, "7"));
        ratings.add(new PieEntry(26, "8"));
        ratings.add(new PieEntry(17, "9"));
        ratings.add(new PieEntry(10, "10"));

        PieDataSet pieDataSet = new PieDataSet(ratings, "Ratings");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Ratings");
        pieChart.animate();


    }
}
