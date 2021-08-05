package com.codepath.quarterstep.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.fragments.ArrangementFragment;
import com.codepath.quarterstep.fragments.FeedFragment;
import com.codepath.quarterstep.fragments.ProfileFragment;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.ScreenSlidePageFragment;
import com.codepath.quarterstep.utils.ScreenSlidePagerAdapter;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.parse.ParseUser;

import java.io.FileReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static Activity mainActivity;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        Constants.LOAD_SOUNDS(this);
        Constants.SET_AUDIO_MANAGER(this);

        setUpNavigation();
    }

    private void setUpNavigation() {
        ArrayList<ScreenSlidePageFragment> fragments = new ArrayList<>();
        fragments.add(new ArrangementFragment());
        fragments.add(new FeedFragment());
        fragments.add(new ProfileFragment());

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
    }
}