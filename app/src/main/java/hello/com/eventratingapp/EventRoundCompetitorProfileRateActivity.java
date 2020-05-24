package hello.com.eventratingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EventRoundCompetitorProfileRateActivity extends AppCompatActivity {

    private TextView jCompetitorNameRateTextView;

    private RatingBar jAverageInnerRatingBar;
    private TextView jWeightedAverageRatingInnerTextView;
    private TextView jNoOfRatingsInnerTextView;

    private RatingBar jUserInnerRatingBar;
    private TextView jRatingScaleInnerTextView;
    private EditText jReviewEditText;
    private Button jSubmitReviewButton;

    private ImageView jCompetitorProfileRateImageView;

    private FloatingActionButton jSubmitRatingFloatingActionButton;

    private TextView jReviewTextView;

    String currentEventFromIntent;
    String currentRoundFromIntent;
    String currentCompetitorFromIntent;

    String totalRatingPointsForCalc = null;
    String noOfRatingsForCalc = null;
    String weightedAverageRatingForCalc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_round_competitor_profile_rate);

        jCompetitorNameRateTextView = (TextView) findViewById(R.id.xCompetitorNameRateTextView);

        jAverageInnerRatingBar = (RatingBar) findViewById(R.id.xAverageInnerRatingBar);
        jWeightedAverageRatingInnerTextView = (TextView) findViewById(R.id.xWeightedAverageRatingInnerTextView);
        jNoOfRatingsInnerTextView = (TextView) findViewById(R.id.xNoOfRatingsInnerTextView);

        jUserInnerRatingBar = (RatingBar) findViewById(R.id.xUserInnerRatingBar);
        jRatingScaleInnerTextView = (TextView) findViewById(R.id.xRatingScaleInnerTextView);

        jReviewEditText = (EditText) findViewById(R.id.xReviewEditText);
        jSubmitReviewButton = (Button) findViewById(R.id.xSubmitReviewButton);

        jCompetitorProfileRateImageView = (ImageView) findViewById(R.id.xCompetitorProfileRateImageView);

        jSubmitRatingFloatingActionButton = (FloatingActionButton) findViewById(R.id.xSubmitRatingFloatingActionButton);

        jReviewTextView = (TextView) findViewById(R.id.xReviewTextView);

        Intent intent = getIntent();
        currentEventFromIntent = intent.getStringExtra("currentEvent");
        currentRoundFromIntent = intent.getStringExtra("currentRound");
        currentCompetitorFromIntent = intent.getStringExtra("currentCompetitor");

        DatabaseReference mDatabaseCompetitorName = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(currentCompetitorFromIntent).child("Competitor Name");
        mDatabaseCompetitorName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String competitorName = dataSnapshot.getValue().toString();
                jCompetitorNameRateTextView.setText(competitorName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Handle ratings
        DatabaseReference mDatabaseAverageRating = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(currentCompetitorFromIntent).child("Ratings").child("Weighted Average Rating");
        mDatabaseAverageRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String weightedAverageRating = dataSnapshot.getValue().toString();
                jWeightedAverageRatingInnerTextView.setText(weightedAverageRating + "/10");
                jAverageInnerRatingBar.setRating(Float.valueOf(weightedAverageRating));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseNoOfRating = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(currentCompetitorFromIntent).child("Ratings").child("No Of Ratings");
        mDatabaseNoOfRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String noOfRatings = dataSnapshot.getValue().toString();
                jNoOfRatingsInnerTextView.setText(noOfRatings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        jUserInnerRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                jRatingScaleInnerTextView.setText(String.valueOf(rating));
                float rate = ratingBar.getRating()*2;
                switch ((int) rate) {
                    case 1:
                        jRatingScaleInnerTextView.setText("Worst! Never see this again!");
                        break;
                    case 2:
                        jRatingScaleInnerTextView.setText("Very bad! I'm not interested in!");
                        break;
                    case 3:
                        jRatingScaleInnerTextView.setText("Need some improvement!");
                        break;
                    case 4:
                        jRatingScaleInnerTextView.setText("Good! Fair enough!");
                        break;
                    case 5:
                        jRatingScaleInnerTextView.setText("Superb! I like that!");
                        break;
                    case 6:
                        jRatingScaleInnerTextView.setText("Perfect! I enjoy that!");
                        break;
                    case 7:
                        jRatingScaleInnerTextView.setText("Brilliant! I love that!");
                        break;
                    case 8:
                        jRatingScaleInnerTextView.setText("Remarkable! I'm really into!");
                        break;
                    case 9:
                        jRatingScaleInnerTextView.setText("Outstanding! I'm interested in!");
                        break;
                    case 10:
                        jRatingScaleInnerTextView.setText("Majestic! I'm big fan of it!");
                        break;
                    default:
                        jRatingScaleInnerTextView.setText("");
                }
            }
        });

        jSubmitRatingFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float userRatingFloat = (jUserInnerRatingBar.getRating())*2;
                String userRatingString = String.valueOf(userRatingFloat);
                Toast.makeText(EventRoundCompetitorProfileRateActivity.this, "Your rating value is " +userRatingString, Toast.LENGTH_SHORT).show();

                DatabaseReference mDatabaseTotalRatingPoints = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(currentCompetitorFromIntent).child("Ratings").child("Total Rating Points");
                mDatabaseTotalRatingPoints.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        totalRatingPointsForCalc = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                DatabaseReference mDatabaseNoOfRating = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(currentCompetitorFromIntent).child("Ratings").child("No Of Ratings");
                mDatabaseNoOfRating.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        noOfRatingsForCalc = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                DatabaseReference mDatabaseWeightedAverageRating = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(currentCompetitorFromIntent).child("Ratings").child("Weighted Average Rating");
                mDatabaseWeightedAverageRating.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        weightedAverageRatingForCalc = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                try {
                    float totalRatingPointsForCalcFloat = Float.valueOf(totalRatingPointsForCalc);
                    float noOfRatingsForCalcFloat = Float.valueOf(noOfRatingsForCalc);
                    float weightedAverageRatingForCalcFloat = Float.valueOf(weightedAverageRatingForCalc);

                    totalRatingPointsForCalcFloat = totalRatingPointsForCalcFloat + userRatingFloat;
                    noOfRatingsForCalcFloat = noOfRatingsForCalcFloat + 1;
                    weightedAverageRatingForCalcFloat = totalRatingPointsForCalcFloat / noOfRatingsForCalcFloat;

                    int totalRatingPointsForCalcInt = Integer.valueOf((int) totalRatingPointsForCalcFloat);
                    int noOfRatingsForCalcInt = Integer.valueOf((int) noOfRatingsForCalcFloat);

                    String totalRatingPointsForCalcString = String.valueOf(totalRatingPointsForCalcInt);
                    String noOfRatingsForCalcString = String.valueOf(noOfRatingsForCalcInt);

                    String weightedAverageRatingForCalcRound = String.format("%.2f", weightedAverageRatingForCalcFloat);

                    mDatabaseTotalRatingPoints.setValue(totalRatingPointsForCalcString);
                    mDatabaseNoOfRating.setValue(noOfRatingsForCalcString);
                    mDatabaseWeightedAverageRating.setValue(weightedAverageRatingForCalcRound);
                }
                catch (Exception exception) {
                    System.out.println(exception.toString());
                }
                jUserInnerRatingBar.setRating(0);

            }
        });

        jSubmitReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String review = jReviewEditText.getText().toString().trim();
                DatabaseReference mDatabaseReview = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(currentCompetitorFromIntent).child("Ratings");
                mDatabaseReview.child("Reviews").setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EventRoundCompetitorProfileRateActivity.this, "Review is stored", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(EventRoundCompetitorProfileRateActivity.this, "Review is not stored", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                jReviewEditText.setText("");

            }
        });

        DatabaseReference mDatabaseGetReview = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(currentCompetitorFromIntent).child("Ratings").child("Reviews");
        mDatabaseGetReview.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String getReview = dataSnapshot.getValue().toString().trim();
                jReviewTextView.setText(getReview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference imagePath = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Rounds").child(currentRoundFromIntent).child("Round Competitors").child(currentCompetitorFromIntent).child("Image Url");
        imagePath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String link = dataSnapshot.getValue().toString();
                Picasso.get().load(link).fit().centerCrop().into(jCompetitorProfileRateImageView);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        jReviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jReviewEditText.setText(jReviewTextView.getText().toString());
            }
        });

    }
}
