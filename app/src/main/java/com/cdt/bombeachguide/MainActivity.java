package com.cdt.bombeachguide;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cdt.bombeachguide.adapter.NavDrawerListAdapter;
import com.cdt.bombeachguide.fragment.ItemFragment;
import com.cdt.bombeachguide.fragment.MainFragment;
import com.cdt.bombeachguide.pojo.NavDrawerItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle drawerToggle;
    private NavDrawerListAdapter mAdapter;
    private Context mContext;

    private String[] navMenuTitles = {"Videos", "Artifacts", "Troops", "About","Setting"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        createActionBar();
        initDrawer();
        if (savedInstanceState == null) {
            displayView(0);
        }



    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private void createActionBar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDrawer(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close){
            public void onDrawerClosed(View view) {

            }

            public void onDrawerOpened(View drawerView) {
                drawerToggle.setDrawerIndicatorEnabled(true);
            }
        };
        mDrawerLayout.setDrawerListener(drawerToggle);

        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        ViewGroup header = (ViewGroup) getLayoutInflater().inflate(R.layout.header_navigation, mDrawerList,
                false);
        mDrawerList.addHeaderView(header);
        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
        // Videos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Artifacts
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Troops
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // About
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Setting
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4],navMenuIcons.getResourceId(4,-1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        mAdapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(mAdapter);
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            switch (position){
                case 1:
                    mDrawerList.setItemChecked(position, true);
                    mDrawerList.setSelection(position);
                    if(mDrawerLayout.isDrawerOpen(findViewById(R.id.rl_drawer))){
                        mDrawerLayout.closeDrawer(findViewById(R.id.rl_drawer));
                    }
                    Intent videosIntent = VideoActivity.newIntent(mContext);
                    startActivity(videosIntent);

                    break;

                case 2:

                    break;


                case 3:

                    break;


            }
        }
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        Fragment mFragment = null;


        switch (position){
            case 1:
                mFragment = MainFragment.newInstance();
                break;
            case 2:
                mFragment = ItemFragment.newInstance("http://boombeach.wikia.com/wiki/Category:Artifacts");
                break;
            case 3:
                mFragment = ItemFragment.newInstance("http://boombeach.wikia.com/wiki/Category:Troops");
                break;
            case 4:
                Toast.makeText(getApplicationContext(),"About Fragment",Toast.LENGTH_LONG).show();
                break;
            case 5:
                Toast.makeText(getApplicationContext(),"Settings Fragment",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }


        if (mFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, mFragment).commit();

            // update selected item and title, then close the drawer
//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);
            setTitle("BoomBeachGuide");
            if(mDrawerLayout.isDrawerOpen(findViewById(R.id.rl_drawer))){
                mDrawerLayout.closeDrawer(findViewById(R.id.rl_drawer));
            }
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
