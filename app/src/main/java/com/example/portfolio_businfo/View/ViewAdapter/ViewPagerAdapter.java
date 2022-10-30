package com.example.portfolio_businfo.View.ViewAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.portfolio_businfo.View.Activity.MainActivity;
import com.example.portfolio_businfo.View.Fragment.Favorites;
import com.example.portfolio_businfo.View.Fragment.Line;
import com.example.portfolio_businfo.View.Fragment.Station;

public class ViewPagerAdapter extends FragmentStateAdapter {
    Fragment fragment;
    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                fragment = new Favorites();
                break;
            case 1:
                fragment = new Station();
                break;
            default:
                fragment = new Line();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}