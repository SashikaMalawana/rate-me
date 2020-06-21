package hello.com.eventratingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SubscribedEventListActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ListView jSubscribedEventListView;
    private ArrayList<String> subscribedEventArrayList = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    private EditText jSubscribedEventFilterEditText;
    private ProgressDialog progressDialog;

    String currentUserFromIntent = "Jason";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribed_event_list);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserFromIntent).child("Subscribed Events");
        jSubscribedEventListView = (ListView) findViewById(R.id.xSubscribedEventListView);
        jSubscribedEventFilterEditText = (EditText) findViewById(R.id.xSubscribedEventFilterEditText);
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Events are loading...");
        progressDialog.show();

        arrayAdapter = new ArrayAdapter<String>(SubscribedEventListActivity.this, android.R.layout.simple_list_item_1, subscribedEventArrayList);
        jSubscribedEventListView.setAdapter(arrayAdapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String eventName = dataSnapshot.getKey();
                subscribedEventArrayList.add(eventName);
                arrayAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String eventKey = dataSnapshot.getKey();
                int index = subscribedEventArrayList.indexOf(eventKey);
                subscribedEventArrayList.set(index, eventKey);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String eventKey = dataSnapshot.getKey();
                int index = subscribedEventArrayList.indexOf(eventKey);
                subscribedEventArrayList.set(index, null);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        jSubscribedEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SubscribedEventListActivity.this, "Clicked list item " +(subscribedEventArrayList.get(position)), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SubscribedEventListActivity.this, EventHomeActivity.class);
                intent.putExtra("clickedItem", subscribedEventArrayList.get(position));
                startActivity(intent);

            }
        });

        jSubscribedEventFilterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (SubscribedEventListActivity.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
