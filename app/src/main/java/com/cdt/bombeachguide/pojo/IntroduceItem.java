package com.cdt.bombeachguide.pojo;

/**
 * Created by Trang on 6/4/2016.
 */
public class IntroduceItem {

    private String mImagePath;
    private String mImageName;

    public IntroduceItem(String imagePath, String imageName) {
        mImagePath = imagePath;
        mImageName = imageName;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public String getImageName() {
        return mImageName;
    }

    public void setImageName(String imageName) {
        mImageName = imageName;
    }
}
