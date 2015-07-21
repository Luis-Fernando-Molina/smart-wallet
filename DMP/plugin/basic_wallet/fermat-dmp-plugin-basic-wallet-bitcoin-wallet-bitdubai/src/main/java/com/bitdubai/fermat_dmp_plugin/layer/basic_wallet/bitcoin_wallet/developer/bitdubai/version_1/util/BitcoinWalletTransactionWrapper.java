package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;

import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.07.10..
 */
public class BitcoinWalletTransactionWrapper implements BitcoinWalletTransaction {

    private final UUID transactionId;
    private final String transactionHash;
    private final TransactionType transactionType;
    private final CryptoAddress addressFrom;
    private final CryptoAddress addressTo;
    private final UUID actorFrom;
    private final UUID actorTo;
    private final Actors actorFromType;
    private final Actors actorToType;
    private final BalanceType balanceType;
    private final long amount;
    private final long runningBookBalance;
    private final long runningAvailableBalance;
    private final long timeStamp;
    private final String memo;
    
    public BitcoinWalletTransactionWrapper(final UUID transactionId,
                                           final String transactionHash,
                                           final TransactionType transactionType,
                                           final CryptoAddress addressFrom,
                                           final CryptoAddress addressTo,
                                           final UUID actorFrom,
                                           final UUID actorTo,
                                           final Actors actorFromType,
                                           final Actors actorToType,
                                           final BalanceType balanceType,
                                           final long amount,
                                           final long runningBookBalance,
                                           final long runningAvailableBalance,
                                           final long timeStamp,
                                           final String memo) {
        this.transactionId = transactionId;
        this.transactionHash = transactionHash;
        this.transactionType = transactionType;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.actorFrom = actorFrom;
        this.actorTo = actorTo;
        this.actorFromType = actorFromType;
        this.actorToType = actorToType;
        this.balanceType = balanceType;
        this.amount = amount;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timeStamp = timeStamp;
        this.memo = memo;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public String getTransactionHash() {
        return transactionHash;
    }

    @Override
    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    @Override
    public UUID getActorTo() {
        return actorTo;
    }

    @Override
    public UUID getActorFrom() {
        return actorFrom;
    }

    @Override
    public Actors getActorToType() {
        return actorToType;
    }

    @Override
    public Actors getActorFromType() {
        return actorFromType;
    }

    @Override
    public BalanceType getBalanceType() {
        return balanceType;
    }

    @Override
    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public long getTimestamp() {
        return timeStamp;
    }

    @Override
    public long getAmount() { return amount; }

    @Override
    public long getRunningBookBalance() {
        return runningBookBalance;
    }

    @Override
    public long getRunningAvailableBalance() {
        return runningAvailableBalance;
    }

    @Override
    public String getMemo() {
        return memo;
    }
}
