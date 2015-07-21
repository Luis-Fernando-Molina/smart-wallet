package com.bitdubai.android_core.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.bitdubai.android_core.app.common.version_1.adapters.PagerAdapter;
import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.classes.MyTypefaceSpan;
import com.bitdubai.android_core.app.common.version_1.navigation_drawer.NavigationDrawerFragment;
import com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ReceiveFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.WalletDesktopFragment;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Matias Furszyfer
 */

public class FermatActivity extends FragmentActivity{

    public static final int ACTIVITY_TYPE_SUB_APP=1;
    public static final int ACTIVITY_TYPE_WALLET=2;



    /**
     * Navigation menu
     */
    private NavigationDrawerFragment NavigationDrawerFragment;

    /**
     * Screen adapters
     */
    private TabsPagerAdapter adapter;
    private PagerAdapter PagerAdapter;

    /**
     * Activity type
     */
    private int activityType;

    /**
     *  Called when the activity is first created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            /*
            * Load wallet UI
            */
            loadUI();

        } catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Error loading the UI - " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Initialize the contents of the Activity's standard options menu
     * @param menu
     * @return true if all is okey
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try {
            MenuInflater inflater = getMenuInflater();

            /**
             *  Our future code goes here...
             */

        }
        catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Error in create optionMenu " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        return super.onCreateOptionsMenu(menu);

    }


    /**
     * This hook is called whenever an item in your options menu is selected.
     * @param item
     * @return true if button is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        try {

            int id = item.getItemId();

            /**
             *  Our future code goes here...
             */


        }
        catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM, FermatException.wrapException(e));

            Toast.makeText(getApplicationContext(), "Error in OptionsItemSelected " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Method call when back button is pressed
     */
    @Override
    public void onBackPressed() {

        if (ApplicationSession.getWalletRuntimeManager().getLasActivity().getType() != Activities.CWP_WALLET_MANAGER_MAIN){

            resetThisActivity();

            Intent intent = new Intent(this, SubAppActivity.class); // TODO : (LUIS) no puede irse a una sub app
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else{
            super.onBackPressed();
        }


    }


    /**
     * Method that loads the UI
     */
    protected void loadUI() {

        try
        {
            /**
             * Get current activity to paint
             */
            Activity activity = getActivityUsedType();
            /**
             * Get tabs to paint
             */
            TabStrip tabs = activity.getTabStrip();
            /**
             * Get activities fragment
             */
            Map<Fragments, com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment> fragments =activity.getFragments();
            /**
             * get actionBar to paint
             */
            TitleBar titleBar = activity.getTitleBar();
            /**
             * Get mainMenu to paint
             */
            MainMenu mainMenu= activity.getMainMenu();
            /**
             * Get NavigationDrawer to paint
             */
            SideMenu sideMenu = activity.getSideMenu();

            /**
             * Pick the layout
             */
            setMainLayout(sideMenu);

            /**
             * Paint tabs in layout
             */
            PagerSlidingTabStrip pagerSlidingTabStrip=((PagerSlidingTabStrip) findViewById(R.id.tabs));
            paintTabs(tabs, pagerSlidingTabStrip, activity);

            /**
             * Paint statusBar
             */
            paintStatusBar(activity.getStatusBar());
            /**
             * Paint titleBar
             */
            paintTitleBar(titleBar);

            /**
             * Paint a simgle fragment
             */
            if(tabs == null && fragments.size() > 1){
                initialisePaging();
            }else{
                /**
                 * Paint tabs
                 */
                setPagerTabs(pagerSlidingTabStrip);
            }
        }
        catch (Exception e) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Error in loadUI " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public Activity getActivityUsedType(){
        Activity activity=null;
        if(ACTIVITY_TYPE_SUB_APP==activityType){
            activity = ApplicationSession.getAppRuntimeMiddleware().getLasActivity();
        }else if(ACTIVITY_TYPE_WALLET==activityType){
            activity = ApplicationSession.getWalletRuntimeManager().getLasActivity();
        }
        return activity;
    }

    /**
     *
     * @param titleBar
     */
    private void paintTitleBar(TitleBar titleBar){
        try {
            TextView abTitle = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", "android"));
            if (titleBar != null) {

                String title = titleBar.getLabel();

                if (abTitle != null) {
                    abTitle.setTextColor(Color.WHITE);
                    abTitle.setTypeface(ApplicationSession.getDefaultTypeface());
                }
                getActionBar().setTitle(title);
                getActionBar().show();
                setActionBarProperties(abTitle,title);
            } else {
                getActionBar().hide();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param abTitle
     * @param title
     */
    private void setActionBarProperties(TextView abTitle, String title){
        SpannableString s = new SpannableString(title);


        s.setSpan(new MyTypefaceSpan(getApplicationContext(), "CaviarDreams.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        getActionBar().setTitle(s);

        // actionBar
        Drawable bg = getResources().getDrawable(R.drawable.transparent);
        bg.setVisible(false, false);
        Drawable wallpaper = getResources().getDrawable(R.drawable.transparent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(Color.parseColor(getActivityUsedType().getColor()));
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                //ld.setCallback(drawableCallback);
                Log.d(getClass().getSimpleName(),"Version incompatible con status bar");
            } else {
                getActionBar().setBackgroundDrawable(ld);
            }
        }
    }

    /**
     *
     * @param pagerSlidingTabStrip
     */
    private void setPagerTabs(PagerSlidingTabStrip pagerSlidingTabStrip){
        /**
         * Get pagerTabs
         */
        ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
        pagertabs.setVisibility(View.VISIBLE);

        /**
         * Making the pagerTab adapter
         */
        adapter = new TabsPagerAdapter(getSupportFragmentManager(),getApplicationContext(),activityType);
        pagertabs.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pagertabs.setPageMargin(pageMargin);
        /**
         * Put tabs in pagerSlidingTabsStrp
         */
        pagerSlidingTabStrip.setViewPager(pagertabs);
    }

    /**
     *
     * @param sidemenu
     */
    private void setMainLayout(SideMenu sidemenu){
        if(sidemenu != null){
            if(ACTIVITY_TYPE_SUB_APP==activityType){
                setContentView(R.layout.runtime_app_activity_runtime_navigator);
            }else if(ACTIVITY_TYPE_WALLET==activityType){
                setContentView(R.layout.runtime_app_wallet_runtime_navigator);
            }

            NavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

            NavigationDrawerFragment.setMenuVisibility(true);
            /**
             * Set up the navigationDrawer
             */
            NavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout), sidemenu);
        }

        /**
         * Paint layout without navigationDrawer
         */
        else{
            if(ACTIVITY_TYPE_SUB_APP==activityType){
                setContentView(R.layout.runtime_app_activity_runtime);
            }else if(ACTIVITY_TYPE_WALLET==activityType){
                setContentView(R.layout.runtime_app_wallet_runtime);
            }

        }
    }


    /**
     *
     * @param tabs
     * @param pagerSlidingTabStrip
     * @param activity
     */
    private void paintTabs(TabStrip tabs,PagerSlidingTabStrip pagerSlidingTabStrip,Activity activity){
        if(tabs == null)
            pagerSlidingTabStrip.setVisibility(View.INVISIBLE);
        else{
            pagerSlidingTabStrip.setVisibility(View.VISIBLE);
            Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/CaviarDreams.ttf");
            pagerSlidingTabStrip.setTypeface(tf,1 );
            pagerSlidingTabStrip.setDividerColor(Color.TRANSPARENT);

            // paint tabs color
            if (tabs.getTabsColor()!=null){
                pagerSlidingTabStrip.setBackgroundColor(Color.parseColor(activity.getTabStrip().getTabsColor()));
                //tabStrip.setDividerColor(Color.TRANSPARENT);
            }

            // paint tabs text color
            if(tabs.getTabsTextColor()!=null){
                pagerSlidingTabStrip.setTextColor(Color.parseColor(activity.getTabStrip().getTabsTextColor()));
            }

            //paint tabs indicate color
            if(tabs.getTabsIndicateColor()!=null){
                pagerSlidingTabStrip.setIndicatorColor(Color.parseColor(activity.getTabStrip().getTabsIndicateColor()));
            }
        }



        // put tabs font
        if (pagerSlidingTabStrip != null){
            pagerSlidingTabStrip.setTypeface(ApplicationSession.mDefaultTypeface, 1);
        }
    }

    /**
     * Method to set status bar color in different version of android
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void paintStatusBar(StatusBar statusBar){
        if(statusBar!=null) {
            if (statusBar.getColor() != null) {
                if (Build.VERSION.SDK_INT > 20) {
                    try {

                        Window window = this.getWindow();

                        // clear FLAG_TRANSLUCENT_STATUS flag:
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                        // finally change the color
                        Color color_status = new Color();
                        window.setStatusBarColor(color_status.parseColor(statusBar.getColor()));
                    } catch (Exception e) {
                        ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
                        Log.d("WalletActivity", "Sdk version not compatible with status bar color");
                    }
                }
            }
        }
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }
    public int getActivityType(){
        return activityType;
    }

    /**
     *  Method used to clean the screen
     */

    protected void resetThisActivity()
    {
        try
        {
            //clean page adapter
            ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
            if(adapter != null) pagertabs.removeAllViews();

            ViewPager viewpager = (ViewPager)super.findViewById(R.id.viewpager);
            viewpager.setVisibility(View.INVISIBLE);
            ViewPager pager = (ViewPager)super.findViewById(R.id.pager);
            pager.setVisibility(View.INVISIBLE);

            if(NavigationDrawerFragment!= null)
            {
                this.NavigationDrawerFragment.setMenuVisibility(false);
                NavigationDrawerFragment = null;
            }



            this.adapter = null;

            //this.tabStrip=null;
            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();


            this.PagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);

        }
        catch (Exception e) {

            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ALL_THE_PLATFORM, FermatException.wrapException(e));

            Toast.makeText(getApplicationContext(), "Error in CleanWindows " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        try {
            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
            Iterator<Map.Entry<Fragments, Fragment>> efragments = ApplicationSession.getAppRuntimeMiddleware().getLasActivity().getFragments().entrySet().iterator();
            boolean flag = false;
            while (efragments.hasNext()) {
                Map.Entry<Fragments, Fragment> fragmentEntry = efragments.next();

                Fragment fragment = (Fragment) fragmentEntry.getValue();
                Fragments type = fragment.getType();

                switch (type) {
                    case CWP_SHELL_LOGIN:
                        break;
                    case CWP_WALLET_MANAGER_MAIN:
                        fragments.add(android.support.v4.app.Fragment.instantiate(this, WalletDesktopFragment.class.getName()));
                        break;
                    case CWP_WALLET_MANAGER_SHOP:
                        break;
                    case CWP_SUB_APP_DEVELOPER:
                        fragments.add(android.support.v4.app.Fragment.instantiate(this, com.bitdubai.sub_app.manager.fragment.SubAppDesktopFragment.class.getName()));
                        break;

                    case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE:
                        fragments.add(android.support.v4.app.Fragment.instantiate(this, ReceiveFragment.class.getName()));
                        break;

                }

            }
            this.PagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);

            ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
            pager.setVisibility(View.VISIBLE);

            //set default page to show
            pager.setCurrentItem(0);

            pager.setAdapter(this.PagerAdapter);

            pager.setBackgroundResource(R.drawable.background_tiled_diagonal_light);


        } catch (Exception ex) {
            ApplicationSession.getErrorManager().reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, ex);
            Toast.makeText(getApplicationContext(), "Can't Load tabs: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
