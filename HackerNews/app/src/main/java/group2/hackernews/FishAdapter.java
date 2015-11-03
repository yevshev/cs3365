package group2.hackernews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Zovin on 10/24/2015.
 */
public class FishAdapter extends ArrayAdapter<Story> {
    private ArrayList<Story> stories;

    public FishAdapter(Context context, int textViewResourceId, ArrayList<Story> items){
        super(context, textViewResourceId, items);
        this.stories = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.fish_layout, null);
        }
        Story story = stories.get(position);
        /*TextView title = (TextView) v.findViewById(R.id.title);
        TextView by = (TextView) v.findViewById(R.id.by);
        //TextView score = (TextView) v.findViewById(R.id.score);
        title.setText(o.title);
        by.setText(o.by);
        //score.setText(o.score);*/

        return v;
    }
}
