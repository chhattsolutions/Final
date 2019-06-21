package com.example.afinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.afinal.MainActivity;
import com.example.afinal.R;
import com.example.afinal.fragments.Inventory;
import com.example.afinal.fragments.Requirement;
import com.example.afinal.fragments.friends;
import com.example.afinal.fragments.groups;
import com.example.afinal.fragments.status;

import java.util.ArrayList;

public class followsup extends AppCompatActivity {
    private TabLayout tablayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followsup);
        tablayout=(TabLayout) findViewById(R.id.tabs);
        //  floatButton = (FloatingActionButton) findViewById(R.id.fab);
        final ViewPager viewPager=(ViewPager) findViewById(R.id.viewpager);
        final followsup.ViewPagerAdpter viewPagerAdpter= new ViewPagerAdpter(getSupportFragmentManager());
        viewPagerAdpter.addFragment(new Requirement(),"Requirement");
        viewPagerAdpter.addFragment(new Inventory(),"Inventory");
        viewPager.setAdapter(viewPagerAdpter);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chhatt (چھت)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(followsup.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        tablayout.setupWithViewPager(viewPager);
        tablayout.getTabAt(0).setText("Requirement");
        tablayout.getTabAt(1).setText("Inventory");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });
    }

    class ViewPagerAdpter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragment;
        private ArrayList<String> title;

        ViewPagerAdpter(FragmentManager fm) {
            super(fm);
            this.fragment = new ArrayList<>();
            this.title = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragment.get(position);
        }

        @Override
        public int getCount() {
            return fragment.size();
        }

        public void addFragment(Fragment fragments, String titles) {
            fragment.add(fragments);
            title.add(titles);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }
}
