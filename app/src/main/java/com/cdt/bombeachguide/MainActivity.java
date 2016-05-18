package com.cdt.bombeachguide;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cdt.bombeachguide.adapter.NavDrawerListAdapter;
import com.cdt.bombeachguide.pojo.NavDrawerItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle drawerToggle;
    private NavDrawerListAdapter mAdapter;
	
    // slide menu items test
    private String[] navMenuTitles = {"Classic landscape", "Classic portrait", "Classic square", "Amazing frame"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createActionBar();
        initDrawer();
        if (savedInstanceState == null) {
            displayView(0);
        }
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
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Blocks & Item
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Mobs
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Youtube
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));


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
            displayView(position);
        }
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {

    }
}
