package com.cdt.bombeachguide.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cdt.bombeachguide.R;
import com.cdt.bombeachguide.VideoActivity;

/**
 * Created by Trang on 5/21/2016.
 */
public class MainFragment extends Fragment {

    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        Button btnVideo = (Button) rootView.findViewById(R.id.btn_video);
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = VideoActivity.newIntent(getContext());
                startActivity(intent);

            }
        });
        return rootView;
    }
}
