package com.cdt.bombeachguide.HTTP;

import android.util.Log;

import com.cdt.bombeachguide.fragment.MainFragment;
import com.cdt.bombeachguide.fragment.VideoFragment;
import com.cdt.bombeachguide.pojo.Item;
import com.cdt.bombeachguide.pojo.VideoItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nguyen on 5/22/2016.
 */
public class HTTPProcess {
//    private String mUrl="";
//    public HTTPProcess(String url){
//        this.mUrl=url;
//    }
HashMap<String, Item> mHashMap = new HashMap<String, Item>();
HashMap<String, VideoItem> mItemHashMap = new HashMap<String, VideoItem>();
private static final String MAIN_URL = "http://boombeach.wikia.com";
public void getListItemsFromUrl(String url,ArrayList<Item> myArray){
        try {
            Document doc =  Jsoup.connect(url).get();

            Elements elements = doc.select("tr");
            long starttime=System.currentTimeMillis();
            for (Element element : elements) {
                // for Artifacts

                Elements tmpelements1 = element.select("td");
                for (Element tmpelement : tmpelements1) {

                    String title = tmpelement.select("a").attr("title");
                    String link = tmpelement.select("a").attr("href");
                    String text = tmpelement.text();
                    String imageUrl =""/*= tmpelement.select("img").attr("src")*/;
                    Elements lsimgurl = tmpelement.select("img");
                    if(lsimgurl.size()>1) imageUrl=lsimgurl.get(1).attr("src");
                    else if(lsimgurl.size()==1) imageUrl=lsimgurl.get(0).attr("src");
                    if (!title.equals("") && !link.equals("")) {
                        Item tmp = mHashMap.get(title);
                        if (tmp == null) {
                            mHashMap.put(title, new Item(link, title,
                                    imageUrl, text));
                        } else {
                            mHashMap.put(title, tmp.merge(tmp, new Item(link,
                                    title, imageUrl, text)));
                        }
                    }


                }

            }


            for (String i : mHashMap.keySet()) {
                if(mHashMap.get(i).title!=null&&mHashMap.get(i).link!=null&&mHashMap.get(i).text!=null&&mHashMap.get(i).imageurl!="")
               myArray.add(mHashMap.get(i));
            }

            // System.out.println("body "+doc.body().text());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


}

    public void searchListItemsFromUrl(String url, MainFragment mainFragment) {
        List<VideoItem> videos = new ArrayList<VideoItem>();

            try {
                Document doc =  Jsoup.connect(url).get();

                Elements elements = doc.select("tr");
                long starttime=System.currentTimeMillis();
                for (Element element : elements) {
                    // for Artifacts

                    Elements tmpelements1 = element.select("td");
                    for (Element tmpelement : tmpelements1) {

                        String title = tmpelement.select("a").attr("title");
                        String decription = tmpelement.text();
                        String murl = tmpelement.select("a").attr("href");

                        String thumbnailUrl =""/*= tmpelement.select("img").attr("src")*/;
                        Elements lsimgurl = tmpelement.select("img");
                        if(lsimgurl.size()>1) thumbnailUrl=lsimgurl.get(1).attr("src");
                        else if(lsimgurl.size()==1) thumbnailUrl=lsimgurl.get(0).attr("src");
                        if (!title.equals("") && !murl.equals("")) {


                            VideoItem tmp = mItemHashMap.get(title);
                            if (tmp == null) {
                                mItemHashMap.put(title, new VideoItem( title,decription,
                                        thumbnailUrl, murl));
                            } else {
                                mItemHashMap.put(title, tmp.merge(tmp, new VideoItem( title,decription,
                                        thumbnailUrl, murl)));
                            }
                        }


                    }

                }


                for (String i : mItemHashMap.keySet()) {
                    if(mItemHashMap.get(i).getTitle()!=null&&mItemHashMap.get(i).getUrl()!=null&&mItemHashMap.get(i).getDescription()!=null&&mItemHashMap.get(i).getThumbnailURL()!="") {
                        videos.add(mItemHashMap.get(i));
                        Log.d("vietduc", mItemHashMap.get(i).getTitle());
                    }
                }
                if(!videos.isEmpty()){
                    mainFragment.addVideoItems(new ArrayList<VideoItem>(videos), VideoFragment.LIST_VIDEO_BoombeachWiki);
                    videos.clear();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    public void getListItemBoomBeachWiki(String url, MainFragment mainFragment) {
        List<VideoItem> videos = new ArrayList<VideoItem>();
        List<VideoItem> videos2 = new ArrayList<VideoItem>();
        try {
            Document doc =  Jsoup.connect(url).get();
            Elements newsHeadlines = doc.select("div");

            for(Element element:newsHeadlines){

                if(element.attr("class").equals("clickable-div"))
                {


                    String tmpTiltle,tmpThumnailUrl = null,tmpUrl;
                    tmpTiltle = element.text();
                    tmpUrl=MAIN_URL+element.select("a").attr("href");
                    for(Element element2:element.select("img")){
                        if(element2.attr("onload").equals("")){
                           tmpThumnailUrl = element2.attr("src");

                            break;
                        }

                    }
                    if(!tmpTiltle.equals(""))   {
                        VideoItem tmpItem = new VideoItem(tmpTiltle,null,tmpThumnailUrl,tmpUrl);
                        videos.add(tmpItem);
                      //  Log.d("vietduc","tmpUrl : "+tmpItem.getUrl()+" tmpThumbnailUrl "+tmpThumnailUrl);
                    }else{
                      String[] lsUrl=tmpUrl.split("/");
                        tmpTiltle=lsUrl[lsUrl.length-1];
                        VideoItem tmpItem = new VideoItem(tmpTiltle,null,tmpThumnailUrl,tmpUrl);
                        videos2.add(tmpItem);

                    }
                }

            }
            if(!videos.isEmpty()){
                mainFragment.addVideoItems(new ArrayList<VideoItem>(videos), VideoFragment.LIST_VIDEO_BoombeachWiki);
                videos.clear();
            }
            if(!videos2.isEmpty()){
                mainFragment.addVideoItems(new ArrayList<VideoItem>(videos2), VideoFragment.LIST_VIDEO_StrategyBlog);
                videos2.clear();
            }




        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


}
