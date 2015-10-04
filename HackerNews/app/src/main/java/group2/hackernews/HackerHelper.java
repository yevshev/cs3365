package group2.hackernews;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class HackerHelper extends Application {
    private RequestQueue mRequestQueue;
    private static HackerHelper mHackerHelper;


    public static final String TAG = HackerHelper.class.getName();
    @Override
    public void onCreate(){
        super.onCreate();
        mHackerHelper = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static synchronized HackerHelper getInstance(){
        return mHackerHelper;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancel() {
        mRequestQueue.cancelAll(TAG);
    }
}

