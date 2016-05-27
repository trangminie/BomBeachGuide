package com.cdt.bombeachguide.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cdt.bombeachguide.R;
import com.cdt.bombeachguide.VideoActivity;

/**
 * Created by Trang on 5/21/2016.
 */
public class MainFragment extends Fragment {
    private Context context;

    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        ImageView img_videos = (ImageView)rootView.findViewById(R.id.img_videos);
        ImageView img_troops = (ImageView)rootView.findViewById(R.id.img_troops);
        ImageView img_tuong = (ImageView)rootView.findViewById(R.id.img_tuong);
        img_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videosIntent = VideoActivity.newIntent(getActivity());
                startActivity(videosIntent);
            }
        });



        return rootView;
    }
}
