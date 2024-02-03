package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdaptor extends FragmentStateAdapter {
    public ViewPagerAdaptor(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new GiyimFragment();
            case 1:
                return new GidaFragment();
            case 2:
                return new icecekFragment();
            case 3:
                return new TeknolojiFragment();
            case 4:
                return new KozmatikFragment();
            case 5:
                return new HepsiFragment();
            default:
                return new GiyimFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
