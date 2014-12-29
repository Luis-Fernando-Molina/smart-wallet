package com.bitdubai.smartwallet.walletframework;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.wallets.teens.SendAllFragment;

/**
 * Created by Natalia on 23/12/2014.
 */
public class SentHistoryActivity extends Activity {

    private PagerSlidingTabStrip tabs;
    private CharSequence mTitle = "Sent History";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_framework_activity_send_all);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new SendAllFragment())
                    .commit();
        }
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.WHITE);
        ((MyApplication) this.getApplication()).setActionBarProperties(this,getWindow(),tabs, getActionBar(), getResources(),abTitle, mTitle.toString());


            /* Custom Action Bar with Icon and Text */

        int tagId = MyApplication.getTagId();
        String[] contacts;


        contacts = new String[]{ "", "Luis Fernando Molina", "Guillermo Villanueva", "Pedro Perrotta", "Mariana Duyos"};


        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.wallets_teens_fragment_sent_history_action_bar,
                null);

        // Set up your ActionBar
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        TextView tv;

        tv = (TextView) actionBarLayout.findViewById(R.id.contact_name);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(contacts[tagId]);

        ImageView profile_picture = (ImageView) actionBarLayout.findViewById(R.id.profile_picture2);

        switch (tagId)
        {
            case 1:
                profile_picture.setImageResource(R.drawable.luis_profile_picture);
                break;
            case 2:
                profile_picture.setImageResource(R.drawable.guillermo_profile_picture);
                break;
            case 3:
                profile_picture.setImageResource(R.drawable.pedro_profile_picture);
                break;
            case 4:
                profile_picture.setImageResource(R.drawable.mariana_profile_picture);
                break;
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wallet_framework_activity_sent_all_menu, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void onSendToContactIconClicked(View v) {

        String tagId = v.getTag().toString();
        MyApplication.setChildId(tagId);
        Intent intent;
        intent = new Intent(this, SendToContactActivity.class);
        startActivity(intent);

        return;

    }

}
