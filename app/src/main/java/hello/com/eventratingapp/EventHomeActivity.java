package hello.com.eventratingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class EventHomeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView eventNameShortTextView;
    private TextView eventNameLongTextView;
    private TextView countryOfOriginTextView;
    private TextView originalLnaguageTextView;
    private TextView genreTextView;
    private Button subscribeEventButton;
    private Button unsubscribeEventButton;
    private String eventForSubscribe;
    private Button competitorListButton;
    private Button createCompetitorButton;

    private Button roundButton;

    private ImageView eventHomeImageView;

    String linkToIntent;

    private ArrayList<String> eventCompetitorArrayList = new ArrayList<String>();
    private ArrayList<String> subscribedEventArrayList = new ArrayList<String>();

    String currentUserFromIntent = "Jason";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_home);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        eventNameShortTextView = (TextView) findViewById(R.id.eventNameShortTextView);
        eventNameLongTextView = (TextView) findViewById(R.id.eventNameLongTextView);
        countryOfOriginTextView = (TextView) findViewById(R.id.countryOfOriginTextView);
        originalLnaguageTextView = (TextView) findViewById(R.id.originalLanguageTextView);
        genreTextView = (TextView) findViewById(R.id.genreTextView);
        subscribeEventButton = (Button) findViewById(R.id.subscribeEventButton);
        unsubscribeEventButton = (Button) findViewById(R.id.unsubscribeEventButton);
        competitorListButton = (Button) findViewById(R.id.competitorListButton);
        createCompetitorButton = (Button) findViewById(R.id.createCompetitorBtn);

        roundButton = (Button) findViewById(R.id.roundBtn);

        eventHomeImageView = (ImageView) findViewById(R.id.eventHomeImageView);

        Intent intent = getIntent();
        final String clickedListViewItem = intent.getStringExtra("clickedItem");

        eventNameShortTextView.setText(clickedListViewItem);

        DatabaseReference mDatabaseEventName = FirebaseDatabase.getInstance().getReference().child("Events").child(clickedListViewItem).child("Event Name");
        mDatabaseEventName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                eventNameLongTextView.setText(name);
                eventForSubscribe = name;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseCountryOfOrigin = FirebaseDatabase.getInstance().getReference().child("Events").child(clickedListViewItem).child("Country Of Origin");
        mDatabaseCountryOfOrigin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String country = dataSnapshot.getValue().toString();
                countryOfOriginTextView.setText(country);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseOriginalLanguage = FirebaseDatabase.getInstance().getReference().child("Events").child(clickedListViewItem).child("Original Language");
        mDatabaseOriginalLanguage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String language = dataSnapshot.getValue().toString();
                originalLnaguageTextView.setText(language);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseGenre = FirebaseDatabase.getInstance().getReference().child("Events").child(clickedListViewItem).child("Genre");
        mDatabaseGenre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String genre = dataSnapshot.getValue().toString();
                genreTextView.setText(genre);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(clickedListViewItem).child("Event Competitors");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String competitorName = dataSnapshot.getKey();
                eventCompetitorArrayList.add(competitorName);
                //arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

        DatabaseReference mDatabaseSubscribed = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserFromIntent).child("Subscribed Events");
        mDatabaseSubscribed.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String eventName = dataSnapshot.getKey();
                subscribedEventArrayList.add(eventName);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

        subscribeEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save subscribed events under logged user
                DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserFromIntent).child("Subscribed Events").child(clickedListViewItem);
                userReference.child("IsSubscribed").setValue("true");

                Toast.makeText(EventHomeActivity.this, "You subscribed " +eventForSubscribe, Toast.LENGTH_SHORT).show();

                if (!subscribedEventArrayList.contains(clickedListViewItem)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(EventHomeActivity.this);
                    builder.setTitle("Subscribed!");
                    builder.setMessage("You have successfully subscribed " + clickedListViewItem + " event");
                    builder.setCancelable(false);
                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();

                }
                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(EventHomeActivity.this);
                    builder.setTitle("Cannot Subscribed!");
                    builder.setMessage("You have already subscribed " +clickedListViewItem + " event");
                    builder.setCancelable(false);
                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();

                }

            }
        });

        unsubscribeEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Remove subscribed events under logged user
                DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserFromIntent).child("Subscribed Events").child(clickedListViewItem);
                userReference.removeValue();

                Toast.makeText(EventHomeActivity.this, "You unsubscribed " +eventForSubscribe, Toast.LENGTH_SHORT).show();

                if (subscribedEventArrayList.contains(clickedListViewItem)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(EventHomeActivity.this);
                    builder.setTitle("Unsubscribed!");
                    builder.setMessage("You have successfully unsubscribed " + clickedListViewItem + " event");
                    builder.setCancelable(false);
                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();

                }
                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(EventHomeActivity.this);
                    builder.setTitle("Cannot Unsubscribed!");
                    builder.setMessage("You have already unsubscribed " +clickedListViewItem + " event");
                    builder.setCancelable(false);
                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();

                }
            }
        });

        createCompetitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventHomeActivity.this, CreateCompetitorActivity.class);
                intent.putExtra("eventName", clickedListViewItem);
                intent.putExtra("eventFullName", eventForSubscribe);
                startActivity(intent);
            }
        });

        competitorListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventHomeActivity.this, EventCompetitorListActivity.class);
                intent.putExtra("eventName", clickedListViewItem);
                intent.putStringArrayListExtra("competitorList", eventCompetitorArrayList);
                startActivity(intent);
            }
        });

        roundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventHomeActivity.this, EventRoundDashboardActivity.class);
                intent.putExtra("eventName", clickedListViewItem);
                intent.putExtra("link", linkToIntent);
                startActivity(intent);
            }
        });

        DatabaseReference imagePath = FirebaseDatabase.getInstance().getReference().child("Events").child(clickedListViewItem).child("Image Url");
        imagePath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String link = dataSnapshot.getValue().toString();
                linkToIntent = dataSnapshot.getValue().toString();
                Picasso.get().load(link).into(eventHomeImageView);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}