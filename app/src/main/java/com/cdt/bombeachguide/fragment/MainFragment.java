package com.cdt.bombeachguide.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdt.bombeachguide.R;
import com.cdt.bombeachguide.VideoActivity;
import com.cdt.bombeachguide.pojo.IntroduceItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trang on 5/21/2016.
 */
public class MainFragment extends Fragment {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<IntroduceItem> mIntroduceItemsList;


    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mIntroduceItemsList = new ArrayList<>();
//        mIntroduceItemsList.add(new IntroduceItem(null, "Video"));
        mIntroduceItemsList.add(new IntroduceItem(null, "Troops"));
        mIntroduceItemsList.add(new IntroduceItem(null, "Artifacts"));
        mRecyclerView.setAdapter(new ImageAdapter(mIntroduceItemsList));
        return rootView;
    }

    private class ImageHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private ImageView mImageView;
        private TextView mTextView;
        private int position;
        private IntroduceItem mIntroduceItem;


        public ImageHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.introduce_item, parent, false));

            mImageView = (ImageView) itemView.findViewById(R.id.img_intro);
            mImageView.setOnClickListener(this);

            mTextView = (TextView) itemView.findViewById(R.id.txt_intro_name);
        }

        public void bindImage(IntroduceItem item, int pos){
            mIntroduceItem = item;
            position = pos;
            mTextView.setText(item.getImageName());
        }

        @Override
        public void onClick(View view) {
            Intent intent = VideoActivity.newIntent(getContext());
            startActivity(intent);
        }
    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageHolder>{
        private List<IntroduceItem> mIntroduceItems;

        public ImageAdapter(List<IntroduceItem> listItems) {
            mIntroduceItems = listItems;
        }


        @Override
        public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ImageHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(ImageHolder imageHolder, int position) {
            IntroduceItem item = mIntroduceItems.get(position);
            imageHolder.bindImage(item, position);
        }

        @Override
        public int getItemCount() {
            return mIntroduceItems.size();
        }
    }
}
