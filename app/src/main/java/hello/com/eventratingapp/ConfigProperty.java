package hello.com.eventratingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperty {

    private static String eventName = "Event Name";
    private static String countryOfOrigin = "Country Of Origin";
    private static String riginalLanguage = "Original Language";
    private static String genre = "Genre";

    public static String readPropertyFile() {
        String event = null;
        Properties property = new Properties();
        try {
            InputStream input = new FileInputStream("C:\\Users\\SASHIKA\\AndroidStudioProjects\\EventRatingApp\\app\\src\\main\\java\\hello\\com\\eventratingapp\\asset\\config.properties");
            property.load(input);
            event = property.getProperty("eventname");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    public static String getEventName() {
        return eventName;
    }

    public static String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public static String getOriginalLanguage() {
        return riginalLanguage;
    }

    public static String getGenre() {
        return genre;
    }

    public static void manageImageView (final Context currentContext, final ProgressDialog progressDialog, Intent data, StorageReference mStorage, ImageView imageView) {
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        Uri uri = data.getData();
//        Picasso.with(currentContext).load(uri).fit().centerCrop().into(imageView);
        Picasso.get().load(uri).fit().centerCrop().into(imageView);

        StorageReference filepath = mStorage.child("Event").child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(currentContext, "Upload done", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void setEventTextView (String clickedListViewItem, String childProperty, final TextView elementTextView) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Event").child(clickedListViewItem).child(childProperty);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                elementTextView.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void setCompetitorTextView(String clickedListViewItem, String childProperty, final TextView elementTextView) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Event").child(clickedListViewItem).child("Competitor").child(childProperty);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString().trim();
                elementTextView.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void setCountDownTimer(final TextView countDownTimerTextView, int duration, int stepSize) {

        new CountDownTimer(duration, stepSize) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownTimerTextView.setText("Time left : " +millisUntilFinished/1000 +"s");
            }

            @Override
            public void onFinish() {
                countDownTimerTextView.setText("Time is up");
            }
        }.start();

    }

}
