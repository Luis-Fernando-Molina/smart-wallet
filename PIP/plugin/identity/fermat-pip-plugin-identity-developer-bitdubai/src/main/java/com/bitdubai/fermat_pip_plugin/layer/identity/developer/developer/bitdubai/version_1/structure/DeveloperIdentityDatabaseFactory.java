package com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 *  The Class  <code>com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.DeveloperIdentityDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Ezequiel Postan - (ezequiel.postan@gmail.com) on 14/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperIdentityDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public DeveloperIdentityDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    protected Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;

            /**
             * Create Developer table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, DeveloperIdentityDatabaseConstants.DEVELOPER_TABLE_NAME);

            table.addColumn(DeveloperIdentityDatabaseConstants.DEVELOPER_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.TRUE);
            table.addColumn(DeveloperIdentityDatabaseConstants.DEVELOPER_DEVELOPER_PRIVATE_KEY_COLUMN_NAME, DatabaseDataType.STRING, 64, Boolean.FALSE);
            table.addColumn(DeveloperIdentityDatabaseConstants.DEVELOPER_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(DeveloperIdentityDatabaseConstants.DEVELOPER_DEVELOPER_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(DeveloperIdentityDatabaseConstants.DEVELOPER_FIRST_KEY_COLUMN);

            try {
                //Create the table
                ((DatabaseFactory) database).createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}

