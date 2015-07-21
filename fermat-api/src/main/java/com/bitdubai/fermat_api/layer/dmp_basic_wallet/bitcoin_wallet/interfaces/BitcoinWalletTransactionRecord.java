package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionState;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWalletTransactionRecord {

    public CryptoAddress getAddressFrom();

    public UUID getIdTransaction();

    public CryptoAddress getAddressTo();

    public long getAmount();

    public long getTimestamp();

    public String getMemo();

    public String getTransactionHash();

    public UUID getActorTo();

    public UUID getActorFrom();

    public Actors getActorToType();

    public Actors getActorFromType();
}
