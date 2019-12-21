package hello.com.eventratingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchEventActivity extends AppCompatActivity {

    private Button searchEventButton;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseMod;
    private EditText eventNameEditText;
    private TextView eventNameShortTextView;
    private TextView eventNameTextView;
    private TextView countryOfOriginTextView;
    private TextView originalLanguageTextView;
    private TextView genreTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_event);

        searchEventButton = (Button) findViewById(R.id.searchEventButton);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        eventNameEditText = (EditText) findViewById(R.id.eventNameField);

        eventNameShortTextView = (TextView) findViewById(R.id.eventNameShortTextView);
        eventNameTextView = (TextView) findViewById(R.id.eventNameTextView);
        countryOfOriginTextView = (TextView) findViewById(R.id.countryOfOriginTextView);
        originalLanguageTextView = (TextView) findViewById(R.id.originalLanguageTextView);
        genreTextView = (TextView) findViewById(R.id.genreTextView);

        searchEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = eventNameEditText.getText().toString().trim();
                mDatabaseMod = mDatabase.child(eventName);
                eventNameShortTextView.setText(eventName);

                DatabaseReference mDatabaseEventName = FirebaseDatabase.getInstance().getReference().child("Event").child(eventName).child("Event Name");
                mDatabaseEventName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.getValue().toString();
                        eventNameTextView.setText(name);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                DatabaseReference mDatabaseCountry = FirebaseDatabase.getInstance().getReference().child("Event").child(eventName).child("Country Of Origin");
                mDatabaseCountry.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String country = dataSnapshot.getValue().toString();
                        countryOfOriginTextView.setText(country);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                DatabaseReference mDatabaseLanguage = FirebaseDatabase.getInstance().getReference().child("Event").child(eventName).child("Original Language");
                mDatabaseLanguage.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String language = dataSnapshot.getValue().toString();
                        originalLanguageTextView.setText(language);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                DatabaseReference mDatabaseGenre = FirebaseDatabase.getInstance().getReference().child("Event").child(eventName).child("Genre");
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
        });
    }
}
