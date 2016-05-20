package com.cdt.bombeachguide.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cdt.bombeachguide.R;
import com.cdt.bombeachguide.pojo.VideoItem;
import com.cdt.bombeachguide.youtube.PlayerActivity;
import com.cdt.bombeachguide.youtube.YoutubeConnector;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Trang on 5/19/2016.
 */
public class VideoFragment extends Fragment {

    private ListView youtubeListView;
    private ProgressBar progressBar;
    private Handler handler;
    private List<String> listUrls;
    private CustomArrayAdapter<VideoItem> adapter;

    public static VideoFragment newInstance(){
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_fragment, container, false);
        youtubeListView = (ListView) rootView.findViewById(R.id.list_youtube);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        handler = new Handler();

        addClickListener();

        listUrls = new ArrayList<String>();
        listUrls.add("https://www.youtube.com/watch?v=CC3yIE_7IkU");
//        AssetManager assetManager = getActivity().getAssets();
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("lyrics.txt")));
//            String line;
//            while( (line = reader.readLine()) != null){
//                listUrls.add("https://www.youtube.com/watch?v=" + line.trim());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
        youtubeListView.setAdapter(adapter);
        searchOnYoutube(listUrls);

        return rootView;
    }


    private void searchOnYoutube(final List<String> listUrl){
        new Thread(){
            public void run(){
                YoutubeConnector yc = new YoutubeConnector(getActivity());
                yc.searchVideoFromUrl(listUrl, VideoFragment.this);
            }
        }.start();
    }

    public void addVideoItems(final Collection<VideoItem> videoItems){
        handler.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                adapter.addItems(videoItems);
            }
        });
    }

    private void addClickListener(){
        youtubeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                intent.putExtra("VIDEO_ID", ((VideoItem)av.getItemAtPosition(pos)).getId());
                startActivity(intent);
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
