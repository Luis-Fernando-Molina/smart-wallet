package com.bitdubai.smartwallet.walletframework;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
import android.view.ViewGroup;
import android.widget.TextView;
import com.bitdubai.smartwallet.wallets.teens.AccountDetailAccountFragment;
import com.bitdubai.smartwallet.wallets.teens.AccountDetailAllFragment;
import com.bitdubai.smartwallet.wallets.teens.AccountDetailCreditsFragment;
import com.bitdubai.smartwallet.wallets.teens.AccountDetailDebitsFragment;
import com.bitdubai.smartwallet.wallets.teens.AccountDetailFiltersFragment;
import com.bitdubai.smartwallet.wallets.teens.DiscountsFragment;
import com.bitdubai.smartwallet.wallets.teens.HomeFragment;
import com.bitdubai.smartwallet.wallets.teens.SendFragment;
import com.bitdubai.smartwallet.wallets.teens.BalanceFragment;
import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.wallets.teens.ReceiveFragment;
import com.bitdubai.smartwallet.wallets.teens.RefillFragment;
import com.bitdubai.smartwallet.wallets.teens.StoresFragment;
public class AccountDetailActivity extends FragmentActivity
{

    private final Handler handler = new Handler();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;

    private String walletStyle = "";

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Menu menu;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_framework_activity_account_detail);

        // I get the action bar title id and put it on a text view in order to later change its color
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);

        Intent i=getIntent();

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        mTitle = "Account details";

        ((MyApplication) this.getApplication()).setActionBarProperties(this, getWindow(), tabs, getActionBar(), getResources(), abTitle, mTitle.toString());
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

        tabs.setBackgroundResource(R.drawable.background_tiled_diagonal_light);
        tabs.setDividerColor(0xFFBBBBBB);



            /* Custom Action Bar with Icon and Text */

        int tagId = MyApplication.getTagId();
        String[] account_types;
        String[] balances;
        String[] balances_available;
        String[] account_aliases;


        account_types = new String[]{"Saving account", "Current account", "Saving account"};
        account_aliases = new String[]{"My savings", "Pocket money", "Holiday savings"};
        balances = new String[]{"$4,615.00", "$78.50", "$500.00"};
        balances_available = new String[]{"$1,615.00 available", "$55.00 available", "$300.00 available"};


        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.wallets_teens_fragment_account_detail_action_bar,
                null);

        // Set up your ActionBar
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        TextView tv;

        tv = (TextView) actionBarLayout.findViewById(R.id.account_type);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(account_types[tagId]);

        tv = (TextView) actionBarLayout.findViewById(R.id.balance);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(balances[tagId]);

        tv = (TextView) actionBarLayout.findViewById(R.id.balance_available);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(balances_available[tagId]);

        tv = (TextView) actionBarLayout.findViewById(R.id.account_alias);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(account_aliases[tagId]);

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

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }




    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wallet_framework_activity_account_detail_menu, menu);

        LayoutInflater inflaterClone = getLayoutInflater().cloneInContext(getLayoutInflater().getContext());
        LayoutInflater.Factory lif = new MyLayoutInflaterFactory();
        inflaterClone.setFactory(lif);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_contact:
                AccountDetailFiltersFragment dialog = new AccountDetailFiltersFragment();
                dialog.show(getSupportFragmentManager(), "QuickContactFragment");
                return true;

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        changeColor(currentColor);
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
        private String[] titles_2 = {  "Debits", "Credits", "All"};

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

            if (walletStyle.equals("Kids") )
            {titles = titles_1;}
            else
            {titles = titles_2;}

            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {

            if (walletStyle.equals("Kids") )
            {titles = titles_1;}
            else
            {titles = titles_2;}

            Fragment currentFragment;
            switch (position) {
                //   case 0:
                //       currentFragment =   AccountDetailAccountFragment.newInstance(position);
                //       break;
                case 0:
                    currentFragment =   AccountDetailAllFragment.newInstance(position);
                    break;
                case 1:
                    currentFragment =  AccountDetailAllFragment.newInstance(position);
                    break;
                case 2:
                    currentFragment =  AccountDetailAllFragment.newInstance(position);
                    break;

                default:
                    currentFragment =   AccountDetailAllFragment.newInstance(position);
                    break;
            }
            return currentFragment;
        }

    }






}