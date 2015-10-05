package group2.hackernews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    TextView TopList;
    HackerHelper getter = HackerHelper.getInstance();

    String title_url = "https://hacker-news.firebaseio.com/v0/item/";


    final static String topStories = "https://hacker-news.firebaseio.com/v0/topstories.json";
    final static String askStories = "https://hacker-news.firebaseio.com/v0/askstories.json";
    final static String jobStories = "https://hacker-news.firebaseio.com/v0/jobstories.json";
    final static String newStories = "https://hacker-news.firebaseio.com/v0/jobstories.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TopList = (TextView) findViewById(R.id.words);
        get_topstories_array();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void load_article_title(String id) {
        String myUrl = title_url + id + ".json";
        CustomJSONObjectRequest request = new CustomJSONObjectRequest
                (Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //This is where you get the data from the stored JSON object.
                        try {
                            String title;
                            title = response.getString("title");
                            TopList.setText(title);

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

        request.setPriority(Request.Priority.HIGH);
        getter.add(request);
    }

    private void get_topstories_array() {
        CustomJSONArrayRequest request = new CustomJSONArrayRequest
                (Request.Method.GET, topStories, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        String[] title_id_list = new String[500];
                        //This is where you get the data from the stored JSON object.
                        try {
                            for(int x = 0; x < 500; x++) {
                                title_id_list[x] = response.getString(x);
                            }
                            load_article_title(title_id_list[0]);

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

        request.setPriority(Request.Priority.HIGH);
        getter.add(request);
    }

    private void get_jobstories_array() {
        CustomJSONArrayRequest request = new CustomJSONArrayRequest
                (Request.Method.GET, jobStories, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        String[] title_id_list = new String[200];
                        //This is where you get the data from the stored JSON object.
                        try {
                            for(int x = 0; x < 200; x++) {
                                title_id_list[x] = response.getString(x);
                            }
                            load_article_title(title_id_list[0]);

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

        request.setPriority(Request.Priority.HIGH);
        getter.add(request);
    }

    private void get_askstories_array() {
        CustomJSONArrayRequest request = new CustomJSONArrayRequest
                (Request.Method.GET, askStories, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        String[] title_id_list = new String[200];
                        //This is where you get the data from the stored JSON object.
                        try {
                            for(int x = 0; x < 200; x++) {
                                title_id_list[x] = response.getString(x);
                            }
                            load_article_title(title_id_list[0]);

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

        request.setPriority(Request.Priority.HIGH);
        getter.add(request);
    }

    private void get_newstories_array() {
        CustomJSONArrayRequest request = new CustomJSONArrayRequest
                (Request.Method.GET, newStories, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        String[] title_id_list = new String[500];
                        //This is where you get the data from the stored JSON object.
                        try {
                            for(int x = 0; x < 500; x++) {
                                title_id_list[x] = response.getString(x);
                            }
                            load_article_title(title_id_list[0]);

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

        request.setPriority(Request.Priority.HIGH);
        getter.add(request);
    }

 /*   private void filter_page(String filter) {
        if (filter.equals("job")) {
            get_jobstories_array();
        }
        if (filter.equals("poll")) {
            get_askstories_array();
        }
        if (filter.equals(""))
    } */
}
