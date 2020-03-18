package hello.com.eventratingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventRoundDashboardActivity extends AppCompatActivity {

    private GridView jGridView;
    String[] numberWord = {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten"};
    int[] numberImage = {R.drawable.pebbles};

    private DatabaseReference mDatabase;
    private ArrayList<String> roundArrayList = new ArrayList<String>();

    String link;

    MainAdapter adapter;

    FloatingActionButton jCreateRoundFloatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_round_dashboard);

        jGridView = (GridView) findViewById(R.id.xRoundGridView);

        jCreateRoundFloatingActionButton = (FloatingActionButton) findViewById(R.id.xCreateRoundFloatingActionButton);

        Intent intent = getIntent();
        final String eventFromIntent = intent.getStringExtra("eventName");
        String linkFromIntent = intent.getStringExtra("link");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(eventFromIntent).child("Rounds");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String round = dataSnapshot.getKey();
                roundArrayList.add(round);
                adapter.notifyDataSetChanged();
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

        DatabaseReference imagePath = FirebaseDatabase.getInstance().getReference().child("Events").child(eventFromIntent).child("Image Url");
        imagePath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                link = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new MainAdapter(EventRoundDashboardActivity.this, roundArrayList, numberImage, linkFromIntent);
        jGridView.setAdapter(adapter);

        jGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "You clicked " + roundArrayList.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EventRoundDashboardActivity.this, EventRoundHomeActivity.class);
                intent.putExtra("eventName", eventFromIntent);
                intent.putExtra("roundName", roundArrayList.get(position));
                startActivity(intent);
            }
        });

        jCreateRoundFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventRoundDashboardActivity.this, CreateEventRoundActivity.class);
                intent.putExtra("eventName", eventFromIntent);
                startActivity(intent);
            }
        });

    }
}
