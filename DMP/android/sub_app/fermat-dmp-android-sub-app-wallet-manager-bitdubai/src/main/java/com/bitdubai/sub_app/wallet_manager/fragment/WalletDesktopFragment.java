package com.bitdubai.sub_app.wallet_manager.fragment;


import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Matias Furszyfer
 */


public class WalletDesktopFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    Typeface tf;

    private WalletManager walletManager;

    private List<InstalledWallet> lstInstalledWallet;

    public static WalletDesktopFragment newInstance(int position,WalletManager walletManager) {
        WalletDesktopFragment f = new WalletDesktopFragment();
        f.setWalletManager(walletManager);
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        setHasOptionsMenu(true);

        if(walletManager!=null) lstInstalledWallet=walletManager.getUserWallets();


        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(6);
        } else {
            gridView.setNumColumns(4);
        }

        AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.shell_wallet_desktop_front_grid_item, lstInstalledWallet);
        _adpatrer.notifyDataSetChanged();
        gridView.setAdapter(_adpatrer);


     /*    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent;
                intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);

                return ;
            }
        });*/


        return gridView;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.wallet_manager_desktop_activity_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //if(id == R.id.action_search){
        //    Toast.makeText(getActivity(), "holaa", Toast.LENGTH_LONG);
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     *  Set Wallet manager plugin
     *
     * @param walletManager
     */
    public void setWalletManager(WalletManager walletManager) {
        this.walletManager = walletManager;
    }




    public class AppListAdapter extends ArrayAdapter<InstalledWallet> {


        public AppListAdapter(Context context, int textViewResourceId, List<InstalledWallet> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final InstalledWallet installedWallet = getItem(position);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.shell_wallet_desktop_front_grid_item, parent, false);
                holder = new ViewHolder();



                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.companyTextView.setText(installedWallet.getWalletName());
            holder.companyTextView.setTypeface(tf, Typeface.BOLD);


            LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.wallet_3);

            //Hardcodeado hasta que esté el wallet resources
            switch (installedWallet.getWalletIcon())
            {

                case "reference_wallet_icon":
                    holder.imageView.setImageResource(R.drawable.fermat);
                    holder.imageView.setTag("WalletBitcoinActivity|4");
                    linearLayout.setTag("WalletBitcoinActivity|4");

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //set the next fragment and params
                             ((FermatScreenSwapper) getActivity()).selectWallet("WalletBitcoinActivity", installedWallet);

                        }
                    });

                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectWallet("WalletBitcoinActivity", installedWallet);

                        }
                    });

                    break;


            }


            return convertView;
        }

        /**
         * ViewHolder.
         */
        private class ViewHolder {



            public ImageView imageView;
            public TextView companyTextView;


        }

    }




}

