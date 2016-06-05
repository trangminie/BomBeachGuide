package com.cdt.bombeachguide.inter;

import com.cdt.bombeachguide.pojo.VideoItem;

import java.util.Collection;

/**
 * Created by Trang on 6/4/2016.
 */
public interface VideoFragmentInterface {

    public void addVideoItems(Collection<VideoItem> videoItems, int videoType);
}
