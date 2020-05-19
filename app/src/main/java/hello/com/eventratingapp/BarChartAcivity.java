package hello.com.eventratingapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChartAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_chart);

        BarChart barChart = findViewById(R.id.barChart);

        ArrayList<BarEntry> ratings = new ArrayList<>();
        ratings.add(new BarEntry(1, 53));
        ratings.add(new BarEntry(2, 21));
        ratings.add(new BarEntry(3, 34));
        ratings.add(new BarEntry(4, 53));
        ratings.add(new BarEntry(5, 67));
        ratings.add(new BarEntry(6, 43));
        ratings.add(new BarEntry(7, 34));
        ratings.add(new BarEntry(8, 26));
        ratings.add(new BarEntry(9, 17));
        ratings.add(new BarEntry(10, 37));

        BarDataSet barDataSet = new BarDataSet(ratings, "Ratings");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Example");
        barChart.animateY(2000);

    }
}
