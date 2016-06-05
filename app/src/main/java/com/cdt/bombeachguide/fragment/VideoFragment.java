package com.cdt.bombeachguide.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cdt.bombeachguide.R;

/**
 * Created by Trang on 5/19/2016.
 */
public class VideoFragment extends BaseFragment {

    public static final int LIST_VIDEO_HEAVY = 1;
    public static final int LIST_VIDEO_ZOKA = 2;
    public static final int LIST_VIDEO_TANK = 3;

    private CardView mHeavyCardView;
    private CardView mZokaCardView;
    private CardView mTankCardView;

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

        mZokaCardView = (CardView) rootView.findViewById(R.id.zoka_view);
        mZokaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseListVideo(LIST_VIDEO_ZOKA);
            }
        });

        mTankCardView = (CardView) rootView.findViewById(R.id.tank_view);
        mTankCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseListVideo(LIST_VIDEO_TANK);
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
