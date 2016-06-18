package com.cdt.bombeachguide.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cdt.bombeachguide.HTTP.HTTPProcess;
import com.cdt.bombeachguide.R;
import com.cdt.bombeachguide.WebViewActivity;
import com.cdt.bombeachguide.inter.VideoFragmentInterface;
import com.cdt.bombeachguide.pojo.VideoItem;
import com.cdt.bombeachguide.youtube.PlayerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Trang on 5/21/2016.
 */
public class ListItemFragment extends BaseFragment implements VideoFragmentInterface{

    private ListView mItemListView;
    private ProgressBar progressBar;
    private Handler handler;

    private CustomArrayAdapter<VideoItem> adapter;

    private static String mTitle;
    private static String mUrl;

    public static ListItemFragment newInstance(String title,String url){


        ListItemFragment fragment = new ListItemFragment();
        mTitle = title;
        mUrl = url;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_video_fragment, container, false);
        mItemListView = (ListView) rootView.findViewById(R.id.list_youtube);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        handler = new Handler();
        setTitle(mTitle);
        addClickListener();
        AssetManager assetManager = getActivity().getAssets();
        adapter = new CustomArrayAdapter<VideoItem>(getActivity()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);

                VideoItem searchResult = getItem(position);

                Picasso.with(getActivity()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                return convertView;
            }
        };
        mItemListView.setAdapter(adapter);
        searchOnWebsite(mUrl);
        getMainActivity().showDrawerAsDrawer();
        return rootView;
    }


//    private void searchOnYoutube(final List<String> listUrl){
//        new Thread(){
//            public void run(){
//                YoutubeConnector yc = new YoutubeConnector(getActivity(), mVideoType);
//                yc.searchVideoFromUrl(listUrl, ListItemFragment.this);
//            }
//        }.start();
//    }
    private void searchOnWebsite(String url){
        new Thread(){
            public void run(){
                HTTPProcess httpProcess = new HTTPProcess();
                httpProcess.searchListItemsFromUrl(mUrl,ListItemFragment.this);
            }
        }.start();
    }
    public void addVideoItems(final ArrayList<VideoItem> videoItems, int videoType){
        handler.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                adapter.addItems(videoItems);
            }
        });
    }

    private void addClickListener(){
        mItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
//                Intent intent = new Intent(getActivity(), PlayerActivity.class);
//                intent.putExtra("VIDEO_ID", ((VideoItem)av.getItemAtPosition(pos)).getId());
//                startActivity(intent);
                String url="http://boombeach.wikia.com/wiki/"+((VideoItem)av.getItemAtPosition(pos)).getTitle();
                if (!mTitle.equals("Buildings")) {

                    WebViewFragment webviewfragment = WebViewFragment.newInstance(((VideoItem)av.getItemAtPosition(pos)).getTitle(),url);
                    displayDetail(webviewfragment);
                }else{

                    ListItemFragment listVideoFragment = ListItemFragment.newInstance(((VideoItem)av.getItemAtPosition(pos)).getTitle(),url);
                    displayDetail(listVideoFragment);
                }

            }

        });
    }



    private static abstract class CustomArrayAdapter<T> extends BaseAdapter {

        private ArrayList<T> mData = new ArrayList<T>();
        private LayoutInflater mInflater;

        public CustomArrayAdapter(Activity activity) {
            mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItems(final Collection<T> items) {
            mData.addAll(items);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public T getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }
}
