package com.volleytutorial.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.volleytutorial.app.R;
import com.volleytutorial.app.model.YouTubeModel;
import com.volleytutorial.app.request.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by R4D3K on 2014-06-01.
 */
public class MainAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private final Context mContext;
    private final Listener mListener;
    private ArrayList<YouTubeModel> mList;
    private boolean mIsLoading = false;
    private int mPrevItemCount = 0;
    private int mIndex = 0;

    public MainAdapter(Context context) {
        this.mContext = context;
        this.mListener = (Listener) context;
        this.mList = new ArrayList<YouTubeModel>();
    }

    public void setData(List<YouTubeModel> youTubeModelList) {
        mList.addAll(youTubeModelList);
        notifyDataSetChanged();
        mIsLoading = false;
    }

    public ArrayList<YouTubeModel> getData() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_main, parent, false);
            ViewHolder holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.textview_listview_main_title),
                    (NetworkImageView) convertView.findViewById(R.id.imageview_listview_main_thumb)
            );
            convertView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        String title = mList.get(position).getTitle();
        holder.title.setText(title);
        holder.thumb.setDefaultImageResId(R.drawable.abc_ic_search_api_holo_light);
        holder.thumb.setImageUrl(mList.get(position).getThumbSmall(), VolleySingleton.getInstance().getImageLoader());

        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int visible, int visibleCount, int totalCount) {

        if(!mIsLoading && totalCount <= (visible + visibleCount)) {
            android.util.Log.d("adapter", "LOAD DATA ! mIsLoading= " + mIsLoading + " totalCount= " + totalCount + " visibleCount= " + visibleCount + " visible= " + visible);
            mIndex+=5;
            mListener.onLoadMoreData(mIndex);
            mIsLoading = true;
        }
    }

    public interface Listener {
        public void onLoadMoreData(int index);
    }

    private class ViewHolder {
        public TextView title;
        public NetworkImageView thumb;

        public ViewHolder(TextView title, NetworkImageView thumb) {
            this.title = title;
            this.thumb = thumb;
        }
    }
}
