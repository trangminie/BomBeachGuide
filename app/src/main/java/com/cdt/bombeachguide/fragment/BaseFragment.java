package com.cdt.bombeachguide.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cdt.bombeachguide.MainActivity;
import com.cdt.bombeachguide.R;

/**
 * Created by Trang on 6/5/2016.
 */
public class BaseFragment extends Fragment {

    public String entityName = "";
    public MainActivity getMainActivity(){
        return (MainActivity) getActivity();
    }

     /**
     * Diplaying fragment view for selected item
     * */
    public void displayDetail(BaseFragment mDetailFragment){
        if (mDetailFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.frame_container, mDetailFragment).addToBackStack(null).commit();
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating detail fragment");
        }
    }

}
