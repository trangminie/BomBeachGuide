package com.cdt.bombeachguide;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.cdt.bombeachguide.SingleFragmentActivity;
import com.cdt.bombeachguide.fragment.ListVideoFragment;

/**
 * Created by Trang on 5/21/2016.
 */
public class ListVideoActivity extends SingleFragmentActivity {

    private static final String EXTRA_TYPE_VIDEO =
            "com.cdt.bombeachguide.type_video";

    @Override
    protected Fragment createFragment() {
        int typeVideo = (Integer) getIntent().getSerializableExtra(EXTRA_TYPE_VIDEO);
        return ListVideoFragment.newInstance(typeVideo);
    }

    public static Intent newIntent(Context packageContext, int typeVideo) {
        Intent intent = new Intent(packageContext, ListVideoActivity.class);
        intent.putExtra(EXTRA_TYPE_VIDEO, typeVideo);
        return intent;
    }
}
