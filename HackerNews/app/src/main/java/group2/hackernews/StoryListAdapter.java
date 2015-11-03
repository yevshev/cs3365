package group2.hackernews;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Zovin on 10/7/2015.
 * A custom adapter to list stories
 */
public class StoryListAdapter extends ArrayAdapter<Story> {
    private ArrayList<Story> stories;

    public StoryListAdapter(Context context, int textViewResourceId, ArrayList<Story> items){
        super(context, textViewResourceId, items);
        this.stories = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.fish_layout, null);
        }
        Story o = stories.get(position);
        ImageView imageView = (ImageView) v.findViewById(R.id.image);
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.lay);
        Random random = new Random();
        int randInt = random.nextInt(100);
        randInt = randInt%3;
        switch (randInt){
            case 0:
                layout.setGravity(RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            case 1:
                layout.setGravity(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
            case 2:
                layout.setGravity(RelativeLayout.CENTER_IN_PARENT);
                break;
        }

        /*wvFish = (WebView) v.findViewById(findId);
        wvFish.loadUrl("file:///android_asset/fishMovingSmall.gif");
        wvFish.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Story o = stories.get(position);
                if (o.uri == null)
                    Toast.makeText(getApplicationContext(), "Can't open article", Toast.LENGTH_LONG).show();
                MainActivity.browser1(o.uri);
                return false;
            }
        });*/
        /*TextView title = (TextView) v.findViewById(R.id.title);
        TextView by = (TextView) v.findViewById(R.id.by);
        //TextView score = (TextView) v.findViewById(R.id.score);
        title.setText(o.title);
        by.setText(o.by);*/
        //score.setText(o.score);

        return v;
    }
}