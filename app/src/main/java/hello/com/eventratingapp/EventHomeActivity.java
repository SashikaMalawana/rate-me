package hello.com.eventratingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventHomeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView eventNameShortTextView;
    private TextView eventNameLongTextView;
    private TextView countryOfOriginTextView;
    private TextView originalLnaguageTextView;
    private TextView genreTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_home);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        eventNameShortTextView = (TextView) findViewById(R.id.eventNameShortTextView);
        eventNameLongTextView = (TextView) findViewById(R.id.eventNameLongTextView);
        countryOfOriginTextView = (TextView) findViewById(R.id.countryOfOriginTextView);
        originalLnaguageTextView = (TextView) findViewById(R.id.originalLanguageTextView);
        genreTextView = (TextView) findViewById(R.id.genreTextView);

        Intent intent = getIntent();
        String clickedListViewItem = intent.getStringExtra("clickedItem");

        eventNameShortTextView.setText(clickedListViewItem);

        DatabaseReference mDatabaseEventName = FirebaseDatabase.getInstance().getReference().child("Event").child(clickedListViewItem).child("Event Name");
        mDatabaseEventName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                eventNameLongTextView.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabaseCountryOfOrigin = FirebaseDatabase.getInstance().getReference().child("Event").child(clickedListViewItem).child("Country Of Origin");
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

        DatabaseReference mDatabaseOriginalLanguage = FirebaseDatabase.getInstance().getReference().child("Event").child(clickedListViewItem).child("Original Language");
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

        DatabaseReference mDatabaseGenre = FirebaseDatabase.getInstance().getReference().child("Event").child(clickedListViewItem).child("Genre");
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

    }
}
