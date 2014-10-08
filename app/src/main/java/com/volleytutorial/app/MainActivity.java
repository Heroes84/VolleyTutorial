package com.volleytutorial.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.volleytutorial.app.adapter.MainAdapter;
import com.volleytutorial.app.model.YouTubeModel;
import com.volleytutorial.app.request.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements Response.Listener<JSONObject>, Response.ErrorListener, MainAdapter.Listener {

    private static final String TAG = "VOLLEY";
    private static final String BUNDLE_LISTDATA = "listviewdata";
    private static String URL_ALL = "https://gdata.youtube.com/feeds/api/videos?v=2&alt=jsonc&q=Starcraft&start-index=%d";
    private ListView mListView;
    private RequestQueue mRequestQueue;
    private ArrayList mYouTubeList;
    private View mListViewFooter;
    private MainAdapter mMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listview_main);
        mListView.setEmptyView(findViewById(R.id.listview_empty_main));
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mListViewFooter = inflater.inflate(R.layout.listview_main_footer, null, false);
        mListView.addFooterView(mListViewFooter);
        mMainAdapter = new MainAdapter(this);
        if(savedInstanceState != null && savedInstanceState.get(BUNDLE_LISTDATA) != null) {
            ArrayList<YouTubeModel> data = savedInstanceState.getParcelableArrayList(BUNDLE_LISTDATA);
            mMainAdapter.setData(data);
        }
        mListView.setOnScrollListener(mMainAdapter);
        mListView.setAdapter(mMainAdapter);

        if(savedInstanceState == null) {
            getNetworkData(1);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BUNDLE_LISTDATA, mMainAdapter.getData());
    }

    private void getNetworkData(int index) {
        mListView.addFooterView(mListViewFooter);
        mMainAdapter.notifyDataSetChanged();
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, String.format(URL_ALL, index), null, this, this);
        mRequestQueue.add(jsonRequest);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        TextView textView = (TextView) findViewById(R.id.textview_listview_empty_title);
        textView.setText(R.string.listview_main_empty_title_error);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        JSONObject jsonData = jsonObject.optJSONObject("data");
        mYouTubeList = new ArrayList<YouTubeModel>();
        if(jsonData != null) {
            JSONArray jsonItems = jsonData.optJSONArray("items");
            if(jsonItems != null) {
                for(int i=0; i<jsonItems.length(); i++) {
                    JSONObject jsonItem = jsonItems.optJSONObject(i);
                    if(jsonItem != null) {
                        JSONObject jsonItemThumbnail = jsonItem.optJSONObject("thumbnail");
                        String title = jsonItem.optString("title", "Brak tytułu");
                        String thumbnail = jsonItemThumbnail.optString("sqDefault", "");
                        mYouTubeList.add(new YouTubeModel(title, thumbnail));
                    }
                }

                mListView.removeFooterView(mListViewFooter);
                mMainAdapter.setData(mYouTubeList);
            }
        }
    }

    @Override
    public void onLoadMoreData(int index) {
        getNetworkData(index);
    }
}
