package hello.com.eventratingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCompetitorActivity extends AppCompatActivity {

    private Button storeButton;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseCompetitor;
    private EditText competitorNameEditText;
    private EditText dateOfBirthEditText;
    private EditText hometownEditText;
    private EditText performanceTypeEditText;
    private EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_competitor);

        storeButton = (Button) findViewById(R.id.dButton);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Competitor");
        competitorNameEditText = (EditText) findViewById(R.id.competitorNameField);
        dateOfBirthEditText = (EditText) findViewById(R.id.dateOfBirthField);
        hometownEditText = (EditText) findViewById(R.id.hometownField);
        performanceTypeEditText = (EditText) findViewById(R.id.performanceTypeField);
        descriptionEditText = (EditText) findViewById(R.id.descriptionField);

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String competitorName = competitorNameEditText.getText().toString().trim();
                String dateOfBirth = dateOfBirthEditText.getText().toString().trim();
                String hometown = hometownEditText.getText().toString().trim();
                String performanceType = performanceTypeEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                if(!competitorName.isEmpty() && !dateOfBirth.isEmpty() && !hometown.isEmpty() || !performanceType.isEmpty() && !description.isEmpty()) {
                    mDatabase.child(competitorName);
                    mDatabaseCompetitor = mDatabase.child(competitorName);

                    mDatabaseCompetitor.child("Competitor Name").setValue(competitorName).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(CreateCompetitorActivity.this, "Competitor Name is stored", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(CreateCompetitorActivity.this, "Competitor Name is not stored", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mDatabaseCompetitor.child("Date Of Birth").setValue(dateOfBirth).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(CreateCompetitorActivity.this, "Date Of Birth is stored", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(CreateCompetitorActivity.this, "Date Of Birth is not stored", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mDatabaseCompetitor.child("Hometown").setValue(hometown).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(CreateCompetitorActivity.this, "Hometown is stored", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(CreateCompetitorActivity.this, "Hometown is not stored", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mDatabaseCompetitor.child("Performance Type").setValue(performanceType).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(CreateCompetitorActivity.this, "Performance Type is stored", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(CreateCompetitorActivity.this, "Performance Type is not stored", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mDatabaseCompetitor.child("Description").setValue(description).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(CreateCompetitorActivity.this, "Description is stored", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(CreateCompetitorActivity.this, "Description is not stored", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(CreateCompetitorActivity.this, "Empty fields are not aloowed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
