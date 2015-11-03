package group2.hackernews;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.app.ListActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.Source;

public class MainActivity extends AppCompatActivity {


    Button article_1;
    Button article_2;
    Button article_3;
    HackerHelper getter = HackerHelper.getInstance();
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
        /*WebView webView = (WebView) findViewById(R.id.webView2);
        webView.loadUrl("file:///android_asset/fishMovingSmall.gif");*/
        /**//*article_1 = (Button) findViewById(R.id.article1);
        article_2 = (Button) findViewById(R.id.article2);
        article_3 = (Button) findViewById(R.id.article3);*/

        ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Loading...");
        //Find the main listview
        topList = (ListView) findViewById(R.id.list);

        topAdapter = new StoryListAdapter(topList.getContext(), R.layout.list_item, stories);
        //adapter = new ArrayAdapter(this, R.layout.item_simple_text, some);
        topList.setAdapter(topAdapter);
        //get_stories_array(topStories);
        doSomethingPlease(topStories, topAdapter);

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

        registerForContextMenu(topList);
        progressDialog.dismiss();
    }

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

/*        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void browser1(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
    private void load_article_title(String id, final int pos) {
        RequestQueue requestQueue = Volley.newRequestQueue(topList.getContext());
        String myUrl = title_url + id + ".json";
        CustomJSONObjectRequest request = new CustomJSONObjectRequest
                (Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //This is where you get the data from the stored JSON object.
                        try {
                            Story story = new Story();
                            story.title = response.getString("title");
                            story.by = response.getString("by");
                            story.score = Integer.toString(pos);
                            stories.add(story);
                            topAdapter.notifyDataSetChanged();


                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Could not get data", Toast.LENGTH_SHORT).show();
                    }
                });

//        request.setPriority(Request.Priority.HIGH);
//        getter.add(request);
        requestQueue.add(request);
    }

    private void get_stories_array(final String urlstories) {

        RequestQueue requestQueue = Volley.newRequestQueue(topList.getContext());
        CustomJSONArrayRequest request = new CustomJSONArrayRequest
                (Request.Method.GET, urlstories, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        //newStories and topStories return up to 500 ids, so checking if that is the current type
 /*                       int top_bound = 200;
                        if (urlstories.equals("newStories") || urlstories.equals("topStories")) {
                            top_bound = 500;
                        }*/

                        String[] title_id_list = new String[response.length()];
                        //This is where you get the data from the stored JSON object.


                        try {
                            for(int x = 0; x < response.length(); x++) {
                                title_id_list[x] = response.getString(x);
                            }
                            for(int y = 0; y < response.length(); y++){
                                load_article_title(title_id_list[y],y);
                            }
                            topAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "ErrorArr", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Could not get data", Toast.LENGTH_SHORT).show();
                    }
                });

//        request.setPriority(Request.Priority.HIGH);
//        getter.add(request);
        requestQueue.add(request);
    }

    private void doSomethingPlease(String source, final StoryListAdapter listAdapter){
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, source, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for (int i=0;i<response.length()/10;i++){
                        nowMakeMeHappy(response.getString(i), listAdapter);
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
        rq.add(jsonArrayRequest);
    }

    private void nowMakeMeHappy(String id, final StoryListAdapter listAdapter){
        String uri = title_url + id + ".json";
        RequestQueue rq = Volley.newRequestQueue(this);
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
        rq.add(jsonObjectRequest);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Story obj = (Story) lv.getItemAtPosition(acmi.position);

            /*menu.setHeaderTitle(obj.title);
            menu.add(obj.by);
            menu.add(obj.score);*/

            LayoutInflater layoutInflater = this.getLayoutInflater();
            View header = layoutInflater.inflate(R.layout.list_item, null);

            TextView title = (TextView) header.findViewById(R.id.title);
            TextView by = (TextView) header.findViewById(R.id.by);
            title.setText(obj.title);
            by.setText(obj.by);


            menu.setHeaderView(header);
            menu.add("View Comments");
            menu.add("Upvote");

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main,menu);
        }
    }
}