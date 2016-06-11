package com.cdt.bombeachguide.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdt.bombeachguide.R;

/**
 * Created by Trang on 5/19/2016.
 */
public class VideoFragment extends BaseFragment {

    public static final int LIST_VIDEO_HEAVY = 1;
    public static final int LIST_VIDEO_BoombeachWiki = 2;
    public static final int LIST_VIDEO_StrategyBlog = 3;

    private CardView mHeavyCardView;
    private CardView mBoombeachWikiCardView;
    private CardView mStrategyBlogCardView;

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
        setTitle("Video");

        mHeavyCardView = (CardView) rootView.findViewById(R.id.heavy_view);
        mHeavyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseListVideo(LIST_VIDEO_HEAVY);
            }
        });

        mBoombeachWikiCardView = (CardView) rootView.findViewById(R.id.BoombeachWiki_view);
        mBoombeachWikiCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseListVideo(LIST_VIDEO_BoombeachWiki);
            }
        });

        mStrategyBlogCardView = (CardView) rootView.findViewById(R.id.StrategyBlog_view);
        mStrategyBlogCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseListVideo(LIST_VIDEO_StrategyBlog);
            }
        });

        getMainActivity().showDrawerAsDrawer();
        return rootView;
    }

    private void chooseListVideo(int videoType){
        ListVideoFragment fragment = ListVideoFragment.newInstance(videoType);
        displayDetail(fragment);
    }




}
