package group2.hackernews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class abc{
    String Title;
}

/**
 * Created by Zovin on 10/6/2015.
 */
public class someListAdaptor extends ArrayAdapter<abc> {
    private ArrayList<abc> abc;
    public someListAdaptor(Context context, int textViewResouceId, ArrayList<abc> items){
        super(context, textViewResouceId, items);
        this.abc=items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v==null){
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item, null);
        }
        abc o = abc.get(position);
        TextView tile = (TextView) v.findViewById(R.id.title);
        tile.setText(o.Title);
        return v;
    }
}
