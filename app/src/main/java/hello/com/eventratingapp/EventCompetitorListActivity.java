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
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class EventCompetitorListActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ListView eventCompetitorListView;
    private ArrayList<String> eventCompetitorArrayList = new ArrayList<String>();

    private ArrayAdapter<String> arrayAdapter;

    private String currentEventFromIntent = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_competitor_list);

        Intent intent = getIntent();
        currentEventFromIntent = intent.getStringExtra("eventName");
        eventCompetitorArrayList = intent.getStringArrayListExtra("competitorList");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors");
        eventCompetitorListView = (ListView) findViewById(R.id.eventCompetitorListView);

        ArrayList<RoundCompetitor> roundCompetitorsList = new ArrayList<>();

        final String[] detailsArray = new String[3];
        for (String eachCompetitor : eventCompetitorArrayList) {

            DatabaseReference dataRef1 = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(eachCompetitor).child("Competitor Name");
            dataRef1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    detailsArray[0] = dataSnapshot.getValue().toString();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            DatabaseReference dataRef2 = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(eachCompetitor).child("Hometown");
            dataRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    detailsArray[1] = dataSnapshot.getValue().toString();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            DatabaseReference dataRef3 = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(eachCompetitor).child("Ratings").child("Weighted Average Rating");
            dataRef3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    detailsArray[2] = dataSnapshot.getValue().toString();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //instead of using above three dataRefs
            DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(eachCompetitor);
            dataRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                        String s1 = dataSnap.child("Competitor Name").getValue().toString();
                        String s2 = dataSnap.child("Hometown").getValue().toString();
                        String s3 = dataSnap.child("Ratings").child("Weighted Average Rating").getValue().toString();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            RoundCompetitor cmptr = new RoundCompetitor(detailsArray[0], detailsArray[1], detailsArray[2]);
            roundCompetitorsList.add(cmptr);

        }

        RoundCompetitorArrayAdapter adapter = new RoundCompetitorArrayAdapter(this, R.layout.adapter_view_layout, roundCompetitorsList);
        eventCompetitorListView.setAdapter(adapter);

//        mDatabase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String competitorName = dataSnapshot.getKey();
//                eventCompetitorArrayList.add(competitorName);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        arrayAdapter = new ArrayAdapter<String>(EventCompetitorListActivity.this, android.R.layout.simple_list_item_1, eventCompetitorArrayList);
//        eventCompetitorListView.setAdapter(arrayAdapter);

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