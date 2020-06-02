package hello.com.eventratingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AddRoundCompetitorActivity extends AppCompatActivity{

    private ArrayList<String> selectedItems = new ArrayList<>();
    private Button jAddCompetitorsButton;
    private Button jRemoveCompetitorsButton;
    private TextView jAddRoundCompetitorHeadTextView;
    private TextView jAddRoundCompetitorTextView;
    private ArrayList<String> eventCompetitorArrayListFromIntent = new ArrayList<String>();
    private ArrayList<String> roundCompetitorArrayList2FromIntent = new ArrayList<String>();
    private String eventNameFromIntent;
    private String roundNameFromIntent;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_round_competitor);

        jAddCompetitorsButton = (Button) findViewById(R.id.xAddCompetitorsButton);
        jRemoveCompetitorsButton = (Button) findViewById(R.id.xRemoveCompetitorsButton);
        jAddRoundCompetitorHeadTextView = (TextView) findViewById(R.id.xAddRoundCompetitorHeadTextView);
        jAddRoundCompetitorTextView = (TextView) findViewById(R.id.xAddRoundCompetitorTextView);

        Intent intent = getIntent();
        eventCompetitorArrayListFromIntent = intent.getStringArrayListExtra("eventCompetitorArrayList");
        roundCompetitorArrayList2FromIntent = intent.getStringArrayListExtra("roundCompetitorArrayList");
        eventNameFromIntent = intent.getStringExtra("eventName");
        roundNameFromIntent = intent.getStringExtra("roundName");

        jAddRoundCompetitorHeadTextView.setText(roundNameFromIntent);
        jAddRoundCompetitorTextView.setText("Add competitors from " +eventNameFromIntent +" event to " +roundNameFromIntent +" round");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(eventNameFromIntent).child("Rounds").child(roundNameFromIntent).child("Round Competitors");
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Events").child(eventNameFromIntent).child("Event Competitors");

        ListView listView = (ListView) findViewById(R.id.xCheckableListView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        String[] items = {"India", "Sri Lanka", "Pakistan", "Bangladesh"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row_layout, R.id.xCheckableTextView, eventCompetitorArrayListFromIntent);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = ((TextView)view).getText().toString();
                if (selectedItems.contains(selectedItem)) {
                    selectedItems.remove(selectedItem);
                }
                else {
                    selectedItems.add(selectedItem);
                }

            }
        });

        jAddCompetitorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String items = "";
                for (String item : selectedItems) {
                    items += item + " and ";
                }
                Toast.makeText(AddRoundCompetitorActivity.this, "You added " +items +" to the round", Toast.LENGTH_SHORT).show();

                ArrayList<String> combinedArrayList = new ArrayList<String>();
                combinedArrayList = CombineTwoArrayLists(roundCompetitorArrayList2FromIntent, selectedItems);
                //mDatabase.setValue(combinedArrayList);

                SetRoundData(combinedArrayList, mDatabase, mDatabase2);

            }
        });

        jRemoveCompetitorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String items = "";
                for (String item : selectedItems) {
                    items += item + " and ";
                }
                Toast.makeText(AddRoundCompetitorActivity.this, "You removed " +items +" from the round", Toast.LENGTH_SHORT).show();

                ArrayList<String> combinedArrayList = new ArrayList<String>();
                combinedArrayList = SeparateTwoArrayLists(roundCompetitorArrayList2FromIntent, selectedItems);
                //mDatabase.setValue(combinedArrayList);

                RemoveRoundData(selectedItems, mDatabase, mDatabase2);

            }
        });

    }

    public ArrayList<String> CombineTwoArrayLists(ArrayList<String> x, ArrayList<String> y) {

        ArrayList<String> combinedArrayList = new ArrayList<String>();
        combinedArrayList.addAll(x);
        for (String item : y) {
            if (x.contains(item)) {
                continue;
            }
            else {
                combinedArrayList.add(item);
            }
        }
        return combinedArrayList;

    }

    public static void SetRoundData(final ArrayList<String> combinedArrayList, final DatabaseReference mDatabase, DatabaseReference mDatabase2) {

        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (String arrayItem : combinedArrayList) {

                    String s1 = dataSnapshot.child(arrayItem).child("Competitor Name").getValue().toString();
                    String s2 = dataSnapshot.child(arrayItem).child("Date Of Birth").getValue().toString();
                    String s3 = dataSnapshot.child(arrayItem).child("Description").getValue().toString();
                    String s4 = dataSnapshot.child(arrayItem).child("Hometown").getValue().toString();
                    String s5 = dataSnapshot.child(arrayItem).child("Image Url").getValue().toString();
                    String s6 = dataSnapshot.child(arrayItem).child("Performance Type").getValue().toString();

                    mDatabase.child(arrayItem).child("Competitor Name").setValue(s1);
                    mDatabase.child(arrayItem).child("Date Of Birth").setValue(s2);
                    mDatabase.child(arrayItem).child("Description").setValue(s3);
                    mDatabase.child(arrayItem).child("Hometown").setValue(s4);
                    mDatabase.child(arrayItem).child("Image Url").setValue(s5);
                    mDatabase.child(arrayItem).child("Performance Type").setValue(s6);
                    mDatabase.child(arrayItem).child("Ratings").child("No Of Ratings").setValue(0);
                    mDatabase.child(arrayItem).child("Ratings").child("Reviews").setValue(0);
                    mDatabase.child(arrayItem).child("Ratings").child("Total Rating Points").setValue(0);
                    mDatabase.child(arrayItem).child("Ratings").child("Weighted Average Rating").setValue(0);

                    mDatabase.child(arrayItem).child("Analytical Ratings").child("1").setValue(0);
                    mDatabase.child(arrayItem).child("Analytical Ratings").child("2").setValue(0);
                    mDatabase.child(arrayItem).child("Analytical Ratings").child("3").setValue(0);
                    mDatabase.child(arrayItem).child("Analytical Ratings").child("4").setValue(0);
                    mDatabase.child(arrayItem).child("Analytical Ratings").child("5").setValue(0);
                    mDatabase.child(arrayItem).child("Analytical Ratings").child("6").setValue(0);
                    mDatabase.child(arrayItem).child("Analytical Ratings").child("7").setValue(0);
                    mDatabase.child(arrayItem).child("Analytical Ratings").child("8").setValue(0);
                    mDatabase.child(arrayItem).child("Analytical Ratings").child("9").setValue(0);
                    mDatabase.child(arrayItem).child("Analytical Ratings").child("10").setValue(0);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<String> SeparateTwoArrayLists(ArrayList<String> x, ArrayList<String> y) {

        ArrayList<String> separateArrayList = new ArrayList<String>();
        separateArrayList.addAll(x);
        for (String item : y) {
            if (x.contains(item)) {
                separateArrayList.remove(item);
            }
            else {
                continue;
            }
        }
        return separateArrayList;

    }

    public static void RemoveRoundData(final ArrayList<String> selectedArrayList, final DatabaseReference mDatabase, DatabaseReference mDatabase2) {

        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (String arrayItem : selectedArrayList) {

                    DatabaseReference removeReference = mDatabase.child(arrayItem);
                    removeReference.removeValue();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
