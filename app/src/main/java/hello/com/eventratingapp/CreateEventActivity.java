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

public class CreateEventActivity extends AppCompatActivity {

    private Button storeButton;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseEvent;
    private EditText eventNameShortEditText;
    private EditText eventNameLongEditText;
    private EditText countryOfOriginEditText;
    private EditText originalLaguageEditTExt;
    private EditText genreEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        storeButton = (Button) findViewById(R.id.dButton);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        eventNameShortEditText = (EditText) findViewById(R.id.eventNameShortField);
        eventNameLongEditText = (EditText) findViewById(R.id.eventNameLongField);
        countryOfOriginEditText = (EditText) findViewById(R.id.countryOfOriginField);
        originalLaguageEditTExt = (EditText) findViewById(R.id.originalLanguageField);
        genreEditText = (EditText) findViewById(R.id.genreField);

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventNameShort = eventNameShortEditText.getText().toString().trim();
                String eventNameLong = eventNameLongEditText.getText().toString().trim();
                String countryOfOrigin = countryOfOriginEditText.getText().toString().trim();
                String originalLanguage = originalLaguageEditTExt.getText().toString().trim();
                String genre = genreEditText.getText().toString().trim();

                if(!eventNameShort.isEmpty() && !eventNameLong.isEmpty() && !countryOfOrigin.isEmpty() && !originalLanguage.isEmpty() && !genre.isEmpty()) {
                    mDatabase.child(eventNameShort);
                    mDatabaseEvent = mDatabase.child(eventNameShort);

                    mDatabaseEvent.child("Event Name").setValue(eventNameLong).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(CreateEventActivity.this, "Event Name is stored", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(CreateEventActivity.this, "Event name is not stored", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mDatabaseEvent.child("Country Of Origin").setValue(countryOfOrigin).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(CreateEventActivity.this, "Country Of Origin is stored", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(CreateEventActivity.this, "Country Of Origin is not stored", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mDatabaseEvent.child("Original Language").setValue(originalLanguage).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(CreateEventActivity.this, "Original Language is stored", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(CreateEventActivity.this, "Original Language is not stored", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mDatabaseEvent.child("Genre").setValue(genre).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(CreateEventActivity.this, "Genre is stored", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(CreateEventActivity.this, "Genre is not stored", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(CreateEventActivity.this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
