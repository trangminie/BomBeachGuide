package com.cdt.bombeachguide.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cdt.bombeachguide.HTTP.HTTPProcess;
import com.cdt.bombeachguide.HTTP.Item;
import com.cdt.bombeachguide.R;
import com.cdt.bombeachguide.WebViewActivity;
import com.cdt.bombeachguide.adapter.ListItemAdapter;

import java.util.ArrayList;

/**
 * Created by Trang on 5/21/2016.
 */
public class ListItemFragment extends BaseFragment {
    ListView mItemListView;
    ArrayList<Item> mItemArrayList=new ArrayList<Item>();
    Context mContext;
    private static String mUrl;

    ListItemAdapter mItemAdapter=null;
    public static ListItemFragment newInstance(String url){
        ListItemFragment fragment = new ListItemFragment();
        mUrl=url;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_item_fragment, container, false);
        mItemListView=(ListView)rootView.findViewById(R.id.item_listview);

        mContext=rootView.getContext();
     //   mItemArrayList.add(new Item("a","b","c","d"));
        mItemAdapter = new ListItemAdapter( (Activity)mContext,R.layout.item_layout,mItemArrayList);

        mItemListView.setAdapter(mItemAdapter);
        final HTTPProcess mHTTPProcess=new HTTPProcess();


        new Thread(new Runnable() {
            @Override
            public void run() {
           //     mHTTPProcess.download("http://boombeach.wikia.com/wiki/Category:Artifacts",mItemArrayList);
                mHTTPProcess.download(mUrl,mItemArrayList);
                ((Activity) mContext).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(mContext,"get data finish "+mItemArrayList.size(),Toast.LENGTH_LONG).show();
                        mItemAdapter.notifyDataSetChanged();
                    }
                });

             //   mItemAdapter.notifyDataSetChanged();
            }
        }).start();
        mItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ((Activity) mContext).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(mContext,"click"+mItemArrayList.get(position).link,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(mContext,WebViewActivity.class);
                        String url="http://boombeach.wikia.com"+mItemArrayList.get(position).link;
                        intent.putExtra("link",url);
                        startActivity(intent);
                    }
                });
            }
        });
        getMainActivity().showDrawerAsDrawer();
        return rootView;

    }
}
