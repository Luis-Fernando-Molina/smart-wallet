package com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces_milestone2;

/**
 * This class represents a predicate to filter the items in the wallet catalogue
 *
 * @author EzequielPostan
 */
public interface WalletCatalogueFilter {

    /**
     * This method is applied on a catalogue item to check if it satisfies a predicate
     *
     * @param newCatalogueItem The catalogue item to analise
     * @return true if the newCatalogue item satisfies the predicate. False in the other case.
     */
    boolean satisfyFilter(WalletStoreCatalogueItem newCatalogueItem);
}
