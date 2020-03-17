package hello.com.eventratingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter{

    private Context contextt;
    private ArrayList<String> numberWord;
    private int[] numberImage;
    private LayoutInflater inflater;
    private String linkk;

    public MainAdapter(Context context, ArrayList<String> numberWord, int[] numberImage, String link){
        contextt = context;
        this.numberWord = numberWord;
        this.numberImage = numberImage;
        this.linkk = link;
    }

    @Override
    public int getCount() {
        return numberWord.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) contextt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.row_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.row_image_view);
        TextView textView = convertView.findViewById(R.id.row_text_view);

        //imageView.setImageResource(numberImage[0]);
        Picasso.get().load(linkk).into(imageView);
        textView.setText(numberWord.get(position));

        return convertView;
    }
}
