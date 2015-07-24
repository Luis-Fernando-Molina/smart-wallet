package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentResult;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 19/06/15.
 */
public class SendFragment extends Fragment{

    /**
     * Wallet session
     */
    WalletSession walletSession;

    private static final String ARG_POSITION = "position";
    View rootView;
    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    UUID user_id = UUID.fromString("afd0647a-87de-4c56-9bc9-be736e0c5059");

    Typeface tf ;


    Bundle savedInstanceState;
    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;
    CryptoWallet cryptoWallet;
    private ErrorManager errorManager;

    private AutoCompleteTextView autocompleteContacts;
    private WalletContactListAdapter adapter;

    private EditText editAddress;
    private EditText editAmount;
    private EditText editNotes;

    private LinearLayout linear_notes;
    private LinearLayout linear_send;


    public static SendFragment newInstance(int position,WalletSession walletSession) {
        SendFragment f = new SendFragment();
        f.setWalletSession(walletSession);
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        tf=Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");


        cryptoWalletManager = walletSession.getCryptoWalletManager();
        errorManager = walletSession.getErrorManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage("CantGetCryptoWalletException- " + e.getMessage());
            ;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_send, container, false);

        try {
            editAddress = (EditText) rootView.findViewById(R.id.address);
            editAddress.setTypeface(tf);
            editAmount = (EditText) rootView.findViewById(R.id.amount);
            editAmount.setTypeface(tf);
            editNotes = (EditText) rootView.findViewById(R.id.notes);
            editNotes.setTypeface(tf);
            linear_notes = (LinearLayout) rootView.findViewById(R.id.linear_notes);
            linear_send = (LinearLayout) rootView.findViewById(R.id.linear_send);

            autocompleteContacts = (AutoCompleteTextView) rootView.findViewById(R.id.contact_name);
            adapter = new WalletContactListAdapter(getActivity(), R.layout.wallets_bitcoin_fragment_contacts_list_item, getWalletContactList());
            autocompleteContacts.setAdapter(adapter);
            autocompleteContacts.setTypeface(tf);

            autocompleteContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    WalletContact walletContact = (WalletContact) arg0.getItemAtPosition(position);
                    editAddress.setText(walletContact.address);
                }
            });

            try {
                long availableBalance = cryptoWallet.getAvailableBalance(wallet_id);
                editAmount.setHint("available funds: "+availableBalance+ " bits");
            } catch (Exception ex) {

            }

            ImageView b = (ImageView) rootView.findViewById(R.id.send_button);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendCrypto();
                }
            });

            editAddress.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if (validateAddress(editAddress.getText().toString()) != null) {
                        editAddress.setTextColor(Color.parseColor("#72af9c"));
                    } else {
                        editAddress.setTextColor(Color.parseColor("#b46a54"));
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });

            // add_contact button definition
            ImageView pasteFromClipboardButton = (ImageView) rootView.findViewById(R.id.paste_from_clipboard_btn);
            pasteFromClipboardButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pasteFromClipboard(rootView);
                }
            });

            editAmount.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    try {
                        Long amount = Long.parseLong(editAmount.getText().toString());
                        if (amount > 0) {
                            linear_notes.setVisibility(View.VISIBLE);
                            linear_send.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        try {
                            long actualBalance = cryptoWallet.getAvailableBalance(wallet_id);
                            editAmount.setHint("Available amount: "+actualBalance+ " bits");
                        } catch (Exception ex) {

                        }

                        linear_notes.setVisibility(View.GONE);
                        linear_send.setVisibility(View.GONE);
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            ImageView scanImage = (ImageView) rootView.findViewById(R.id.scan_qr);

            scanImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator integrator = new IntentIntegrator(getActivity(), (EditText) rootView.findViewById(R.id.address));
                    integrator.initiateScan();
                }
            });

        } catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(" CreateView Exception- " + e.getMessage());

        }
        return rootView;
    }

    private List<WalletContact> getWalletContactList() {
        List<WalletContact> contacts = new ArrayList<>();
        try {
            List<WalletContactRecord> walletContactRecords = cryptoWallet.listWalletContacts(wallet_id);
            for (WalletContactRecord wcr : walletContactRecords) {
                contacts.add(new WalletContact(wcr.getActorName(), wcr.getReceivedCryptoAddress().getAddress(), wcr.getContactId()));
            }
        } catch (CantGetAllWalletContactsException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage("CantGetAllWalletContactsException- " + e.getMessage());
        }
        return contacts;
    }

    private void sendCrypto() {

        CryptoAddress validAddress = validateAddress(editAddress.getText().toString());
        if (validAddress != null) {
            EditText amount = (EditText) rootView.findViewById(R.id.amount);
            try {
                //TODO que pasa si no puedo crear el user?
                WalletContactRecord walletContactRecord = cryptoWallet.createWalletContact(validAddress, autocompleteContacts.getText().toString(), Actors.EXTRA_USER, ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET, wallet_id);

                // TODO harcoded deliveredbyactorid
                cryptoWallet.send(Long.parseLong(amount.getText().toString()), validAddress, editNotes.getText().toString(), wallet_id, user_id, Actors.INTRA_USER, walletContactRecord.getActorId(), walletContactRecord.getActorType());

                Toast.makeText(getActivity(), "Send OK", Toast.LENGTH_LONG).show();
            } catch (InsufficientFundsException e) {
                Toast.makeText(getActivity(), "Insufficient funds", Toast.LENGTH_LONG).show();

            } catch (CantSendCryptoException e) {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage("Error send satoshis - " + e.getMessage());
            } catch (CantCreateWalletContactException e) {
                // TODO que hacer si no puedo crear el contacto? igual envio el dinero?
                //Toast.makeText(this.getActivity(), "Can't create new contact", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "Invalid Address", Toast.LENGTH_LONG).show();

        }
    }

    private void pasteFromClipboard(View rootView) {
        ClipboardManager clipboard = (ClipboardManager) rootView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        // Gets the ID of the "paste" menu item
        ImageView mPasteItem = (ImageView) rootView.findViewById(R.id.paste_from_clipboard_btn);
        if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            mPasteItem.setEnabled(true);
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            EditText editText = (EditText) rootView.findViewById(R.id.address);
            CryptoAddress validAddress = validateAddress(item.getText().toString());
            if (validAddress != null) {
                editText.setText(validAddress.getAddress());
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Cannot find an address in the clipboard text.\n\n"+item.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // This enables the paste menu item, since the clipboard contains plain text.
            mPasteItem.setEnabled(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (data != null) {
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

                //get references to edit text control to show scand result
                EditText textResult = IntentIntegrator.getTextResult();
                if (scanResult != null) {

                    // handle scan result
                    String contantsString = scanResult.getContents() == null ? "0" : scanResult.getContents();
                    if (contantsString.equalsIgnoreCase("0")) {
                        Toast.makeText(this.getActivity(), "Problem to get contact address", Toast.LENGTH_LONG).show();

                    } else {
                        //load into text address
                        textResult.setText(contantsString);
                    }
                } else {
                    Toast.makeText(this.getActivity(), "Problem to scan the barcode.", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(" Load address Exception- " + e.getMessage());
        }
    }

    private CryptoAddress validateAddress(String strToValidate) {
        String[] tokens = strToValidate.split("-|\\.|:|,|;| ");

        CryptoAddress cryptoAddress = new CryptoAddress(null, CryptoCurrency.BITCOIN);
        for (String token : tokens) {
            token = token.trim();
            if (token.length() > 25 && token.length() < 40) {
                cryptoAddress.setAddress(token);
                if (cryptoWallet.isValidAddress(cryptoAddress)) {
                    return cryptoAddress;
                }
            }
        }
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //show alert
    private void showMessage(String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
            }
        });
        //alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }

    public void setWalletSession(WalletSession walletSession) {
        this.walletSession = walletSession;
    }
}
