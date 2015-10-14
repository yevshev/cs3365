package group2.hackernews;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MainRequestQueue getter = MainRequestQueue.getInstance();
    ListView topList;
    ListView jobList;

    String title_url = "https://hacker-news.firebaseio.com/v0/item/";


    final static String topStories = "https://hacker-news.firebaseio.com/v0/topstories.json";
    final static String askStories = "https://hacker-news.firebaseio.com/v0/askstories.json";
    final static String jobStories = "https://hacker-news.firebaseio.com/v0/jobstories.json";
    final static String newStories = "https://hacker-news.firebaseio.com/v0/newstories.json";

    private ArrayList<Story> stories = new ArrayList<>();
    private ArrayList<String> some = new ArrayList<>();
    private StoryListAdapter topAdapter;
    private StoryListAdapter jobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Loading...");

        //Find the main listview
        topList = (ListView) findViewById(R.id.list);

        //get the list ready to populate
        topAdapter = new StoryListAdapter(topList.getContext(), R.layout.list_item, stories);
        topList.setAdapter(topAdapter);

        populate_list(topStories, topAdapter);

        //Storing string resources into Array
        topList.setClickable(true);
        topList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Story o = (Story) topList.getItemAtPosition(position);
                if (o.uri == null)
                    Toast.makeText(getApplicationContext(), "Can't open article", Toast.LENGTH_LONG).show();
                browser1(o.uri);
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

    //Gets the JSON Array filled with article ID's depending on the type of post.  ie Top, Show, Ask...
    private void populate_list(String source, final StoryListAdapter listAdapter){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, source, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //uses the id list to make a call to get detailed post info
                //from JSON objects using the ID's in the array.
                try{
                    for (int i=0;i<response.length()/10;i++){
                        //Make the JSON request for each id.
                        fill_text_list(response.getString(i), listAdapter);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new AlertDialog.Builder(getApplicationContext())
                        .setMessage(error.getMessage())
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        });
        getter.add(jsonArrayRequest);
    }

    //Gets a JSON Object from HackerNews and populates the list.
    private void fill_text_list(String id, final StoryListAdapter listAdapter){
        String uri = title_url + id + ".json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Story story = new Story();
                    story.title = response.getString("title");
                    story.by    = response.getString("by");
                    story.score = Integer.toString(response.getInt("score"));
                    story.uri   = response.getString("url");
                    stories.add(story);
                    listAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new AlertDialog.Builder(getApplicationContext())
                        .setMessage(error.getMessage())
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        });
        getter.add(jsonObjectRequest);
    }
}