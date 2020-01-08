package hello.com.eventratingapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class CreateCompetitorActivity extends AppCompatActivity {

    private Button storeButton;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseCompetitor;
    private EditText competitorNameEditText;
    private EditText dateOfBirthEditText;
    private EditText hometownEditText;
    private EditText performanceTypeEditText;
    private EditText descriptionEditText;

    private StorageReference mStorage;
    private Button selectCompetitorImageButton;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog;

    private ImageView competitorImageView;

    String eventNameFromIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_competitor);

        storeButton = (Button) findViewById(R.id.dButton);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        competitorNameEditText = (EditText) findViewById(R.id.competitorNameField);
        dateOfBirthEditText = (EditText) findViewById(R.id.dateOfBirthField);
        hometownEditText = (EditText) findViewById(R.id.hometownField);
        performanceTypeEditText = (EditText) findViewById(R.id.performanceTypeField);
        descriptionEditText = (EditText) findViewById(R.id.descriptionField);

        mStorage = FirebaseStorage.getInstance().getReference();
        competitorImageView = (ImageView) findViewById(R.id.competitorImageView);
        mProgressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        eventNameFromIntent = intent.getStringExtra("eventName");

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String competitorName = competitorNameEditText.getText().toString().trim();
                String dateOfBirth = dateOfBirthEditText.getText().toString().trim();
                String hometown = hometownEditText.getText().toString().trim();
                String performanceType = performanceTypeEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                if(!competitorName.isEmpty() && !dateOfBirth.isEmpty() && !hometown.isEmpty() || !performanceType.isEmpty() && !description.isEmpty()) {

                    mDatabaseCompetitor = mDatabase.child(eventNameFromIntent).child("Event Competitors").child(competitorName);

                    if (competitorImageView.getDrawable() != null) {

                        try {
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

                            competitorNameEditText.setText("");
                            dateOfBirthEditText.setText("");
                            hometownEditText.setText("");
                            performanceTypeEditText.setText("");
                            descriptionEditText.setText("");

                            DatabaseReference mRatingReference = FirebaseDatabase.getInstance().getReference().child("Event").child(eventNameFromIntent).child("Event Competitors").child(competitorName).child("Rating");
                            mRatingReference.child("No Of Ratings").setValue(0);
                            mRatingReference.child("Total Rating Points").setValue(0);
                            mRatingReference.child("Weighted Average Rating").setValue(0);
                            mRatingReference.child("Reviews").setValue(null);

                        }
                        catch (Exception exception) {
                            System.out.println(exception.toString());
                        }

                        try {
                            AlertDialog.Builder myBuilder = new AlertDialog.Builder(CreateCompetitorActivity.this);
                            myBuilder.setTitle("You have successfully create " +competitorName);
                            myBuilder.setMessage("Do you want to create more competitors?");
                            myBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
//                                    finish();
//                                    startActivity(getIntent());
                                }
                            });
                            myBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent intent = new Intent(CreateCompetitorActivity.this, EventCompetitorProfileActivity.class);
                                    intent.putExtra("currentEvent", eventNameFromIntent);
                                    intent.putExtra("clickedItem", competitorName);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog alertDialog = myBuilder.create();
                            alertDialog.show();
//                            myBuilder.create().show();
                        }
                        catch (Exception exception) {
                            System.out.println(exception.toString());
                        }

                    }
                    else {
                        Toast.makeText(CreateCompetitorActivity.this, "Empty competitor image is not allowed", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(CreateCompetitorActivity.this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        competitorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            EditText competitorNameEditText = (EditText) findViewById(R.id.competitorNameField);
            String competitorName = competitorNameEditText.getText().toString().trim();

            if (!competitorName.isEmpty()) {

                try {

                    Uri uri = data.getData();
                    Picasso.get().load(uri).fit().centerCrop().into(competitorImageView);

                    mProgressDialog.setMessage("Uploading");
                    mProgressDialog.show();

                    StorageReference filepath = mStorage.child("Images").child("Events").child(eventNameFromIntent).child("Competitors").child(competitorName).child(uri.getLastPathSegment());
                    filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mProgressDialog.dismiss();
                            Toast.makeText(CreateCompetitorActivity.this, "Upload done", Toast.LENGTH_LONG).show();

                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            String downloadUriStr = downloadUri.toString();
                            EditText competitorName = (EditText) findViewById(R.id.competitorNameField);
                            String competitorNameGet = competitorName.getText().toString().trim();
                            DatabaseReference imageToStore = FirebaseDatabase.getInstance().getReference().child("Event").child(eventNameFromIntent).child("Event Competitors").child(competitorNameGet).child("imageUrl");
                            imageToStore.setValue(downloadUriStr);
                        }
                    });

                }
                catch (Exception exception) {
                    System.out.println(exception.toString());
                }
            }

        }

    }
}
