package com.cdt.bombeachguide;

import android.support.v4.app.Fragment;

import com.cdt.bombeachguide.fragment.VideoFragment;

/**
 * Created by Trang on 5/19/2016.
 */
public class VideoActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return VideoFragment.newInstance();
    }
}
