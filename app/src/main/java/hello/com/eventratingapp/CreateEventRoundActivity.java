package hello.com.eventratingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEventRoundActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    DatabaseReference mDatabaseRound;
    EditText jRoundNameEditText;
    EditText jNoOfCompetitorsEditText;
    EditText jRoundGuestEditText;
    EditText jActiveTimePeriodEditText;
    Button jCreateRoundButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_round);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        jRoundNameEditText = (EditText) findViewById(R.id.xRoundNameEditText);
        jNoOfCompetitorsEditText = (EditText) findViewById(R.id.xNoOfCompetitorsEditText);
        jRoundGuestEditText = (EditText) findViewById(R.id.xRoundGuestEditText);
        jActiveTimePeriodEditText = (EditText) findViewById(R.id.xActiveTimePeriodEditText);
        jCreateRoundButton = (Button) findViewById(R.id.xCreateRoundButton);

        Intent intent = getIntent();
        final String eventNameFromIntent = intent.getStringExtra("eventName");

        jCreateRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String roundName = jRoundNameEditText.getText().toString().trim();
                String noOfCompetitors = jNoOfCompetitorsEditText.getText().toString().trim();
                String roundGuest = jRoundGuestEditText.getText().toString().trim();
                String activeTimePeriod = jActiveTimePeriodEditText.getText().toString().trim();

                if (!roundName.isEmpty() && !noOfCompetitors.isEmpty() && !roundGuest.isEmpty() && !activeTimePeriod.isEmpty()) {

                    mDatabaseRound = mDatabase.child(eventNameFromIntent).child("Rounds").child(roundName);

                    try {

                        mDatabaseRound.child("Round Name").setValue(roundName).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CreateEventRoundActivity.this, "Round name is stored", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(CreateEventRoundActivity.this, "Round name is not stored", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        mDatabaseRound.child("No Of Competitors").setValue(noOfCompetitors).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CreateEventRoundActivity.this, "No of competitors is stored", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(CreateEventRoundActivity.this, "No of competitors is not stored", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        mDatabaseRound.child("Round Guest").setValue(roundGuest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CreateEventRoundActivity.this, "Round guest is stored", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(CreateEventRoundActivity.this, "Round guest is not stored", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        mDatabaseRound.child("Active Time Period").setValue(activeTimePeriod).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CreateEventRoundActivity.this, "Active time period is stored", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(CreateEventRoundActivity.this, "Active time period is not stored", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        jRoundNameEditText.setText("");
                        jNoOfCompetitorsEditText.setText("");
                        jRoundGuestEditText.setText("");
                        jActiveTimePeriodEditText.setText("");

                    }
                    catch (Exception exception) {
                        System.out.println(exception.toString());
                    }

                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventRoundActivity.this);
                        builder.setMessage("You have successfully create " +roundName +" round :)");
                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent intent = new Intent(CreateEventRoundActivity.this, EventRoundHomeActivity.class);
                                intent.putExtra("eventName", eventNameFromIntent);
                                intent.putExtra("roundName", roundName);
                                startActivity(intent);
                            }
                        });
                        builder.create().show();
                    }
                    catch (Exception exception) {
                        System.out.println(exception.toString());
                    }

                }
                else {
                    Toast.makeText(CreateEventRoundActivity.this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
