package com.cdt.bombeachguide.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cdt.bombeachguide.ListVideoActivity;
import com.cdt.bombeachguide.R;

/**
 * Created by Trang on 5/19/2016.
 */
public class VideoFragment extends Fragment {

    public static final int LIST_VIEO_1 = 1;
    public static final int LIST_VIEO_2 = 2;
    public static final int LIST_VIEO_3 = 3;


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

        Button btn1 = (Button) rootView.findViewById(R.id.btn_video_1);
        Button btn2 = (Button) rootView.findViewById(R.id.btn_video_2);
        Button btn3 = (Button) rootView.findViewById(R.id.btn_video_3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseListVideo(LIST_VIEO_1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseListVideo(LIST_VIEO_2);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseListVideo(LIST_VIEO_3);
            }
        });

        return rootView;
    }

    private void chooseListVideo(int listVideo){
        Intent intent = ListVideoActivity.newIntent(getContext(), listVideo);
        startActivity(intent);
    }


}
