package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.executors;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.ActorAddressBookNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantGenerateTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.UnexpectedTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.*;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.TransactionWrapper;

/**
 * Created by jorgegonzalez on 2015.07.08..
 */
public class BitcoinBasicWalletTransactionExecutor implements TransactionExecutor {

    private BitcoinWalletWallet bitcoinWallet;
    private ActorAddressBookManager actorAddressBookManager;

    public BitcoinBasicWalletTransactionExecutor(final BitcoinWalletWallet bitcoinWallet, final ActorAddressBookManager actorAddressBookManager){
        this.bitcoinWallet = bitcoinWallet;
        this.actorAddressBookManager = actorAddressBookManager;
    }

    @Override
    public void executeTransaction(Transaction<CryptoTransaction> transaction) throws  CantRegisterCreditException, CantRegisterDebitException, UnexpectedTransactionException {
        switch (transaction.getInformation().getCryptoStatus()){
            case ON_CRYPTO_NETWORK:
                processOnCryptoNetworkTransaction(transaction);
                break;
            case ON_BLOCKCHAIN:
                processOnBlockChainTransaction(transaction);
                break;
            //todo ezequiel, cambie de Reversed a REVERSED_ON_CRYPTO_NETWORK
            case REVERSED_ON_CRYPTO_NETWORK:
                processReversedOnCryptoNetworkTransaction(transaction);
                break;
            case REVERSED_ON_BLOCKCHAIN:
                processReversedOnBlockchainTransaction(transaction);
                break;
            default:
                throw new UnexpectedTransactionException("El crypto status no es esperado",null,"El cryptoStatus es: "+ transaction.getInformation().getCryptoStatus().getCode(),"");
        }
    }

    private void processOnCryptoNetworkTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterCreditException {
        try {
            BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction, TransactionType.CREDIT);
            bitcoinWallet.getBookBalance().credit(record);
        } catch (CantGenerateTransactionException e) {
            throw new CantRegisterCreditException("I couldn't generate the transaction",e,"","");
        }
    }

    private void processOnBlockChainTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterCreditException{
        try {
            BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction, TransactionType.CREDIT);
            bitcoinWallet.getAvailableBalance().credit(record);
        } catch (CantGenerateTransactionException e) {
            throw new CantRegisterCreditException("I couldn't generate the transaction",e,"","");
        }
    }

    private void processReversedOnCryptoNetworkTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterDebitException {
        try {
            BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction, TransactionType.DEBIT);
            bitcoinWallet.getBookBalance().debit(record);
        } catch (CantGenerateTransactionException e) {
            throw new CantRegisterDebitException("I couldn't generate the transaction",e,"","");
        }
    }

    private void processReversedOnBlockchainTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterDebitException {
        try {
            BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction, TransactionType.DEBIT);
            bitcoinWallet.getAvailableBalance().debit(record);
        } catch (CantGenerateTransactionException e) {
            throw new CantRegisterDebitException("I couldn't generate the transaction",e,"","");
        }
    }


    private BitcoinWalletTransactionRecord generateBitcoinTransaction(final Transaction<CryptoTransaction> transaction, final TransactionType transactionType) throws CantGenerateTransactionException {

        try {
            CryptoTransaction cryptoTransaction = transaction.getInformation();

            ActorAddressBookRegistry actorAddressBookRegistry = this.actorAddressBookManager.getActorAddressBookRegistry();
            ActorAddressBookRecord actorAddressBookRecord = actorAddressBookRegistry.getActorAddressBookByCryptoAddress(cryptoTransaction.getAddressTo());


            long timestamp = transaction.getTimestamp();
            TransactionWrapper bitcoinWalletTransactionRecord = new TransactionWrapper();

            bitcoinWalletTransactionRecord.setIdTransaction(transaction.getTransactionID());
            bitcoinWalletTransactionRecord.setTransactionHash(cryptoTransaction.getTransactionHash());
            bitcoinWalletTransactionRecord.setAddressFrom(cryptoTransaction.getAddressFrom());
            bitcoinWalletTransactionRecord.setAddressTo(cryptoTransaction.getAddressTo());
            bitcoinWalletTransactionRecord.setAmount(cryptoTransaction.getCryptoAmount());
            bitcoinWalletTransactionRecord.setTimestamp(timestamp);
            bitcoinWalletTransactionRecord.setMemo("No information");

            bitcoinWalletTransactionRecord.setActorFromId(actorAddressBookRecord.getDeliveredToActorId());
            bitcoinWalletTransactionRecord.setActorFromType(actorAddressBookRecord.getDeliveredToActorType());
            bitcoinWalletTransactionRecord.setActorToId(actorAddressBookRecord.getDeliveredByActorId());
            bitcoinWalletTransactionRecord.setActorToType(actorAddressBookRecord.getDeliveredByActorType());

            return bitcoinWalletTransactionRecord;

        } catch (CantGetActorAddressBookRegistryException e) {
            throw new CantGenerateTransactionException("I couldn't get actor address book registry",e,"","");
        } catch (CantGetActorAddressBookException e) {
            throw new CantGenerateTransactionException("I couldn't get actor address book",e,"","");
        } catch (ActorAddressBookNotFoundException e) {
            throw new CantGenerateTransactionException("I couldn't find the actor",e,"","");
        }
    }
}
