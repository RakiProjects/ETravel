package com.example.e_travel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class CityContentActivity extends AppCompatActivity{

    private static final String EXTRA_CITY_ID = "EXTRA_CITY_ID";

    public static void start(Context context, int cityId, String cityName) {
        Intent starter = new Intent(context, CityContentActivity.class);
        starter.putExtra(EXTRA_CITY_ID, cityId);
        starter.putExtra("cityName", cityName);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cityName = (String) getIntent().getExtras().get("cityName");
        setTitle(cityName);
        setContentView(R.layout.activity_city_content);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.city_content_pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.city_content_tabs);
        tabLayout.setupWithViewPager(pager);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            int cityId = (int) getIntent().getExtras().get(EXTRA_CITY_ID);
            return  CityContentFragment.newInstance(cityId, position);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.sightseeing);
                case 1:
                    return getResources().getText(R.string.restaurants);
                case 2:
                    return getResources().getText(R.string.hotels);
            }
            return null;
        }
    }
}
