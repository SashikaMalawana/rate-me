package hello.com.eventratingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EventRoundCompetitorProfileActivity extends AppCompatActivity {

    private TextView jRoundNameTextView;

    private TextView jCompetitorNameTextView;
    private TextView jDateOfBirthTextView;
    private TextView jHometownTextView;
    private TextView jPerformanceTypeTextView;
    private TextView jDescriptionTextView;

    private RatingBar jAverageRatingBar;
    private TextView jWeightedAverageRatingTextView;
    private TextView jNoOfRatingsTextView;
    private Button jRateCompetitorButton;

    private Button jBarChartButton;
    private Button jPieChartButton;
    private Button jRadarChartButton;

    private ImageView jEventCompetitorProfileImageView;

    String currentEventFromIntent;
    String currentRoundFromIntent;
    String clickedListViewItem;
    String currentFullEventFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_round_competitor_profile);

        jRoundNameTextView = (TextView) findViewById(R.id.xRoundNameTextView);

        jCompetitorNameTextView = (TextView) findViewById(R.id.xCompetitorNameTextView);
        jDateOfBirthTextView = (TextView) findViewById(R.id.xDateOfBirthTextView);
        jHometownTextView = (TextView) findViewById(R.id.xHometownTextView);
        jPerformanceTypeTextView = (TextView) findViewById(R.id.xPerformanceTypeTextView);
        jDescriptionTextView = (TextView) findViewById(R.id.xDescriptionTextView);

        jAverageRatingBar = (RatingBar) findViewById(R.id.xAverageRatingBar);
        jWeightedAverageRatingTextView = (TextView) findViewById(R.id.xWeightedAverageRatingTextView);
        jNoOfRatingsTextView = (TextView) findViewById(R.id.xNoOfRatingsTextView);
        jRateCompetitorButton = (Button) findViewById(R.id.xRateCompetitorButton);

        jBarChartButton = (Button) findViewById(R.id.xBarChartButton);
        jPieChartButton = (Button) findViewById(R.id.xPieChartButton);
        jRadarChartButton = (Button) findViewById(R.id.xRadarChartButton);

        jEventCompetitorProfileImageView = (ImageView) findViewById(R.id.xEventCompetitorProfileImageView);

        Intent intent = getIntent();
        currentEventFromIntent = intent.getStringExtra("currentEvent");
        currentRoundFromIntent = intent.getStringExtra("currentRound");
        clickedListViewItem = intent.getStringExtra("clickedItem");
        currentFullEventFromIntent = intent.getStringExtra("currentEventFull");

        jRoundNameTextView.setText(currentRoundFromIntent);

        DatabaseReference mDatabaseCompetitorName = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(clickedListViewItem).child("Competitor Name");
        mDatabaseCompetitorName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String competitorName = dataSnapshot.getValue().toString();
                jCompetitorNameTextView.setText(competitorName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseDateOfBirth = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(clickedListViewItem).child("Date Of Birth");
        mDatabaseDateOfBirth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dateOfBirth = dataSnapshot.getValue().toString();
                jDateOfBirthTextView.setText(dateOfBirth);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabseHometown = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(clickedListViewItem).child("Hometown");
        mDatabseHometown.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String hometown = dataSnapshot.getValue().toString();
                jHometownTextView.setText(hometown);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabasePerformanceType = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(clickedListViewItem).child("Performance Type");
        mDatabasePerformanceType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String performanceType = dataSnapshot.getValue().toString();
                jPerformanceTypeTextView.setText(performanceType);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseDescription = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(clickedListViewItem).child("Description");
        mDatabaseDescription.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String description = dataSnapshot.getValue().toString();
                jDescriptionTextView.setText(description);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Handle ratings
        DatabaseReference mDatabaseRatingPoint = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(clickedListViewItem).child("Ratings").child("Weighted Average Rating");
        mDatabaseRatingPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String weightedAverageRating = dataSnapshot.getValue().toString();
                jWeightedAverageRatingTextView.setText(weightedAverageRating + "/10");
                jAverageRatingBar.setRating(Float.valueOf(weightedAverageRating));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseNoOfRating = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(clickedListViewItem).child("Ratings").child("No Of Ratings");
        mDatabaseNoOfRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String noOfRatings = dataSnapshot.getValue().toString();
                jNoOfRatingsTextView.setText(noOfRatings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Button click event
        jRateCompetitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventRoundCompetitorProfileActivity.this, EventCompetitorProfileRateActivity.class);
                intent.putExtra("currentEvent", currentEventFromIntent);
                intent.putExtra("currentCompetitor", clickedListViewItem);
                startActivity(intent);
            }
        });

        jBarChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventRoundCompetitorProfileActivity.this, BarChartActivity.class);
                startActivity(intent);
            }
        });

        jPieChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventRoundCompetitorProfileActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });

        jRadarChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventRoundCompetitorProfileActivity.this, RadarChartActivity.class);
                startActivity(intent);
            }
        });

        jAverageRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventRoundCompetitorProfileActivity.this, BarChartActivity.class);
                startActivity(intent);
            }
        });

        DatabaseReference imagePath = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(clickedListViewItem).child("Image Url");
        imagePath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String link = dataSnapshot.getValue().toString();
                Picasso.get().load(link).fit().centerCrop().into(jEventCompetitorProfileImageView);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
