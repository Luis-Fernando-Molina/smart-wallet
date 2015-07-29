package com.bitdubai.android_core.app;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
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

import com.bitdubai.android_core.app.common.version_1.FragmentFactory.WalletFragmentFactory;
import com.bitdubai.android_core.app.common.version_1.Sessions.WalletSessionManager;
import com.bitdubai.android_core.app.common.version_1.adapters.ScreenPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.classes.MyTypefaceSpan;
import com.bitdubai.android_core.app.common.version_1.navigation_drawer.NavigationDrawerFragment;
import com.bitdubai.android_core.app.common.version_1.tabbed_dialog.PagerSlidingTabStrip;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.enums.FermatFragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubAppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ReceiveFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.WalletDesktopFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Matias Furszyfer
 */

public class FermatActivity extends FragmentActivity{


    /**
     * Navigation menu
     */
    private NavigationDrawerFragment NavigationDrawerFragment;

    /**
     * Screen adapters
     */
    private TabsPagerAdapter adapter;
    private ScreenPagerAdapter screenPagerAdapter;

    /**
     * Activity type
     */
    private ActivityType activityType;

    /**
     *  Called when the activity is first created
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {

            /*
            *  Our Future code goes here ...
            */

        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
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
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
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
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(),"Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method that loads the UI
     */
    protected void loadBasicUI(Activity activity) {

        try
        {
            /**
             * Get tabs to paint
             */
            TabStrip tabs = activity.getTabStrip();
            /**
             * Get activities fragment
             */
            Map<FermatFragments, com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment> fragments =activity.getFragments();
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
            paintTabs(tabs, activity);

            /**
             * Paint statusBar
             */
            paintStatusBar(activity.getStatusBar());
            /**
             * Paint titleBar
             */
            paintTitleBar(titleBar,activity);

        }
        catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }
    }

    public Activity getActivityUsedType(){
        Activity activity=null;
        if(ActivityType.ACTIVITY_TYPE_SUB_APP==activityType){
            SubApp subApp = getAppRuntimeMiddleware().getLastSubApp();
            activity = subApp.getLastActivity();
        }else if(ActivityType.ACTIVITY_TYPE_WALLET==activityType){
            //activity = getWalletRuntimeManager().getLasActivity();
        }
        return activity;
    }

    /**
     *
     * @param titleBar
     */
    protected void paintTitleBar(TitleBar titleBar,Activity activity){
        try {
            TextView abTitle = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", "android"));
            if (titleBar != null) {

                String title = titleBar.getLabel();

                if (abTitle != null) {
                    abTitle.setTextColor(Color.WHITE);
                    abTitle.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/CaviarDreams.ttf"));
                }
                getActionBar().setTitle(title);
                getActionBar().show();
                setActionBarProperties(title,activity);
            } else {
                getActionBar().hide();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param title
     */
    protected void setActionBarProperties(String title,Activity activity){
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

            Drawable colorDrawable = new ColorDrawable(Color.parseColor(activity.getColor()));
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
    * Method used from a Wallet to paint tabs
    */
    protected void setPagerTabs(Wallet wallet,TabStrip tabStrip,WalletSession walletSession){
        /**
         * Get pager from xml
         */
        PagerSlidingTabStrip pagerSlidingTabStrip=((PagerSlidingTabStrip) findViewById(R.id.tabs));
        /**
         * Get pagerTabs
         */
        ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
        pagertabs.setVisibility(View.VISIBLE);

        /**
         * Making the pagerTab adapter
         */

        adapter = new TabsPagerAdapter(getSupportFragmentManager(),
                getApplicationContext(),
                WalletFragmentFactory.getFragmentFactoryByWalletType(wallet.getPublicKey()),
                tabStrip,
                walletSession);
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
     * Method used from a subApp to paint tabs
     */
    protected void setPagerTabs(SubApp subApp,TabStrip tabStrip){
        /**
         * Get pager from xml
         */
        PagerSlidingTabStrip pagerSlidingTabStrip=((PagerSlidingTabStrip) findViewById(R.id.tabs));
        /**
         * Get pagerTabs
         */
        ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
        pagertabs.setVisibility(View.VISIBLE);

        /**
         * Making the pagerTab adapter
         */
        adapter = new TabsPagerAdapter(getSupportFragmentManager(), getApplicationContext(), getAppRuntimeMiddleware().getLastSubApp().getLastActivity(), (ApplicationSession) getApplication(), getErrorManager());

        pagertabs.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pagertabs.setPageMargin(pageMargin);
        /**
         * Put tabs in pagerSlidingTabsStrp
         */
        pagerSlidingTabStrip.setViewPager(pagertabs);
    }

    private List<android.support.v4.app.Fragment> getWalletFragments(String walletType){
        List<android.support.v4.app.Fragment> lstWalletFragment= new ArrayList<android.support.v4.app.Fragment>();
        //tengo que traer el FragmentFactory dependiendo del tipo de wallet que es un enum ejemplo basic_wallet
        //WalletFragmentFactory.getFragmentFactoryByWalletType(getWalletRuntimeManager().getActivity(.))
        return null;
    }

    /**
     *  Select the xml based on the activity type
     * @param sidemenu
     */
    protected void setMainLayout(SideMenu sidemenu){
        if(sidemenu != null){
            if(ActivityType.ACTIVITY_TYPE_SUB_APP==activityType){
                setContentView(R.layout.runtime_app_activity_runtime_navigator);
            }else if(ActivityType.ACTIVITY_TYPE_WALLET==activityType){
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
            if(ActivityType.ACTIVITY_TYPE_SUB_APP==activityType){
                setContentView(R.layout.runtime_app_activity_runtime);
            }else if(ActivityType.ACTIVITY_TYPE_WALLET==activityType){
                setContentView(R.layout.runtime_app_wallet_runtime);
            }

        }
    }


    /**
     *
     * @param tabs
     * @param activity
     */
    protected void paintTabs(TabStrip tabs,Activity activity){
        /**
         * Get Pager from xml
         */
        PagerSlidingTabStrip pagerSlidingTabStrip=((PagerSlidingTabStrip) findViewById(R.id.tabs));

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
            pagerSlidingTabStrip.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/CaviarDreams.ttf"), 1);
        }
    }

    /**
     * Method to set status bar color in different version of android
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void paintStatusBar(StatusBar statusBar){
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
                        getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
                        Log.d("WalletActivity", "Sdk version not compatible with status bar color");
                    }
                }
            }
        }
    }

    /**
     *  Set the activity type
     * @param activityType Enum value
     */

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    /**
     *  Get the activity type
     * @return ActivityType enum value
     */

    public ActivityType getActivityType(){
        return activityType;
    }

    /**
     *  Method used to clean the screen
     */

    protected void resetThisActivity(){

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

            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();


            this.screenPagerAdapter = new ScreenPagerAdapter(getSupportFragmentManager(), fragments);

        }
        catch (Exception e) {

            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));

            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Initialise the fragments to be paged
     */
    protected void initialisePaging() {

        try {


            List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
            SubApp subApp = getAppRuntimeMiddleware().getHomeScreen();
            Activity activity = subApp.getLastActivity();

            for(FermatFragments key: activity.getFragments().keySet()) {
                Fragment fragment = activity.getFragments().get(key);

                switch (fragment.getType()) {
                    case CWP_SHELL_LOGIN:
                        break;
                    case CWP_WALLET_MANAGER_MAIN:
                        //DeveloperSubAppSession subAppSession = new DeveloperSubAppSession();
                        //Excepcion que no puede ser casteado  a WalletManagerManager
                        //WalletDesktopFragment walletDesktopFragment = WalletDesktopFragment.newInstance(0,getWalletManagerManager());
                        WalletManager manager= getWalletManager();
                        WalletDesktopFragment walletDesktopFragment = WalletDesktopFragment.newInstance(0,manager);
                        fragments.add(walletDesktopFragment);
                        //fragments.add(android.support.v4.app.Fragment.instantiate(this, WalletDesktopFragment.class.getName()));
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

            fragments.add(0,fragments.get(1));
            fragments.remove(2);

            /**
             * this pagerAdapter is the screenPagerAdapter with no tabs
             */
            screenPagerAdapter = new ScreenPagerAdapter(getSupportFragmentManager(), fragments);

            ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
            pager.setVisibility(View.VISIBLE);

            //set default page to show
            pager.setCurrentItem(0);

            pager.setAdapter(this.screenPagerAdapter);

            if (pager.getBackground() == null) {
                Drawable d = Drawable.createFromStream(getAssets().open("drawables/home3.png"), null);
                pager.setBackground(d);
            }


        } catch (Exception ex) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(ex));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }


    public WalletSessionManager getWalletSessionManager(){
        return ((ApplicationSession)getApplication()).getWalletSessionManager();
    }
    /**
     *  Get SubAppRuntimeManager from the fermat platform
     * @return  reference of SubAppRuntimeManager
     */

    public SubAppRuntimeManager getAppRuntimeMiddleware(){
        return (SubAppRuntimeManager) ((ApplicationSession)getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
    }

    /**
     * Get WalletRuntimeManager from the fermat platform
     * @return reference of WalletRuntimeManager
     */

    public WalletRuntimeManager getWalletRuntimeManager(){
        return (WalletRuntimeManager) ((ApplicationSession)getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_WALLET_RUNTIME_MODULE);
    }

    /**
     * Get WalletManager from the fermat platform
     * @return  reference of WalletManagerManager
     */

    public WalletManager getWalletManager(){
        return (WalletManager) ((ApplicationSession)getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_WALLET_MANAGER_MODULE);
    }

    /**
     * Get ErrorManager from the fermat platform
     * @return reference of ErrorManager
     */

    public ErrorManager getErrorManager(){
        return (ErrorManager) ((ApplicationSession)getApplication()).getFermatPlatform().getCorePlatformContext().getAddon(Addons.ERROR_MANAGER);
    }

    /**
     * Get WalletManagerManager from the fermat platform
     * @return  reference of WalletManagerManager
     */

    public WalletFactoryManager getWalletFactoryManager(){
        return (WalletFactoryManager) ((ApplicationSession)getApplication()).getFermatPlatform().getCorePlatformContext().getPlugin(Plugins.BITDUBAI_WALLET_FACTORY_MODULE);
    }


}
