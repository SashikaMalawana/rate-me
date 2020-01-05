package hello.com.eventratingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EventDashboardActivity extends AppCompatActivity {

    private Button createEventButton;
    private Button subscribeEventButton;
    private Button eventListButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_dashboard);

        createEventButton = (Button) findViewById(R.id.createEventBtn);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDashboardActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });

        subscribeEventButton = (Button) findViewById(R.id.subscribeEventBtn);
        subscribeEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(EventDashboardActivity.this, SubscribeEvent.class);
//                startActivity(intent);
            }
        });

        eventListButton = (Button) findViewById(R.id.eventListBtn);
        eventListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDashboardActivity.this, EventListActivity.class);
                startActivity(intent);
            }
        });

    }
}
