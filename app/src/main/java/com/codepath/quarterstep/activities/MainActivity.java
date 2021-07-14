package com.codepath.quarterstep.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.fragments.ArrangmentFragment;
import com.codepath.quarterstep.fragments.FeedFragment;
import com.codepath.quarterstep.fragments.ProfileFragment;
import com.codepath.quarterstep.utils.ScreenSlidePageFragment;
import com.codepath.quarterstep.utils.ScreenSlidePagerAdapter;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.parse.ParseUser;

import java.io.FileReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    // Temporary logout button
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = new ParseUser();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        setUpNavigation();
    }

    private void setUpNavigation() {
        ArrayList<ScreenSlidePageFragment> fragments = new ArrayList<>();
        fragments.add(ScreenSlidePageFragment.newInstance(getString(R.string.arrangement), R.color.red_inactive));
        fragments.add(ScreenSlidePageFragment.newInstance(getString(R.string.home), R.color.blue_inactive));
        fragments.add(ScreenSlidePageFragment.newInstance(getString(R.string.profile), R.color.green_inactive));

        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(fragments, fragmentManager);

        final BubbleNavigationLinearView bubbleNavigationLinearView = findViewById(R.id.bottomNavigationBar);
        final ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment;
                switch (position) {
                    case 0:
                        fragment = new ArrangmentFragment();
                        break;
                    case 1:
                        fragment = new FeedFragment();
                        break;
                    case 2:
                        fragment = new ProfileFragment();
                        break;
                    default:
                        fragment = new ArrangmentFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                bubbleNavigationLinearView.setCurrentActiveItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                viewPager.setCurrentItem(position, true);
            }
        });

        // Set default view
        fragmentManager.beginTransaction().replace(R.id.flContainer, new ArrangmentFragment()).commit();
    }
}