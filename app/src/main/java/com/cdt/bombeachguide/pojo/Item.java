package com.cdt.bombeachguide.pojo;

/**
 * Created by nguyen on 5/22/2016.
 */

public class Item {
    public String link;
    public String title;
    public String imageurl;
    public String text;
    public Item(String link, String title, String imageurl, String text) {
        super();
        this.link = link;
        this.title = title;
        this.imageurl = imageurl;
        this.text = text;
    }
    public Item merge(Item troop1,Item troop2){
        if(troop1==null)return troop2;
        else if(troop2==null)return troop1;
        else {
            String tmplink,tmptitle,tmpimageurl,tmptext;
            if(troop1.link.equals(troop2.link)) tmplink=troop1.link;
            else tmplink=troop1.link+troop2.link;

            if(troop1.title.equals(troop2.title)) tmptitle=troop1.title;
            else tmptitle=troop1.title+troop2.title;

            if(troop1.imageurl.equals(troop2.imageurl)) tmpimageurl=troop1.imageurl;
            else tmpimageurl=troop1.imageurl+troop2.imageurl;

            if(troop1.text.equals(troop2.text)) tmptext=troop2.text;
            else tmptext=troop1.text+troop2.text;


            return new Item(tmplink, tmptitle, tmpimageurl, tmptext);
        }
    }
    public void print(){
        System.out.println("------------------------------------------------------");
        System.out.println("title :"+title);
        System.out.println("link :"+link);

        System.out.println("imageUrl :"+imageurl);
        System.out.println("text"+text);

    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String out=title+"/"+link+"/"+imageurl+"/"+text;
        return out;
    }
}
