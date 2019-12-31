package hello.com.eventratingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ListView eventListView;
    private ArrayList<String> eventArrayList = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    private EditText eventFilterEditText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        eventListView = (ListView) findViewById(R.id.eventListView);
        eventFilterEditText = (EditText) findViewById(R.id.eventFilterEditText);
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Events are loading...");
        progressDialog.show();

        arrayAdapter = new ArrayAdapter<String>(EventListActivity.this, android.R.layout.simple_list_item_1, eventArrayList);
        eventListView.setAdapter(arrayAdapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String eventName = dataSnapshot.getKey();
                eventArrayList.add(eventName);
                arrayAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String eventKey = dataSnapshot.getKey();
                int index = eventArrayList.indexOf(eventKey);
                eventArrayList.set(index, eventKey);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String eventKey = dataSnapshot.getKey();
                int index = eventArrayList.indexOf(eventKey);
                eventArrayList.set(index, null);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EventListActivity.this, "Clicked list item " +(eventArrayList.get(position)), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EventListActivity.this, EventHomeActivity.class);
                intent.putExtra("clickedItem", eventArrayList.get(position));
                startActivity(intent);

            }
        });

        eventFilterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (EventListActivity.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
