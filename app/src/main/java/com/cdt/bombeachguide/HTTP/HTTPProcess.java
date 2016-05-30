package com.cdt.bombeachguide.HTTP;

import android.content.ClipData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nguyen on 5/22/2016.
 */
public class HTTPProcess {
//    private String mUrl="";
//    public HTTPProcess(String url){
//        this.mUrl=url;
//    }
HashMap<String, Item> mHashMap = new HashMap<String, Item>();

public void download(String url,ArrayList<Item> myArray){


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

}
