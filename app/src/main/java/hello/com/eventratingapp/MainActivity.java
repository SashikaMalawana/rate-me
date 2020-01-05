package hello.com.eventratingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mSearchForEventsButton;
    private Button mEventDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchForEventsButton = (Button) findViewById(R.id.searchForEventsButton);
        mSearchForEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchEventActivity.class);
                startActivity(intent);
            }
        });

        mEventDashboard = (Button) findViewById(R.id.eventDashboardButton);
        mEventDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventDashboardActivity.class);
                startActivity(intent);
            }
        });

    }
}
