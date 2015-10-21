package group2.hackernews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView topList;
    ListView jobList;
    API_Getter processor;
    private ArrayList<Story> comments = new ArrayList<>();

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
        topList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Story o = (Story) topList.getItemAtPosition(position);
                processor.clear_processing();
                if (o.getUri() == null)
                    Toast.makeText(getApplicationContext(), "Can't open article", Toast.LENGTH_LONG).show();
                for(int i = 0; i < o.getKids().length(); i++){
                    try {
                        processor.get_JSON_from_HN_and_set_UI_elements(o.getKids().getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                topList.setClickable(false);
            }
    });
        progressDialog.dismiss();
    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //open a browser using url
    public void browser1(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}