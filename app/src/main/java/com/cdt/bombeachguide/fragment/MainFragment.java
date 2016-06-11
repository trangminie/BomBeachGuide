package com.cdt.bombeachguide.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cdt.bombeachguide.HTTP.HTTPProcess;
import com.cdt.bombeachguide.R;
import com.cdt.bombeachguide.inter.VideoFragmentInterface;
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
public class MainFragment extends BaseFragment implements VideoFragmentInterface{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<IntroduceItem> mIntroduceItemsList;
    private AssetManager assetManager;

    private TextView mVideoTextView;

    private HorizontalListView mHeavyListView;
    private ProgressBar mHeavyProgressBar;
    private Handler mHeavyHandler;
    private CustomArrayAdapter<VideoItem> mHeavyAdapter;
    private TextView mHeavyTextView;

    private LinearLayout mBoombeachWikiListView;
    private ProgressBar mBoombeachWikiProgressBar;
    private Handler mBoombeachWikiHandler;
    private CustomArrayAdapter<VideoItem> mBoombeachWikiAdapter;
    private ArrayList<VideoItem> mListBoombeachWiki=new ArrayList<>();
    private TextView mBoombeachWikiTextView;

    private LinearLayout mStrategyBlogListView;
    private ProgressBar mStrategyBlogProgressBar;
    private Handler mStrategyBlogHandler;
    private CustomArrayAdapter<VideoItem> mStrategyBlogAdapter;
    private ArrayList<VideoItem> mListStrategyBlog=new ArrayList<>();
    private TextView mStrategyBlogTextView;

    private ImageView img_HomeBanner;

    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        setTitle("Home");
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mContext=getActivity();

        img_HomeBanner=(ImageView)rootView.findViewById(R.id.imageView_homeBanner);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Picasso.with(getActivity())
                        .load("http://vignette4.wikia.nocookie.net/boombeach/images/2/2e/MainPageBanner.png/revision/latest/scale-to-width-down/650?cb=20160327220846")
                        .fit()
                        .into(img_HomeBanner);
            }
        });
        mIntroduceItemsList = new ArrayList<>();
//        mIntroduceItemsList.add(new IntroduceItem(null, "Video"));
        mIntroduceItemsList.add(new IntroduceItem(null, "Troops"));
        mIntroduceItemsList.add(new IntroduceItem(null, "Artifacts"));
        mIntroduceItemsList.add(new IntroduceItem(null, "Artifacts"));
        mRecyclerView.setAdapter(new ImageAdapter(mIntroduceItemsList));

        assetManager = getActivity().getAssets();
        mVideoTextView = (TextView) rootView.findViewById(R.id.txt_video);

        mHeavyListView = (HorizontalListView) rootView.findViewById(R.id.video_heavy);
        mHeavyProgressBar = (ProgressBar) rootView.findViewById(R.id.heavy_progressbar);
        mHeavyTextView = (TextView) rootView.findViewById(R.id.list_video_heavy);
        createListHeavy();

        mBoombeachWikiListView = (LinearLayout) rootView.findViewById(R.id.list_item_boombeach);
        mBoombeachWikiProgressBar = (ProgressBar) rootView.findViewById(R.id.BoombeachWiki_progressbar);
        mBoombeachWikiTextView = (TextView) rootView.findViewById(R.id.tv_boombeachwiki);

        createListBoombeachWiki();

        mStrategyBlogListView = (LinearLayout) rootView.findViewById(R.id.list_item_strategy_blog);
        mStrategyBlogProgressBar = (ProgressBar) rootView.findViewById(R.id.StrategyBlog_progressbar);
        mStrategyBlogTextView = (TextView) rootView.findViewById(R.id.list_video_StrategyBlog);
        createListStrategyBlog();

        searchOnWebsite("http://boombeach.wikia.com/wiki/Boom_Beach_Wiki");
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

    private void createListBoombeachWiki(){
        mBoombeachWikiHandler = new Handler();
        addClickListenerForVideo(VideoFragment.LIST_VIDEO_BoombeachWiki);

 /*       String mFolderName = "Zo_lyrics.txt";
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

        mBoombeachWikiAdapter = new CustomArrayAdapter<VideoItem>(getActivity()){
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
                        .fit()
                        .into(thumbnail);
                title.setText(searchResult.getTitle());
                return convertView;
            }
        };
        mBoombeachWikiListView.setAdapter(mBoombeachWikiAdapter);*/
        searchOnWebsite("http://boombeach.wikia.com/wiki/Boom_Beach_Wiki");


    }

    private void createListStrategyBlog(){
        mStrategyBlogHandler = new Handler();
        addClickListenerForVideo(VideoFragment.LIST_VIDEO_StrategyBlog);

/*        String mFolderName = "Tan_lyrics.txt";
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

        mStrategyBlogAdapter = new CustomArrayAdapter<VideoItem>(getActivity()){
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
        mStrategyBlogListView.setAdapter(mStrategyBlogAdapter);*/
     //   searchOnYoutube(listUrls, VideoFragment.LIST_VIDEO_StrategyBlog);
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
            VideoFragment videoFragment = VideoFragment.newInstance();
            displayDetail(videoFragment);
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
    private void searchOnWebsite(final String Url){
        new Thread(){
            public void run(){
                HTTPProcess httpProcess=new HTTPProcess();
                httpProcess.getListItemBoomBeachWiki(Url, MainFragment.this);
            }
        }.start();
    }

    public void addVideoItems(final ArrayList<VideoItem> videoItems, int videoType){
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

            case VideoFragment.LIST_VIDEO_BoombeachWiki:
                mBoombeachWikiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBoombeachWikiProgressBar.setVisibility(View.GONE);
                      //  mBoombeachWikiAdapter.addItems(videoItems);
                    //    mListBoombeachWiki = videoItems;

                        for (final VideoItem item:videoItems){
                            View v=insertPhoto(item);
                            mBoombeachWikiListView.addView(v);
                            v.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getContext(),item.getUrl(),Toast.LENGTH_LONG).show();

                                    ListItemFragment listVideoFragment = ListItemFragment.newInstance(item.getUrl());
                                    displayDetail(listVideoFragment);
                                }
                            });

                        }
                    }
                });
                break;

            case VideoFragment.LIST_VIDEO_StrategyBlog:
                mStrategyBlogHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mStrategyBlogProgressBar.setVisibility(View.GONE);
                       // mStrategyBlogAdapter.addItems(videoItems);
                        for (final VideoItem item:videoItems){
                            View v=insertPhoto(item);
                            mStrategyBlogListView.addView(v);
                            v.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getContext(),item.getUrl(),Toast.LENGTH_LONG).show();
                                    ListItemFragment listVideoFragment = ListItemFragment.newInstance(item.getUrl());
                                    displayDetail(listVideoFragment);
                                }
                            });
                        }
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
            case VideoFragment.LIST_VIDEO_StrategyBlog:

            //    mStrategyBlogListView.setOnClickListener();
            /*   mStrategyBlogListView.setOnClickListener(new View.OnClickListener() {

                    @Override
                   public void onItemClick(AdapterView<?> av, View v, int pos,
                                            long id) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("link", ((VideoItem)av.getItemAtPosition(pos)).getUrl());
                        startActivity(intent);
                    }

                });*/
                break;
            case VideoFragment.LIST_VIDEO_BoombeachWiki:
/*                mBoombeachWikiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> av, View v, int pos,
                                            long id) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("link", ((VideoItem)av.getItemAtPosition(pos)).getUrl());
                        startActivity(intent);
                    }

                });*/
                break;
        }

    }

    private void addClickListernerForListVideo(){
        mVideoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoFragment videoFragment = VideoFragment.newInstance();
                displayDetail(videoFragment);
            }
        });
        mHeavyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListVideoFragment listVideoFragment = ListVideoFragment.newInstance(VideoFragment.LIST_VIDEO_HEAVY);
                displayDetail(listVideoFragment);
            }
        });

        mBoombeachWikiTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListVideoFragment listVideoFragment = ListVideoFragment.newInstance(VideoFragment.LIST_VIDEO_BoombeachWiki);
               displayDetail(listVideoFragment);


            }
        });

        mStrategyBlogTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListVideoFragment listVideoFragment = ListVideoFragment.newInstance(VideoFragment.LIST_VIDEO_StrategyBlog);
                displayDetail(listVideoFragment);
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
    View insertPhoto(VideoItem item){
        //Bitmap bm = decodeSampledBitmapFromUri(path, 220, 220);
        View child = getActivity().getLayoutInflater().inflate(R.layout.horizontal_video_item, null);
       TextView title = (TextView)child.findViewById(R.id.video_title);
        title.setText(item.getTitle());

        ImageView imageView = (ImageView)child.findViewById(R.id.video_thumbnail);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(getActivity())
                .load(item.getThumbnailURL())
                .resize(250, 200)
                .centerCrop()
                .into(imageView);


        return child;
    }
}
