package group2.hackernews;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by david on 10/4/15.
 */
public class CustomJSONArrayRequest extends JsonArrayRequest
{
    public CustomJSONArrayRequest(int method, String url, JSONArray jsonRequest,
                                   Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    private Request.Priority mPriority;

    public void setPriority(Request.Priority priority) {
        mPriority = priority;
    }

    @Override
    public Request.Priority getPriority() {
        return mPriority == null ? Request.Priority.NORMAL : mPriority;
    }
}
