package com.bitdubai.smartwallet.ui.os.android.app.subapp.shop.version_1.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyTypefaceSpan;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.fragment.NavigationDrawerFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyLayoutInflaterFactory;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity.RequestsSentActivity;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.PagerSlidingTabStrip;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.shop.version_1.fragment.HistoryFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.shop.version_1.fragment.ReviewsFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.shop.version_1.fragment.ShopMapFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.shop.version_1.fragment.ShopProductsFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.shop.version_1.fragment.ShopShopFragment;

/**
 * Created by Natalia on 09/01/2015.
 */
public class ShopActivity  extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks
{

    private final Handler handler = new Handler();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private Drawable oldBackground = null;
    private int currentColor = 0xFF88ee00;

    private String walletStyle = "";

    private CharSequence mTitle = "My Shop";
    private Menu menu;
    private NavigationDrawerFragment mNavigationDrawerFragment;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity_shop);
        MyApplication.setActivityId("ShopActivity");
        // I get the action bar title id and put it on a text view in order to later change its color
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Intent i=getIntent();

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);


        //((MyApplication) this.getApplication()).setActionBarProperties(this, getWindow(), tabs, getActionBar(), getResources(), abTitle, mTitle.toString());
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //getWindow().getDecorView().setBackgroundResource(R.drawable.wallet_wallpaper_yellow);

        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        ((MyApplication) this.getApplication()).setDefaultTypeface(tf);
        tabs.setTypeface(tf, 1);
        //changeColor(currentColor);

        tabs.setDividerColor(0xFFFFFFFF);
        tabs.setIndicatorColor(0xFFFFFFFF);
        tabs.setIndicatorHeight(9);
        tabs.setBackgroundColor(0xFF76dc4a);
        tabs.setTextColor(0xFFFFFFFF);

        String color = "#76dc4a";
        MyApplication.setActionBar(getActionBar());
        ((MyApplication) this.getApplication()).changeColor(Color.parseColor(color), getResources());


        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }




    //***
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    //***




    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wallet_shop_activity_account_detail_menu, menu);

        LayoutInflater inflaterClone = getLayoutInflater().cloneInContext(getLayoutInflater().getContext());
        LayoutInflater.Factory lif = new MyLayoutInflaterFactory();
        inflaterClone.setFactory(lif);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
/*
            case R.id.action_contact:
                TabbedDialogFragment dialog = new TabbedDialogFragment();
                dialog.show(getSupportFragmentManager(), "QuickContactFragment");
                return true;
*/
            case R.id.action_requests_sent:
                Intent intent;
                intent = new Intent(this, RequestsSentActivity.class);
                startActivity(intent);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    private void changeColor(int newColor) {

        tabs.setIndicatorColor(newColor);

        // change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(newColor);
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

            if (oldBackground == null) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ld.setCallback(drawableCallback);
                } else {
                    getActionBar().setBackgroundDrawable(ld);
                }

            } else {

                TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

                // workaround for broken ActionBarContainer drawable handling on
                // pre-API 17 builds
                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    td.setCallback(drawableCallback);
                } else {
                    getActionBar().setBackgroundDrawable(td);
                }

                td.startTransition(200);

            }

            oldBackground = ld;


            getActionBar().setDisplayShowTitleEnabled(false);
            getActionBar().setDisplayShowTitleEnabled(true);

        }

        currentColor = newColor;

    }

    public void onColorClicked(View v) {

        int color = Color.parseColor(v.getTag().toString());
        changeColor(color);

    }

    //***
    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
     //   currentColor = savedInstanceState.getInt("currentColor");
      //  changeColor(currentColor);
    }

    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;
        private String[] titles_1 = { };
        private String[] titles_2 = { "Shop", "Products", "Reviews", "History", "Map"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (walletStyle.equals("Kids") )
            {titles = titles_1;}
            else
            {titles = titles_2;}

            return titles[position];
        }

        @Override
        public int getCount() {
           titles = titles_2;
            return titles.length;
        }
        @Override
        public Fragment getItem(int position) {
           titles = titles_2;
            Fragment currentFragment;
            switch (position) {
                case 0:
                    currentFragment =   ShopShopFragment.newInstance(position);
                    break;
                case 1:
                    currentFragment =   ShopProductsFragment.newInstance(position);
                    break;
                case 2:
                    currentFragment =   ReviewsFragment.newInstance(position);
                    break;
                case 3:
                    currentFragment =  HistoryFragment.newInstance(position);
                    break;
                case 4:
                    currentFragment =  ShopMapFragment.newInstance(position);
                    break;
                default:
                    currentFragment =   ShopShopFragment.newInstance(position);
                    break;
            }
            return currentFragment;
        }


    }
}