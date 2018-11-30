package github.a3sung.dreammemo.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import java.util.Set;

import github.a3sung.dreammemo.MainActivity;
import github.a3sung.dreammemo.R;
import github.a3sung.dreammemo.fragment.DreamMemoListFragment;
import github.a3sung.dreammemo.fragment.SettingFragment;
import github.a3sung.dreammemo.fragment.SignInFragment;
import github.a3sung.dreammemo.model.PageModel;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fm;
    private MainActivity activity;
    private int currentPage = -1;
    private int pageCount = 3;
    private Fragment[] pageFragemnt = new Fragment[pageCount];
    private PageModel[] pageModel = new PageModel[pageCount];


    private MainPagerAdapter(FragmentManager fm, MainActivity activity) {
        super(fm);
        this.fm = fm;
        this.activity = activity;
        initializePage();
    }

    private void initializePage(){
        pageFragemnt[0] = DreamMemoListFragment.newInstance(0);
        pageFragemnt[1] = SignInFragment.newInstance(1);
        pageFragemnt[2] = SettingFragment.newInstance(2);
        pageModel[0] = new PageModel();
        pageModel[1] = new PageModel();
        pageModel[2] = new PageModel();
    }

    // initialized part
    private static MainPagerAdapter instance;
    public static MainPagerAdapter initInstance(FragmentManager fm, MainActivity activity){
        if (instance == null){
            instance = new MainPagerAdapter(fm, activity);
        }
        return instance;
    }

    public static MainPagerAdapter getInstance() throws  NullPointerException{
        return instance;
    }

    private boolean isAvailablePosition(int position){
        if (0 <= position && position < pageCount) return true;
        else return false;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        System.out.println(position);
        currentPage = position;
        if (isAvailablePosition(position)){
            return pageFragemnt[position];
        }
        else {
            return DreamMemoListFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return pageCount;
    }

    public boolean startFragment(int page, Fragment fragment, Object sendData){

        if (isAvailablePosition(page)){
            pageFragemnt[page] = fragment;
            pageModel[page].setCurrentPageData(sendData);
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(String.valueOf(page));
            ft.replace(R.id)
            return true;
        }
        else {
            return false;
        }
    }


}
