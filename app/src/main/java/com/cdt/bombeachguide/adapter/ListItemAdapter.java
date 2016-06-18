package com.cdt.bombeachguide.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdt.bombeachguide.R;
import com.cdt.bombeachguide.pojo.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nguyen on 5/22/2016.
 */
public class ListItemAdapter extends ArrayAdapter<Item>
{
    Activity context=null;
    ArrayList<Item> myArray=null;
    int layoutId;

    public ListItemAdapter(Activity context,
                          int layoutId,
                          ArrayList<Item>arr){
        super(context, layoutId, arr);
        this.context=context;
        this.layoutId=layoutId;
        this.myArray=arr;
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {

        LayoutInflater inflater=
                context.getLayoutInflater();
        convertView=inflater.inflate(layoutId, null);
        //chỉ là test thôi, bạn có thể bỏ If đi
        if(myArray.size()>0 && position>=0)
        {
            //dòng lệnh lấy TextView ra để hiển thị Mã và tên lên
            final Item item=myArray.get(position);
            final ImageView imgitem=(ImageView)
                    convertView.findViewById(R.id.video_thumbnail);
            imgitem.setImageResource(R.drawable.header);
            Picasso.with(context)
                    .load(item.imageurl)
                    .resize(100, 100)
                    .into(imgitem);

            final TextView tvitem=(TextView)convertView.findViewById(R.id.video_title);
            tvitem.setTextColor(Color.RED);
            tvitem.setText(item.title);
        }

        return convertView;
    }
}