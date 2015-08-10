package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;

/**
 * Created by Natalia on 30/03/2015.
 */
public class ActorAddressBookCryptoModuleDatabaseFactory implements DealsWithPluginDatabaseSystem {
    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager.
    * */
    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        try {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT KNOW PLUGIN DATABASE");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager.
    * */
    public Database createDatabase(UUID ownerId, UUID walletId) throws CantCreateDatabaseException {
        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        Database database;
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, walletId.toString());
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            FermatException e = new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE,FermatException.wrapException(cantCreateDatabaseException)," ","COULD NOT CREATE DATABASE");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            /**
             * First the Address book table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
            table.addColumn(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID, DatabaseDataType.STRING, 36, true);
            table.addColumn(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_DELIVERED_BY_ACTOR_ID, DatabaseDataType.STRING, 36, false);
            table.addColumn(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_DELIVERED_BY_ACTOR_TYPE, DatabaseDataType.STRING, 3, false);
            table.addColumn(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_DELIVERED_TO_ACTOR_ID, DatabaseDataType.STRING, 36, false);
            table.addColumn(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_DELIVERED_TO_ACTOR_TYPE, DatabaseDataType.STRING, 3, false);
            table.addColumn(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, DatabaseDataType.STRING, 33, false);
            table.addColumn(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, DatabaseDataType.STRING, 33, false);
            table.addColumn(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_TIME_STAMP, DatabaseDataType.LONG_INTEGER, 0, false);
            table.addIndex(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESSES_FIRST_KEY_COLUMN_NAME);

            try {
                ((DatabaseFactory) database).createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                FermatException e = new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE,FermatException.wrapException(cantCreateTableException)," ","COULD NOT CREATE DATABASE");
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateTableException);
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             * * *
             */
            FermatException e = new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE,FermatException.wrapException(invalidOwnerId)," ","COULD NOT KNOW ID");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, invalidOwnerId);
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }
}
