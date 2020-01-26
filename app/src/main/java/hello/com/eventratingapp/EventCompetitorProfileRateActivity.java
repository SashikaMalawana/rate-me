package hello.com.eventratingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class EventCompetitorProfileRateActivity extends AppCompatActivity {

    private TextView eventCompetitorNameRateTextView;

    private RatingBar averageInnerRatingBar;
    private TextView weightedAverageRatingInnerTextView;
    private TextView noOfRatingsInnerTextView;

    private RatingBar userInnerRatingBar;
    private TextView ratingScaleInnerTextView;
    private EditText reviewEditText;
    private Button submitRatingReviewButton;

    private ImageView eventCompetitorProfileRateImageView;

    String currentEventFromIntent;
    String currentCompetitorFromIntent;

    String totalRatingPointsForCalc = null;
    String noOfRatingsForCalc = null;
    String weightedAverageRatingForCalc = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_competitor_profile_rate);

        eventCompetitorNameRateTextView = (TextView) findViewById(R.id.competitorNameRateTextView);

        averageInnerRatingBar = (RatingBar) findViewById(R.id.averageInnerRatingBar);
        weightedAverageRatingInnerTextView = (TextView) findViewById(R.id.weightedAverageRatingInnerTextView);
        noOfRatingsInnerTextView = (TextView) findViewById(R.id.noOfRatingsInnerTextView);

        userInnerRatingBar = (RatingBar) findViewById(R.id.userInnerRatingBar);
        ratingScaleInnerTextView = (TextView) findViewById(R.id.ratingScaleInnerTextView);

        reviewEditText = (EditText) findViewById(R.id.reviewEditText);
        submitRatingReviewButton = (Button) findViewById(R.id.submitRatingReviewButton);

        eventCompetitorProfileRateImageView = (ImageView) findViewById(R.id.eventCompetitorProfileRateImageView);

        Intent intent = getIntent();
        currentEventFromIntent = intent.getStringExtra("currentEvent");
        currentCompetitorFromIntent = intent.getStringExtra("currentCompetitor");

        DatabaseReference mDatabaseCompetitorName = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(currentCompetitorFromIntent).child("Competitor Name");
        mDatabaseCompetitorName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String competitorName = dataSnapshot.getValue().toString();
                eventCompetitorNameRateTextView.setText(competitorName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Handle ratings
        DatabaseReference mDatabaseAverageRating = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(currentCompetitorFromIntent).child("Ratings").child("Weighted Average Rating");
        mDatabaseAverageRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String weightedAverageRating = dataSnapshot.getValue().toString();
                weightedAverageRatingInnerTextView.setText(weightedAverageRating + "/10");
                averageInnerRatingBar.setRating(Float.valueOf(weightedAverageRating));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseNoOfRating = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(currentCompetitorFromIntent).child("Ratings").child("No Of Ratings");
        mDatabaseNoOfRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String noOfRatings = dataSnapshot.getValue().toString();
                noOfRatingsInnerTextView.setText(noOfRatings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userInnerRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingScaleInnerTextView.setText(String.valueOf(rating));
                float rate = ratingBar.getRating()*2;
                switch ((int) rate) {
                    case 1:
                        ratingScaleInnerTextView.setText("Worst! Never see this again!");
                        break;
                    case 2:
                        ratingScaleInnerTextView.setText("Very bad! I'm not interested in!");
                        break;
                    case 3:
                        ratingScaleInnerTextView.setText("Need some improvement!");
                        break;
                    case 4:
                        ratingScaleInnerTextView.setText("Good! Fair enough!");
                        break;
                    case 5:
                        ratingScaleInnerTextView.setText("Superb! I like that!");
                        break;
                    case 6:
                        ratingScaleInnerTextView.setText("Perfect! I enjoy that!");
                        break;
                    case 7:
                        ratingScaleInnerTextView.setText("Brilliant! I love that!");
                        break;
                    case 8:
                        ratingScaleInnerTextView.setText("Remarkable! I'm really into!");
                        break;
                    case 9:
                        ratingScaleInnerTextView.setText("Outstanding! I'm interested in!");
                        break;
                    case 10:
                        ratingScaleInnerTextView.setText("Majestic! I'm big fan of it!");
                        break;
                    default:
                        ratingScaleInnerTextView.setText("");
                }
            }
        });

        submitRatingReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float userRatingFloat = (userInnerRatingBar.getRating())*2;
                String userRatingString = String.valueOf(userRatingFloat);
                Toast.makeText(EventCompetitorProfileRateActivity.this, "Your rating value is " +userRatingString, Toast.LENGTH_SHORT).show();

                DatabaseReference mDatabaseTotalRatingPoints = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(currentCompetitorFromIntent).child("Ratings").child("Total Rating Points");
                mDatabaseTotalRatingPoints.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        totalRatingPointsForCalc = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                DatabaseReference mDatabaseNoOfRating = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(currentCompetitorFromIntent).child("Ratings").child("No Of Ratings");
                mDatabaseNoOfRating.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        noOfRatingsForCalc = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                DatabaseReference mDatabaseWeightedAverageRating = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(currentCompetitorFromIntent).child("Ratings").child("Weighted Average Rating");
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

                String review = reviewEditText.getText().toString().trim();
                DatabaseReference mDatabaseReview = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(currentCompetitorFromIntent).child("Ratings");
                mDatabaseReview.child("Reviews").setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EventCompetitorProfileRateActivity.this, "Review is stored", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(EventCompetitorProfileRateActivity.this, "Review is not stored", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                userInnerRatingBar.setRating(0);
                reviewEditText.setText("");
            }
        });

        DatabaseReference imagePath = FirebaseDatabase.getInstance().getReference().child("Events").child(currentEventFromIntent).child("Event Competitors").child(currentCompetitorFromIntent).child("Image Url");
        imagePath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String link = dataSnapshot.getValue().toString();
                Picasso.get().load(link).fit().centerCrop().into(eventCompetitorProfileRateImageView);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
