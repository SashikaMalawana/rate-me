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
import java.util.ArrayList;

public class AddRoundCompetitorActivity extends AppCompatActivity{

    private ArrayList<String> selectedItems = new ArrayList<>();
    private Button jAddCompetitorButton;
    private ArrayList<String> roundCompetitorArrayListFromIntent = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_round_competitor);

        jAddCompetitorButton = (Button) findViewById(R.id.xAddCompetitorButton);

        Intent intent = getIntent();
        roundCompetitorArrayListFromIntent = intent.getStringArrayListExtra("roundCompetitorArrayList");

        ListView listView = (ListView) findViewById(R.id.xcheckable_list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        String[] items = {"India", "Sri Lanka", "Pakistan", "Bangladesh"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row_layout, R.id.txt_lan, roundCompetitorArrayListFromIntent);
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
                for (String item:selectedItems) {
                    items += item + " and ";
                }
                Toast.makeText(AddRoundCompetitorActivity.this, "You clicked " +items, Toast.LENGTH_SHORT).show();

            }
        });

    }

}
