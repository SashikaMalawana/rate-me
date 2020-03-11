package hello.com.eventratingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventRoundHomeActivity extends AppCompatActivity {

    private TextView jRoundNameHeadTextView;
    private TextView jRoundNameTextView;
    private TextView jNoOfCompetitorsTextView;
    private TextView jRoundGuestTextView;
    private TextView jActiveTimePeriodTextView;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> roundArrayList = new ArrayList<String>();

    private ListView jRoundCompetitorsListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_round_home);

        jRoundNameHeadTextView = (TextView) findViewById(R.id.xRoundNameHeadTextView);
        jRoundNameTextView = (TextView) findViewById(R.id.xRoundNameTextView);
        jNoOfCompetitorsTextView = (TextView) findViewById(R.id.xNoOfCompetitorsTextView);
        jRoundGuestTextView = (TextView) findViewById(R.id.xRoundGuestTextView);
        jActiveTimePeriodTextView = (TextView) findViewById(R.id.xActiveTimePeriodTextView);

        jRoundCompetitorsListView = (ListView) findViewById(R.id.xRoundCompetitorsListView);

        Intent intent = getIntent();
        String eventNameFromIntent = intent.getStringExtra("eventNameFromIntent");
        String roundNameFromIntent = intent.getStringExtra("roundName");

        arrayAdapter = new ArrayAdapter<String>(EventRoundHomeActivity.this, android.R.layout.simple_list_item_1, roundArrayList);
        jRoundCompetitorsListView.setAdapter(arrayAdapter);

        DatabaseReference mDatabaseRoundName = FirebaseDatabase.getInstance().getReference().child("Events").child(eventNameFromIntent).child("Rounds").child(roundNameFromIntent).child("Round Name");
        mDatabaseRoundName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String roundName = dataSnapshot.getValue().toString();
                jRoundNameHeadTextView.setText(roundName);
                jRoundNameTextView.setText(roundName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseNoOfCompetitors = FirebaseDatabase.getInstance().getReference().child("Events").child(eventNameFromIntent).child("Rounds").child(roundNameFromIntent).child("No Of Competitors");
        mDatabaseNoOfCompetitors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String noOfCompetitors = dataSnapshot.getValue().toString();
                jNoOfCompetitorsTextView.setText(noOfCompetitors);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseRoundGuest = FirebaseDatabase.getInstance().getReference().child("Events").child(eventNameFromIntent).child("Rounds").child(roundNameFromIntent).child("Round Guest");
        mDatabaseRoundGuest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String roundGuest = dataSnapshot.getValue().toString();
                jRoundGuestTextView.setText(roundGuest);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseActiveTimePeriod = FirebaseDatabase.getInstance().getReference().child("Events").child(eventNameFromIntent).child("Rounds").child(roundNameFromIntent).child("Active Time Period");
        mDatabaseActiveTimePeriod.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String activeTimePeriod = dataSnapshot.getValue().toString();
                jActiveTimePeriodTextView.setText(activeTimePeriod);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
