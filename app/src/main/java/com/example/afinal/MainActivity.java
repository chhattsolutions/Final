package com.example.afinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.Model.Chat;
import com.example.afinal.fragments.Newsfeed;
import com.example.afinal.fragments.friends;
import com.example.afinal.fragments.groups;
import com.example.afinal.fragments.status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatButton;
     private TabLayout tablayout;
    DatabaseReference reference;
    ImageView count_yes;
    ImageView count_no;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Chhatt (چھت)");
        tablayout=(TabLayout) findViewById(R.id.tabs);
      //  floatButton = (FloatingActionButton) findViewById(R.id.fab);
        final ViewPager viewPager=(ViewPager) findViewById(R.id.viewpager);
        final ViewPagerAdpter viewPagerAdpter=new ViewPagerAdpter(getSupportFragmentManager());

        viewPagerAdpter.addFragment(new status(),"Status");
        viewPagerAdpter.addFragment(new friends(),"Friends");
        viewPagerAdpter.addFragment(new groups(),"Users");
        viewPagerAdpter.addFragment(new Newsfeed(),"NewsFeed");
        viewPager.setAdapter(viewPagerAdpter);
        tablayout.setupWithViewPager(viewPager);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        count_yes=(ImageView) findViewById(R.id.tick_yes);
        count_no=(ImageView)findViewById(R.id.tick_no);

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
        setupTabIcons();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId())
       {
           case R.id.logout:
               FirebaseAuth.getInstance().signOut();
               startActivity(new Intent(MainActivity.this, login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
               return true;
           case R.id.profile:
              Intent a=new Intent(MainActivity.this,profile.class);
               startActivity(a);
               return true;
           case R.id.about:
               Toast.makeText(MainActivity.this,"Version 0.0001",Toast.LENGTH_LONG).show();
           case R.id.followUps:
               Intent i=new Intent(MainActivity.this, followsup.class);
               startActivity(i);
       }
       return false;
    }

    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.ic_tab_infor,
                R.drawable.ic_tab_person,
                R.drawable.ic_tab_group,
                R.drawable.ic_action_name
        };

        tablayout.getTabAt(2).setIcon(tabIcons[2]);
        tablayout.getTabAt(0).setIcon(tabIcons[0]);
        tablayout.getTabAt(1).setIcon(tabIcons[1]);
        tablayout.getTabAt(3).setIcon(tabIcons[3]);
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
    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
       HashMap<String, Object> hashMap = new HashMap<>();
       hashMap.put("status", status);

       reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();

            status("Offline");

    }

}
