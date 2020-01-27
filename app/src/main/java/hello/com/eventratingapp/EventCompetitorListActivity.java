package hello.com.eventratingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class EventCompetitorListActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ListView eventCompetitorListView;
    private ArrayList<String> eventCompetitorArrayList = new ArrayList<String>();

    private ArrayAdapter<String> arrayAdapter;

    private String currentEventFromIntent = null;

    private ArrayList<String> eventCompetitorKeyArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_competitor_list);

        Intent intent = getIntent();
        currentEventFromIntent = intent.getStringExtra("eventName");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors");
        eventCompetitorListView = (ListView) findViewById(R.id.eventCompetitorListView);

        arrayAdapter = new ArrayAdapter<String>(EventCompetitorListActivity.this, android.R.layout.simple_list_item_1, eventCompetitorArrayList);
        eventCompetitorListView.setAdapter(arrayAdapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String competitorName = dataSnapshot.getKey();
                eventCompetitorArrayList.add(competitorName);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                String competitorName = dataSnapshot.getKey();
//                int index = eventCompetitorKeyArrayList.indexOf(competitorName);
//                eventCompetitorArrayList.set(index,competitorName);
//                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        eventCompetitorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventCompetitorListActivity.this, EventCompetitorProfileActivity.class);
                intent.putExtra("currentEvent", currentEventFromIntent);
                intent.putExtra("clickedItem", eventCompetitorArrayList.get(position));
                startActivity(intent);
            }
        });

    }
}
