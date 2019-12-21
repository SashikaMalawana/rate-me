package hello.com.eventratingapp;

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
    private TextView eventNameLongTeztView;
    private TextView countryOfOriginTextView;
    private TextView originalLnaguageTextView;
    private TextView genreTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_home);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        eventNameShortTextView = (TextView) findViewById(R.id.eventNameShortTextView);
        eventNameLongTeztView = (TextView) findViewById(R.id.eventNameLongTextView);
        countryOfOriginTextView = (TextView) findViewById(R.id.countryOfOriginTextView);
        originalLnaguageTextView = (TextView) findViewById(R.id.originalLanguageTextView);
        genreTextView = (TextView) findViewById(R.id.genreTextView);

//        PassListViewItemPosition pass = new PassListViewItemPosition();
//        String clickedListViewItem = pass.getArrayListValue();
        String clickedListViewItem = "AGT";
        DatabaseReference mDatabaseEventName = FirebaseDatabase.getInstance().getReference().child("Event").child(clickedListViewItem).child("Event Name");
        mDatabaseEventName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                eventNameLongTeztView.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
