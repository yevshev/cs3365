package group2.hackernews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

    ListView topList;
    ListView jobList;
    API_Getter processor;
    private ArrayList<Story> comments = new ArrayList<>();
    Intent intent;

    final static String topStories = "https://hacker-news.firebaseio.com/v0/topstories.json";
    final static String askStories = "https://hacker-news.firebaseio.com/v0/askstories.json";
    final static String jobStories = "https://hacker-news.firebaseio.com/v0/jobstories.json";
    final static String newStories = "https://hacker-news.firebaseio.com/v0/newstories.json";

    private StoryListAdapter jobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Loading...");

        //Find the main listview
        topList = (ListView) findViewById(R.id.list);

        processor = new API_Getter(topList);
        processor.use_url_to_get_IDArray_then_process(topStories);

        //Storing string resources into Array
        topList.setClickable(true);
        topList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Story o = (Story) topList.getItemAtPosition(position);
                //processor.clear_processing();
                String string;
                intent = new Intent(MainActivity.this, CommentActivity.class);
                if (o.getUri() == null)
                    Toast.makeText(getApplicationContext(), "Can't open article", Toast.LENGTH_LONG).show();
                string = o.getKids().toString();
                intent.putExtra("kids", string);
                startActivity(intent);
                return true;
            }
        });
        topList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Story o = (Story) topList.getItemAtPosition(position);
                //processor.clear_processing();
                String string;
                intent = new Intent(MainActivity.this, CommentActivity.class);
                if (o.getUri() == null)
                    Toast.makeText(getApplicationContext(), "Can't open article", Toast.LENGTH_LONG).show();
                else
                    browser1(o.getUri().toString());
            }
        });
        progressDialog.dismiss();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //open a browser using url
    public void browser1(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}