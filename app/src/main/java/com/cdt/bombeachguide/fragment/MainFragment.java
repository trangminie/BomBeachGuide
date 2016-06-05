package com.cdt.bombeachguide.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cdt.bombeachguide.ListVideoActivity;
import com.cdt.bombeachguide.R;
import com.cdt.bombeachguide.VideoActivity;
import com.cdt.bombeachguide.VideoFragmentInterface;
import com.cdt.bombeachguide.pojo.IntroduceItem;
import com.cdt.bombeachguide.pojo.VideoItem;
import com.cdt.bombeachguide.ui.HorizontalListView;
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
 * Created by Trang on 5/21/2016.
 */
public class MainFragment extends Fragment implements VideoFragmentInterface{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<IntroduceItem> mIntroduceItemsList;
    private AssetManager assetManager;

    private HorizontalListView mHeavyListView;
    private ProgressBar mHeavyProgressBar;
    private Handler mHeavyHandler;
    private CustomArrayAdapter<VideoItem> mHeavyAdapter;
    private TextView mHeavyTextView;

    private HorizontalListView mZokaListView;
    private ProgressBar mZokaProgressBar;
    private Handler mZokaHandler;
    private CustomArrayAdapter<VideoItem> mZokaAdapter;
    private TextView mZokaTextView;

    private HorizontalListView mTankListView;
    private ProgressBar mTankProgressBar;
    private Handler mTankHandler;
    private CustomArrayAdapter<VideoItem> mTankAdapter;
    private TextView mTankTextView;


    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mIntroduceItemsList = new ArrayList<>();
//        mIntroduceItemsList.add(new IntroduceItem(null, "Video"));
        mIntroduceItemsList.add(new IntroduceItem(null, "Troops"));
        mIntroduceItemsList.add(new IntroduceItem(null, "Artifacts"));
        mRecyclerView.setAdapter(new ImageAdapter(mIntroduceItemsList));

        assetManager = getActivity().getAssets();

        mHeavyListView = (HorizontalListView) rootView.findViewById(R.id.video_heavy);
        mHeavyProgressBar = (ProgressBar) rootView.findViewById(R.id.heavy_progressbar);
        mHeavyTextView = (TextView) rootView.findViewById(R.id.list_video_heavy);
        createListHeavy();

        mZokaListView = (HorizontalListView) rootView.findViewById(R.id.video_zoka);
        mZokaProgressBar = (ProgressBar) rootView.findViewById(R.id.zoka_progressbar);
        mZokaTextView = (TextView) rootView.findViewById(R.id.list_video_zoka);
        createListZoka();

        mTankListView = (HorizontalListView) rootView.findViewById(R.id.video_tank);
        mTankProgressBar = (ProgressBar) rootView.findViewById(R.id.tank_progressbar);
        mTankTextView = (TextView) rootView.findViewById(R.id.list_video_tank);
        createListTank();

        addClickListernerForListVideo();
        return rootView;
    }

    private void createListHeavy(){
        mHeavyHandler = new Handler();
        addClickListenerForVideo(VideoFragment.LIST_VIDEO_HEAVY);

        String mFolderName = "Hea_lyrics.txt";
        List<String> listUrls = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(mFolderName)));
            String line;
            while( (line = reader.readLine()) != null){
                listUrls.add("https://www.youtube.com/watch?v=" + line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mHeavyAdapter = new CustomArrayAdapter<VideoItem>(getActivity()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.horizontal_video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                title.setSelected(true);
                VideoItem searchResult = getItem(position);

                Picasso.with(getActivity())
                        .load(searchResult.getThumbnailURL())
                        .resize(250, 200)
                        .centerCrop()
                        .into(thumbnail);
                title.setText(searchResult.getTitle());
                return convertView;
            }
        };
        mHeavyListView.setAdapter(mHeavyAdapter);
        searchOnYoutube(listUrls, VideoFragment.LIST_VIDEO_HEAVY);
    }

    private void createListZoka(){
        mZokaHandler = new Handler();
        addClickListenerForVideo(VideoFragment.LIST_VIDEO_ZOKA);

        String mFolderName = "Zo_lyrics.txt";
        List<String> listUrls = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(mFolderName)));
            String line;
            while( (line = reader.readLine()) != null){
                listUrls.add("https://www.youtube.com/watch?v=" + line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mZokaAdapter = new CustomArrayAdapter<VideoItem>(getActivity()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.horizontal_video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                title.setSelected(true);
                VideoItem searchResult = getItem(position);

                Picasso.with(getActivity())
                        .load(searchResult.getThumbnailURL())
                        .resize(250, 200)
                        .centerCrop()
                        .into(thumbnail);
                title.setText(searchResult.getTitle());
                return convertView;
            }
        };
        mZokaListView.setAdapter(mZokaAdapter);
        searchOnYoutube(listUrls, VideoFragment.LIST_VIDEO_ZOKA);
    }

    private void createListTank(){
        mTankHandler = new Handler();
        addClickListenerForVideo(VideoFragment.LIST_VIDEO_TANK);

        String mFolderName = "Tan_lyrics.txt";
        List<String> listUrls = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(mFolderName)));
            String line;
            while( (line = reader.readLine()) != null){
                listUrls.add("https://www.youtube.com/watch?v=" + line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mTankAdapter = new CustomArrayAdapter<VideoItem>(getActivity()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.horizontal_video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                title.setSelected(true);
                VideoItem searchResult = getItem(position);

                Picasso.with(getActivity())
                        .load(searchResult.getThumbnailURL())
                        .resize(250, 200)
                        .centerCrop()
                        .into(thumbnail);
                title.setText(searchResult.getTitle());
                return convertView;
            }
        };
        mTankListView.setAdapter(mTankAdapter);
        searchOnYoutube(listUrls, VideoFragment.LIST_VIDEO_TANK);
    }

    private class ImageHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private ImageView mImageView;
        private TextView mTextView;
        private int position;
        private IntroduceItem mIntroduceItem;


        public ImageHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.introduce_item, parent, false));

            mImageView = (ImageView) itemView.findViewById(R.id.img_intro);
            mImageView.setOnClickListener(this);

            mTextView = (TextView) itemView.findViewById(R.id.txt_intro_name);
        }

        public void bindImage(IntroduceItem item, int pos){
            mIntroduceItem = item;
            position = pos;
            mTextView.setText(item.getImageName());
        }

        @Override
        public void onClick(View view) {
            Intent intent = VideoActivity.newIntent(getContext());
            startActivity(intent);
        }
    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageHolder>{
        private List<IntroduceItem> mIntroduceItems;

        public ImageAdapter(List<IntroduceItem> listItems) {
            mIntroduceItems = listItems;
        }


        @Override
        public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ImageHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(ImageHolder imageHolder, int position) {
            IntroduceItem item = mIntroduceItems.get(position);
            imageHolder.bindImage(item, position);
        }

        @Override
        public int getItemCount() {
            return mIntroduceItems.size();
        }
    }

    private void searchOnYoutube(final List<String> listUrl, final int videoType){
        new Thread(){
            public void run(){
                YoutubeConnector yc = new YoutubeConnector(getActivity(), videoType);
                yc.searchVideoFromUrl(listUrl, MainFragment.this);
            }
        }.start();
    }

    public void addVideoItems(final Collection<VideoItem> videoItems, int videoType){
        switch (videoType){
            case VideoFragment.LIST_VIDEO_HEAVY:
                mHeavyHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mHeavyProgressBar.setVisibility(View.GONE);
                        mHeavyAdapter.addItems(videoItems);
                    }
                });
                break;
        }

    }

    private void addClickListenerForVideo(int videoType){
        switch (videoType){
            case VideoFragment.LIST_VIDEO_HEAVY:
                mHeavyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> av, View v, int pos,
                                            long id) {
                        Intent intent = new Intent(getActivity(), PlayerActivity.class);
                        intent.putExtra("VIDEO_ID", ((VideoItem)av.getItemAtPosition(pos)).getId());
                        startActivity(intent);
                    }

                });
                break;
        }

    }

    private void addClickListernerForListVideo(){
        mHeavyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ListVideoActivity.newIntent(getContext(), VideoFragment.LIST_VIDEO_HEAVY);
                startActivity(intent);
            }
        });

        mZokaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ListVideoActivity.newIntent(getContext(), VideoFragment.LIST_VIDEO_ZOKA);
                startActivity(intent);
            }
        });

        mTankTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ListVideoActivity.newIntent(getContext(), VideoFragment.LIST_VIDEO_TANK);
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
