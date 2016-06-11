package com.cdt.bombeachguide.inter;

import com.cdt.bombeachguide.pojo.VideoItem;

import java.util.ArrayList;

/**
 * Created by Trang on 6/4/2016.
 */
public interface VideoFragmentInterface {

    public void addVideoItems(ArrayList<VideoItem> videoItems, int videoType);
}
