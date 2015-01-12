package com.bitdubai.smartwallet.ui.os.android.app.shell.version_1.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.smartwallet.ui.os.android.app.subapp.shop.version_1.activity.ShopActivity;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity.FrameworkActivity;
import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;

import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_manager.version_1.fragment.WalletDesktopFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_manager.version_1.fragment.ShopDesktopFragment;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_store.version_1.activity.StoreFrontActivity;

import android.support.v4.app.FragmentActivity;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;

public class DesktopActivity extends FragmentActivity {


    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shell_activity_wallet_desktop);
      /*  getActionBar().hide();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new WalletDesktopFragment())
                    .commit();
        }*/

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        ((MyApplication) this.getApplication()).setDefaultTypeface(tf);

        this.initialisePaging();
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        try
        {
            List<Fragment> fragments = new Vector<Fragment>();
            fragments.add(Fragment.instantiate(this, WalletDesktopFragment.class.getName()));
            fragments.add(Fragment.instantiate(this, ShopDesktopFragment.class.getName()));

            this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
            //
            ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
            pager.setAdapter(this.mPagerAdapter);

        }catch (Exception ex) {
          String strError = ex.getMessage();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wallet_manager_desktop_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_wallet_store) {

            Intent intent;
            intent = new Intent(this, StoreFrontActivity.class);
            startActivity(intent);

            return true;
        }

        if (id == R.id.action_shop) {

            Intent intent;
            intent = new Intent(this, ShopActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onWalletClicked(View v) {
        String tagId="3";

     tagId = v.getTag().toString();

        if (Integer.parseInt(tagId) > 4)
        {
            Toast.makeText(getApplicationContext(), "This part of the prototype is not ready yet",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent intent;

            intent = new Intent(this, FrameworkActivity.class);

            // Intent i=getIntent();
            // String walletId =i.getStringExtra( "Wallet Id");


            ((MyApplication) this.getApplication()).setWalletId(Integer.parseInt(tagId));

            // intent.putExtra("Wallet Id", tag );
            startActivity(intent);
        }
    }


    public void onShopClicked(View v) {
        String tagId="";

        tagId = v.getTag().toString();
            Intent intent;
            intent = new Intent(this, ShopActivity.class);

           // ((MyApplication) this.getApplication()).setWalletId(Integer.parseInt(tagId));

            // intent.putExtra("Wallet Id", tag );
            startActivity(intent);



    }


}
