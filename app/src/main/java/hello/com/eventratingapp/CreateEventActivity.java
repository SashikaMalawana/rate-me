package hello.com.eventratingapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CreateEventActivity extends AppCompatActivity {

    private Button storeButton;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseEvent;
    private EditText eventNameShortEditText;
    private EditText eventNameLongEditText;
    private EditText countryOfOriginEditText;
    private EditText originalLanguageEditText;
    private EditText genreEditText;

    private Spinner eventSpinner;
    private String languageList[] = {"English", "French", "Spanish", "German", "Italian", "Chines", "Japenes", "Korean", "Hindi", "Tamil", "Telugu", "Malayalam", "Panjab", "Gujarati", "Sinhala"};
    private ArrayList<String> languageArrayList = new ArrayList<String>();
    String languageFromSpinner = null;

    private StorageReference mStorage;
    private ImageView createEventImageView;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        storeButton = (Button) findViewById(R.id.dButton);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        eventNameShortEditText = (EditText) findViewById(R.id.eventNameShortField);
        eventNameLongEditText = (EditText) findViewById(R.id.eventNameLongField);
        countryOfOriginEditText = (EditText) findViewById(R.id.countryOfOriginField);
        originalLanguageEditText = (EditText) findViewById(R.id.originalLanguageField);
        genreEditText = (EditText) findViewById(R.id.genreField);

        eventSpinner = (Spinner) findViewById(R.id.eventSpinner);

        mStorage = FirebaseStorage.getInstance().getReference();
        createEventImageView = (ImageView) findViewById(R.id.createEventImageView);
        mProgressDialog = new ProgressDialog(this);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, languageArrayList);
        eventSpinner.setAdapter(arrayAdapter);

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DatabaseReference languageDatabase = FirebaseDatabase.getInstance().getReference().child("Languages");
        languageDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                languageFromSpinner = dataSnapshot.getValue().toString();
                languageArrayList.add(languageFromSpinner);
                arrayAdapter.notifyDataSetChanged();
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

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String eventNameShort = eventNameShortEditText.getText().toString().trim();
                String eventNameLong = eventNameLongEditText.getText().toString().trim();
                String countryOfOrigin = countryOfOriginEditText.getText().toString().trim();
                String originalLanguage = originalLanguageEditText.getText().toString().trim();
                String genre = genreEditText.getText().toString().trim();
                String langFromSpinner = eventSpinner.getSelectedItem().toString();

                if(!eventNameShort.isEmpty() && !eventNameLong.isEmpty() && !countryOfOrigin.isEmpty() && !langFromSpinner.isEmpty() && !genre.isEmpty()) {

                    mDatabaseEvent = mDatabase.child(eventNameShort);

                    if (createEventImageView.getDrawable() != null) {

                        try {

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
                            mDatabaseEvent.child("Original Language").setValue(langFromSpinner).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                            eventNameShortEditText.setText("");
                            eventNameLongEditText.setText("");
                            countryOfOriginEditText.setText("");
                            originalLanguageEditText.setText("");
                            genreEditText.setText("");

                        }
                        catch (Exception exception) {
                            System.out.println(exception.toString());
                        }

                        try {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                            builder.setMessage("You have successfully create " +eventNameLong +" event :)");
                            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent intent = new Intent(CreateEventActivity.this, EventHomeActivity.class);
                                    intent.putExtra("clickedItem", eventNameShort);
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
                        Toast.makeText(CreateEventActivity.this, "Empty event image is not allowed", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(CreateEventActivity.this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        createEventImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        final TextView countDownTimerTextView = (TextView) findViewById(R.id.countDownTimer);
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownTimerTextView.setText("Time left : " +millisUntilFinished/1000 +"s");
            }

            @Override
            public void onFinish() {
                countDownTimerTextView.setText("Time is up!");
            }
        }.start();
        //ConfigProperty.setCountDownTimer(countDownTimerTextView, 10000, 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            EditText eventNameTextView = (EditText) findViewById(R.id.eventNameLongField);
            String eventName = eventNameTextView.getText().toString().trim();

            if (!eventName.isEmpty()) {

                try {

                    Uri uri = data.getData();
                    Picasso.get().load(uri).fit().centerCrop().into(createEventImageView);

                    mProgressDialog.setMessage("Uploading...");
                    mProgressDialog.show();

                    StorageReference filepath = mStorage.child("Images").child("Events").child(eventName).child(uri.getLastPathSegment());
                    filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mProgressDialog.dismiss();
                            Toast.makeText(CreateEventActivity.this, "Upload finished", Toast.LENGTH_LONG).show();

                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            String downloadUrlStr = downloadUrl.toString();
                            EditText eventNameShort = (EditText) findViewById(R.id.eventNameShortField);
                            String eventNameShortGet = eventNameShort.getText().toString().trim();
                            DatabaseReference imageToStore = FirebaseDatabase.getInstance().getReference().child("Events").child(eventNameShortGet).child("Image Url");
                            imageToStore.setValue(downloadUrlStr);
                        }
                    });

                }
                catch (Exception exception) {
                    System.out.println(exception.toString());
                }

            }
            else {
                mProgressDialog.dismiss();
                Toast.makeText(CreateEventActivity.this, "Provide at least Event Name to store the image", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
