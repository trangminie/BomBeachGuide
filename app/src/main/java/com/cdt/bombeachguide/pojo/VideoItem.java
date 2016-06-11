package com.cdt.bombeachguide.pojo;

/**
 * Created by Trang on 6/12/2015.
 */
public class VideoItem {
    private String title;
    private String description;
    private String thumbnailURL;//link image
    private String url;
    private String id;
    public VideoItem(String tit, String descrip, String thumbnailurl,String url) {
        super();
        this.title = tit;
        this.description = descrip;
        this.thumbnailURL = thumbnailurl;
        this.url=url;

    }
    public VideoItem(){};
    public VideoItem merge(VideoItem itm1,VideoItem itm2){
        if(itm1==null)return itm2;
        else if(itm2==null)return itm1;
        else {
            String tmpurl,tmptitle,tmpthumbnailURL,tmpDescription;
            if(itm1.thumbnailURL.equals(itm2.thumbnailURL)) tmpurl=itm1.thumbnailURL;
            else tmpurl=itm1.thumbnailURL+itm2.thumbnailURL;

            if(itm1.title.equals(itm2.title)) tmptitle=itm1.title;
            else tmptitle=itm1.title+itm2.title;

            if(itm1.url.equals(itm2.url)) tmpthumbnailURL=itm1.url;
            else tmpthumbnailURL=itm1.url+itm2.url;

            if(itm1.description.equals(itm2.description)) tmpDescription=itm2.description;
            else tmpDescription=itm1.description+itm2.description;


            return new VideoItem( tmptitle, tmpDescription, tmpthumbnailURL,tmpurl);
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
    public String getUrl(){return  url;}
    public void setThumbnailURL(String thumbnail) {
        this.thumbnailURL = thumbnail;
    }

}
