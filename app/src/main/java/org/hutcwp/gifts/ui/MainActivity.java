package org.hutcwp.gifts.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.app.AppGlobal;
import org.hutcwp.gifts.databinding.ActivityMainBinding;
import org.hutcwp.gifts.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {


    ActivityMainBinding binding;


    private String currentFragmentTag;

    private FragmentManager fragmentManager;

    private static final String FRAGMENT_TAG_ZONE = "zone";
    private static final String FRAGMENT_TAG_HOME = "tab_home";
    private static final String FRAGMENT_TAG_USER = "user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.lyTopTitle.setBtnLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "onclik", Toast.LENGTH_LONG).show();
            }
        });
        binding.lyTopTitle.serBtnRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "onclik", Toast.LENGTH_LONG).show();
            }
        });
        binding.lyTopTitle.setTitle("我的主页");

        binding.lyTopTitle.setVisibility(View.GONE);

        setPageChange();

        fragmentManager = getSupportFragmentManager();

        initFragment(savedInstanceState);
    }

    /**
     * 设置Page改变
     */
    private void setPageChange() {


        binding.lyBottom.lyZone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switchContent(FRAGMENT_TAG_ZONE);
                Log.d("test","zone");
                return true;
            }
        });

        binding.lyBottom.lyHome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switchContent(FRAGMENT_TAG_HOME);
                Log.d("test","tab_home");

                return true;
            }
        });
        binding.lyBottom.lyUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switchContent(FRAGMENT_TAG_USER);
                Log.d("test","user");
                return true;
            }
        });

    }


    /**
     * 初始化Fragment
     * @param savedInstanceState
     */
    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            switchContent(FRAGMENT_TAG_HOME);
        } else {
            currentFragmentTag = savedInstanceState.getString(AppGlobal.CURRENT_INDEX);
            switchContent(currentFragmentTag);
        }
    }

    /**
     * 重写的方法
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(AppGlobal.CURRENT_INDEX, currentFragmentTag);
    }

    /**
     * 选择当前的Fragment
     *
     * @param name Fragment的名字
     */
    public void switchContent(String name) {

        Log.d("error", "switchContent");
        if (currentFragmentTag != null && currentFragmentTag.equals(name))
            return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Fragment currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        Fragment foundFragment = fragmentManager.findFragmentByTag(name);
        if (foundFragment == null) {
            switch (name) {
                case FRAGMENT_TAG_HOME:
                    foundFragment = new HomeFragment();
                    break;
                case FRAGMENT_TAG_ZONE:
                    foundFragment = new ZoneFragment();
                    break;
                case FRAGMENT_TAG_USER:
                    foundFragment = new UserFragment();
                    break;
            }
        }
        if (foundFragment == null) {

        } else if (foundFragment.isAdded()) {
            ft.show(foundFragment);
        } else {
            ft.add(R.id.content, foundFragment, name);
        }
        ft.commit();
        currentFragmentTag = name;

    }

}
