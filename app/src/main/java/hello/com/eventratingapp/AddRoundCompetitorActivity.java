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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddRoundCompetitorActivity extends AppCompatActivity{

    private ArrayList<String> selectedItems = new ArrayList<>();
    private Button jAddCompetitorButton;
    private TextView jAddRoundCompetitorHeadTextView;
    private TextView jAddRoundCompetitorTextView;
    private ArrayList<String> eventCompetitorArrayListFromIntent = new ArrayList<String>();
    private ArrayList<String> roundCompetitorArrayList2FromIntent = new ArrayList<String>();
    private String eventNameFromIntent;
    private String roundNameFromIntent;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_round_competitor);

        jAddCompetitorButton = (Button) findViewById(R.id.xAddCompetitorButton);
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

        jAddCompetitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String items = "";
                for (String item : selectedItems) {
                    items += item + " and ";
                }
                Toast.makeText(AddRoundCompetitorActivity.this, "You added " +items +"to the round", Toast.LENGTH_SHORT).show();

                ArrayList<String> combinedArrayList = new ArrayList<String>();
                combinedArrayList = CombineTwoArrayLists(roundCompetitorArrayList2FromIntent, selectedItems);
                mDatabase.setValue(combinedArrayList);

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

}
