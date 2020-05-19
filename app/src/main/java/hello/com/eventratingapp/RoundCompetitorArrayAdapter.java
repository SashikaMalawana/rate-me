package hello.com.eventratingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class RoundCompetitorArrayAdapter extends ArrayAdapter<RoundCompetitor> {

    private Context mContext;
    private int mResource;

    public RoundCompetitorArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<RoundCompetitor> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String hometown = getItem(position).getHometown();
        String weightedAverageRating = getItem(position).getWeightedAverageRating();

        RoundCompetitor Competitor = new RoundCompetitor(name, hometown, weightedAverageRating);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.xCompetitorNameTextView);
        TextView tvHometown = (TextView) convertView.findViewById(R.id.xHometownTextView);
        TextView tvWeightedAverageRating = (TextView) convertView.findViewById(R.id.xWeightedAverageRatingTextView);

        tvName.setText(name);
        tvHometown.setText(hometown);
        tvWeightedAverageRating.setText(weightedAverageRating);

        return convertView;

    }
}
