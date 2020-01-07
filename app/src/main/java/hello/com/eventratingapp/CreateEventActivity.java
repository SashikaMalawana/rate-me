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

public class CreateEventActivity extends AppCompatActivity {

    private Button storeButton;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseEvent;
    private EditText eventNameShortEditText;
    private EditText eventNameLongEditText;
    private EditText countryOfOriginEditText;
    private EditText originalLaguageEditText;
    private EditText genreEditText;

    private StorageReference mStorage;
    private ImageView mImageView;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        storeButton = (Button) findViewById(R.id.dButton);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        eventNameShortEditText = (EditText) findViewById(R.id.eventNameShortField);
        eventNameLongEditText = (EditText) findViewById(R.id.eventNameLongField);
        countryOfOriginEditText = (EditText) findViewById(R.id.countryOfOriginField);
        originalLaguageEditText = (EditText) findViewById(R.id.originalLanguageField);
        genreEditText = (EditText) findViewById(R.id.genreField);

        mStorage = FirebaseStorage.getInstance().getReference();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mProgressDialog = new ProgressDialog(this);

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String eventNameShort = eventNameShortEditText.getText().toString().trim();
                String eventNameLong = eventNameLongEditText.getText().toString().trim();
                String countryOfOrigin = countryOfOriginEditText.getText().toString().trim();
                String originalLanguage = originalLaguageEditText.getText().toString().trim();
                String genre = genreEditText.getText().toString().trim();

                if(!eventNameShort.isEmpty() && !eventNameLong.isEmpty() && !countryOfOrigin.isEmpty() && !originalLanguage.isEmpty() && !genre.isEmpty()) {

                    mDatabase.child(eventNameShort);
                    mDatabaseEvent = mDatabase.child(eventNameShort);

                    if (mImageView.getDrawable() != null) {

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

                            eventNameShortEditText.setText("");
                            eventNameLongEditText.setText("");
                            countryOfOriginEditText.setText("");
                            originalLaguageEditText.setText("");
                            genreEditText.setText("");

                        }
                        catch (Exception exception) {
                            System.out.println(exception.toString());
                        }

                        try {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                            builder.setMessage("You have successfully create an event :)");
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

        mImageView.setOnClickListener(new View.OnClickListener() {
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

            EditText eventNameTextView = (EditText) findViewById(R.id.eventNameLongField);
            String eventName = eventNameTextView.getText().toString().trim();

            if (!eventName.isEmpty()) {

                try {

                    Uri uri = data.getData();
                    Picasso.get().load(uri).fit().centerCrop().into(mImageView);

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
                            DatabaseReference imageToStore = FirebaseDatabase.getInstance().getReference().child("Event").child(eventNameShortGet).child("imageUrl");
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
