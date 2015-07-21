package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;

import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.list.TransactionListView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by natalia on 17/06/15.
 */
public class TransactionsFragment2 extends Fragment {

    private static final String ARG_POSITION = "position";
    View rootView;
    public static Typeface mDefaultTypeface;

    //List TRANSACTIONS = new ArrayList<Transactions>();

    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;
    private static Platform platform = new Platform();
    CryptoWallet cryptoWallet;
    private ErrorManager errorManager;

    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");


    List<CryptoWalletTransaction> lstTransactions = new ArrayList<CryptoWalletTransaction>();

    ListView listViewTransactions;
    SwipeRefreshLayout swipeRefreshLayout;
    TransactionArrayAdapter transactionArrayAdapter;

    private int pointerOffset = 0;
    private int cantTransactions = 10;


    //Type face font
    Typeface tf;


    public static TransactionsFragment2 newInstance(int position) {
        TransactionsFragment2 f = new TransactionsFragment2();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        cryptoWalletManager = platform.getCryptoWalletManager();
        errorManager = platform.getErrorManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage("CantGetCryptoWalletException- " + e.getMessage());

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_transactions, container, false);
        // Get ListView object from xml
        listViewTransactions = (ListView) rootView.findViewById(R.id.transactionlist);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        //adapter.
        // Set the emptyView to the ListView


        /*TextView textViewEmptyListView = (TextView) rootView.findViewById(R.id.emptyElement);


        if(lstTransactions.isEmpty()){
            textViewEmptyListView.setTypeface(tf);
            listViewTransactions.setEmptyView(textViewEmptyListView);
        }*/





        // Create the adapter to convert the array to views

        List<TransactionHolder> lstHolders = new ArrayList<TransactionHolder>();
        try {
            for(CryptoWalletTransaction cryptoWalletTransaction:cryptoWallet.getTransactions(cantTransactions,pointerOffset, wallet_id)){
                TransactionHolder transactionHolder = new TransactionHolder(TransactionListView.TYPE_TRANSACTION,cryptoWalletTransaction);
                lstHolders.add(transactionHolder);
            }
        } catch (CantGetTransactionsException e) {
            e.printStackTrace();
        }

        //TODO: esto iria antes, se tiene que cargar la lista primero, despues se depura por credito o debito y despues se lo pasa al holder
        //BalanceType balanceType = Platform.TYPE_BALANCE_TYPE_SELECTED;
        //lstTransactions=showTransactionListSelected(lstTransactions,balanceType);

        //transactionArrayAdapter = new TransactionArrayAdapter(this.getActivity(),lstTransactions); //showTransactionListSelected(lstTransactions, Platform.TYPE_BALANCE_TYPE_SELECTED));
        TransactionArrayAdapter transactionArrayAdapterBasic = new TransactionArrayAdapter(getActivity(),lstHolders);

        //loadNewTransactions();

        // Assign adapter to ListView
        listViewTransactions.setAdapter(transactionArrayAdapter);
        //swipeRefreshLayout.setColorSchemeColors(android.R.color);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTransactionsContent();
            }
        });

        /*listViewTransactions.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //view.
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //showMessage("holas");
                Toast.makeText(getActivity(),"visible item count:"+visibleItemCount+"\n"
                        +"first vible item:"+firstVisibleItem+"\n"
                        +"total item count:"+totalItemCount,Toast.LENGTH_SHORT).show();

            }
        });*/

        return rootView;
    }

    private List<CryptoWalletTransaction> showTransactionListSelected(List<CryptoWalletTransaction> lstTransactions, BalanceType balanceType) {
        List<CryptoWalletTransaction> lstToShow = new ArrayList<CryptoWalletTransaction>();
        for (CryptoWalletTransaction t : lstTransactions) {
            if (t.getBitcoinWalletTransaction().getBalanceType()==(balanceType)) {
                lstToShow.add(t);
            }
        }
        return lstToShow;
    }


    private void refreshTransactionsContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNewTransactions();
                //transactionArrayAdapter = new TransactionArrayAdapter(getActivity(),lstTransactions);
                //listViewTransactions.setAdapter(transactionArrayAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);




    }

    private void loadNewTransactions(){
        //List<Transactions> lstAux;
        if(lstTransactions.isEmpty())
            //Toast.makeText(getActivity(),"No transactions",Toast.LENGTH_SHORT).show();
            try {
                List<CryptoWalletTransaction> lst =cryptoWallet.getTransactions(cantTransactions, pointerOffset, wallet_id);
                for(CryptoWalletTransaction transaction: lst){
                    lstTransactions.add(0, transaction);
                }

            } catch (CantGetTransactionsException e)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage("Cant Get Transactions Exception- " + e.getMessage());
            }
            catch(Exception ex)
            {
                showMessage("Unexpected error get Transactions - " + ex.getMessage());
            }
        else{
            try {
                List<CryptoWalletTransaction> lst =cryptoWallet.getTransactions(cantTransactions,pointerOffset, wallet_id);
                for(CryptoWalletTransaction transaction: lst){
                   lstTransactions.add(0, transaction);
                }

            } catch (CantGetTransactionsException e)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage("Cant Get Transactions Exception- " + e.getMessage());
            }
            catch(Exception ex)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                showMessage("Unexpected error get Transactions - " + ex.getMessage());

            }
        }
        pointerOffset=lstTransactions.size();

        showTransactionListSelected(lstTransactions,Platform.TYPE_BALANCE_TYPE_SELECTED);

        transactionArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    public class TransactionArrayAdapter extends ArrayAdapter<TransactionHolder> {

        public TransactionArrayAdapter(Context context, List<TransactionHolder> lstTrasactions) {
            super(context, 0, lstTrasactions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            TransactionListView item = getItem(position);
            //get inflater
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View listItemView = convertView;

            //if view exist
            if (null == convertView) {
                //Si no existe, entonces inflarlo con image_list_view.xml

                if (item.getType() == TransactionListView.TYPE_TRANSACTION) {
                    listItemView = inflater.inflate(
                            R.layout.wallets_bitcoin_fragment_transactions_list_items2,
                            parent,
                            false);


                    //TODO: fijarse si obtiene el objeto
                    CryptoWalletTransaction cryptoWalletTransaction = (CryptoWalletTransaction) item.getObject();

                    //Get TextViews
                    TextView txtView_contact_name = (TextView) listItemView.findViewById(R.id.textView_contact_name);
                    TextView txtView_amount = (TextView) listItemView.findViewById(R.id.textView_amount);
                    //TextView txtView_notes = (TextView)listItemView.findViewById(R.id.textView_notes);
                    TextView txtView_when = (TextView) listItemView.findViewById(R.id.textView_time);
                    //TextView txtView_type = (TextView)listItemView.findViewById(R.id.textView_status);

                    //Getting Transactions instance at the current position


                    txtView_contact_name.setText(cryptoWalletTransaction.getInvolvedActorName());
                    if (Platform.TYPE_BALANCE_TYPE_SELECTED == BalanceType.AVAILABLE) {
                        txtView_amount.setText(Platform.formatBalanceString(cryptoWalletTransaction.getBitcoinWalletTransaction().getRunningAvailableBalance()));
                    } else if (Platform.TYPE_BALANCE_TYPE_SELECTED == BalanceType.BOOK)
                        txtView_amount.setText(Platform.formatBalanceString(cryptoWalletTransaction.getBitcoinWalletTransaction().getRunningBookBalance()));

            /*if(item.getBitcoinWalletTransaction().getMemo()!=null){
                txtView_notes.setText(item.getBitcoinWalletTransaction().getMemo());
            }
            */

                    txtView_when.setText((DateFormat.format("hh:mm:ss", cryptoWalletTransaction.getBitcoinWalletTransaction().getTimestamp())));
                    //txtView_type.setText(item.getBitcoinWalletTransaction().getTransactionType().toString());

                    TextView textView_type = (TextView) listItemView.findViewById(R.id.textView_type);
                    textView_type.setText("Received");

                } else if (item.getType() == TransactionListView.TYPE_TRANSACTION) {
                    listItemView = inflater.inflate(
                            R.layout.header,
                            parent,
                            false);

                    TextView separator = (TextView) listItemView.findViewById(R.id.separator);
                    separator.setText("Today");
                }




            }

            return listItemView;
        }
    }


    private void showMessage(String text){
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
                Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
            }
        });
        //alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }


    public class TransactionHolder implements TransactionListView{

        private int type;
        private Object object;

        TransactionHolder(int type,Object object){
            this.type=type;
            this.object=object;
        }

        @Override
        public int getType() {
            return type;
        }

        @Override
        public Object getObject() {
            return object;
        }
    }

}
