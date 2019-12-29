package hello.com.eventratingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventCompetitorProfileActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView eventCompetitorHeadName;
    private TextView eventCompetitorNameTextView;
    private TextView dateOfBirthTextView;
    private TextView hometownTextView;
    private TextView performanceTypeTextView;
    private TextView descriptionTextView;

    private RatingBar averageRatingBar;
    private TextView weightedAverageRatingTextView;
    private TextView noOfRatingsTextView;
    private Button rateCompetitorButton;

    String currentEventFromIntent;
    String clickedListViewItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_competitor_profile);

        eventCompetitorHeadName = (TextView) findViewById(R.id.competitorNameHeadTextView);
        eventCompetitorNameTextView = (TextView) findViewById(R.id.competitorNameTextView);
        dateOfBirthTextView = (TextView) findViewById(R.id.dateOfBirthTextView);
        hometownTextView = (TextView) findViewById(R.id.hometownTextView);
        performanceTypeTextView = (TextView) findViewById(R.id.performanceTypeTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);

        averageRatingBar = (RatingBar) findViewById(R.id.averageRatingBar);
        weightedAverageRatingTextView = (TextView) findViewById(R.id.weightedAverageRatingTextView);
        noOfRatingsTextView = (TextView) findViewById(R.id.noOfRatingsTextView);
        rateCompetitorButton = (Button) findViewById(R.id.rateCompetitorButton);

        Intent intent = getIntent();
        currentEventFromIntent = intent.getStringExtra("currentEvent");
        clickedListViewItem = intent.getStringExtra("clickedItem");

        eventCompetitorHeadName.setText(clickedListViewItem);

        DatabaseReference mDatabaseCompetitorName = FirebaseDatabase.getInstance().getReference().child("Event").child(currentEventFromIntent).child("Event Competitors").child(clickedListViewItem).child("Competitor Name");
        mDatabaseCompetitorName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String competitorName = dataSnapshot.getValue().toString();
                eventCompetitorNameTextView.setText(competitorName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseDateOfBirth = FirebaseDatabase.getInstance().getReference().child("Event").child(currentEventFromIntent).child("Event Competitors").child(clickedListViewItem).child("Date Of Birth");
        mDatabaseDateOfBirth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dateOfBirth = dataSnapshot.getValue().toString();
                dateOfBirthTextView.setText(dateOfBirth);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabseHometown = FirebaseDatabase.getInstance().getReference().child("Event").child(currentEventFromIntent).child("Event Competitors").child(clickedListViewItem).child("Hometown");
        mDatabseHometown.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String hometown = dataSnapshot.getValue().toString();
                hometownTextView.setText(hometown);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabasePerformanceType = FirebaseDatabase.getInstance().getReference().child("Event").child(currentEventFromIntent).child("Event Competitors").child(clickedListViewItem).child("Performance Type");
        mDatabasePerformanceType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String performanceType = dataSnapshot.getValue().toString();
                performanceTypeTextView.setText(performanceType);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseDescription = FirebaseDatabase.getInstance().getReference().child("Event").child(currentEventFromIntent).child("Event Competitors").child(clickedListViewItem).child("Description");
        mDatabaseDescription.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String description = dataSnapshot.getValue().toString();
                descriptionTextView.setText(description);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Handle ratings
        DatabaseReference mDatabaseRatingPoint = FirebaseDatabase.getInstance().getReference().child("Event").child(currentEventFromIntent).child("Event Competitors").child(clickedListViewItem).child("Rating").child("Weighted Average Rating");
        mDatabaseRatingPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String weightedAverageRating = dataSnapshot.getValue().toString();
                weightedAverageRatingTextView.setText(weightedAverageRating + "/10");
                averageRatingBar.setRating(Float.valueOf(weightedAverageRating));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseNoOfRating = FirebaseDatabase.getInstance().getReference().child("Event").child(currentEventFromIntent).child("Event Competitors").child(clickedListViewItem).child("Rating").child("No Of Ratings");
        mDatabaseNoOfRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String noOfRatings = dataSnapshot.getValue().toString();
                noOfRatingsTextView.setText(noOfRatings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rateCompetitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventCompetitorProfileActivity.this, EventCompetitorProfileRateActivity.class);
                intent.putExtra("currentEvent", currentEventFromIntent);
                intent.putExtra("currentCompetitor", clickedListViewItem);
                startActivity(intent);
            }
        });

    }
}
