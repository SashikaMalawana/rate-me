package hello.com.eventratingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mCreateEventButton;
    private Button mCreateCompetitorButton;
    private Button mSearchForEventsButton;
    private Button mEventListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCreateEventButton = (Button) findViewById(R.id.createEventButton);
        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });

        mCreateCompetitorButton = (Button) findViewById(R.id.createCompetitorButton);
        mCreateCompetitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateCompetitorActivity.class);
                startActivity(intent);
            }
        });

        mSearchForEventsButton = (Button) findViewById(R.id.searchForEventsButton);
        mSearchForEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchEventActivity.class);
                startActivity(intent);
            }
        });

        mEventListButton = (Button) findViewById(R.id.eventListButton);
        mEventListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventListActivity.class);
                startActivity(intent);
            }
        });
    }
}
