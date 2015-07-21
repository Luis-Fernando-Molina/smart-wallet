package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionState;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

import java.util.UUID;

/**
 * Created by eze on 2015.06.25..
 */
public class TransactionWrapper implements BitcoinWalletTransactionRecord {

    /*
     * BitcoinWalletTransactionRecord Interface member variables
     */
    private UUID transactionId;

    private UUID actorFromId;

    private UUID actorToId;

    private Actors actorFromType;

    private Actors actorToType;

    private String transactionHash;

    private CryptoAddress addressFrom;

    private CryptoAddress addressTo;

    private long amount;

    private long timestamp;

    private String memo;

    @Override
    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(CryptoAddress addressFrom) {
        this.addressFrom = addressFrom;
    }

    @Override
    public UUID getIdTransaction() {
        return this.transactionId;
    }


    public void setIdTransaction(UUID id) {
        this.transactionId = id;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(CryptoAddress addressTo) {
        this.addressTo = addressTo;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String getTransactionHash() {
        return transactionHash;
    }


    @Override
    public UUID getActorTo() {
        return this.actorToId;
    }

    public void setActorToId(UUID actorToId) {this.actorToId = actorToId;}

    @Override
    public UUID getActorFrom() {
        return this.actorFromId;
    }

    public void setActorFromId(UUID actorFromId){this.actorFromId = actorFromId;}

    @Override
    public Actors getActorToType() {return this.actorToType; }

    public void setActorToType(Actors actorToType) {this.actorToType = actorToType;}

    @Override
    public Actors getActorFromType() { return this.actorFromType;}

    public void setActorFromType(Actors actorFromType){ this.actorFromType = actorFromType; }

    public void setTransactionHash(String tramsactionHash) {
        this.transactionHash = tramsactionHash;
    }
}
