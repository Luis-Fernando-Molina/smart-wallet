package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_settings.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_settings.developer.bitdubai.version_1.exceptions.CantLoadSettingsFileException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.UUID;



/**
 * Created by natalia on 21/07/15.
 */
public class WalletSettingsSettings implements WalletSettings {

    private final String WALLET_SETTINGS_FILE_NAME = "walletsSettings";

    private PluginFileSystem pluginFileSystem;
    private UUID pluginId;
    private ErrorManager errorManager;
    private PluginTextFile walletSettingsXml;
    private UUID walletIdInTheDevice;


    /**
     * Constructor
     *
     */

    public WalletSettingsSettings(UUID walletIdInTheDevice,PluginFileSystem pluginFileSystem, UUID pluginId,ErrorManager errorManager){

        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
        this.walletIdInTheDevice = walletIdInTheDevice;

    }


    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException {

        SAXBuilder builder = new SAXBuilder();

        /**
         * load file content
         */
        try {
            loadSettingsFile();

        } catch (CantLoadSettingsFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantGetDefaultLanguageException("Error load settings file",e, "File name: "+ WALLET_SETTINGS_FILE_NAME,"");

        }

        /**
         * Get language settings on xml
         */
        try {

            Document document = (Document) builder.build(new StringReader(this.walletSettingsXml.getContent()));

            Element rootNode = document.getRootElement();

                /**
                 * Check wallet id is equals to this wallet process
                 */
                if(rootNode.getChildText("wallet_id").equals(this.walletIdInTheDevice.toString()))
                {
                    return UUID.fromString(rootNode.getChildText("language_id").toString());
                }else
                {
                    //error invalid wallet id
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, null);

                    throw new CantGetDefaultLanguageException("Error write settings data, the wallet Ids mismatched",null, "","Xml carrupted");

                }


        } catch(JDOMException|IOException e)
        {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantGetDefaultLanguageException("Error parse settings file to xml object",e, "","Xml bad format");

        }

    }

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException {
        SAXBuilder builder = new SAXBuilder();

        /**
         * load file content
         */
        try {
            loadSettingsFile();

        } catch (CantLoadSettingsFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantGetDefaultSkinException("Error load settings file",e, "File name: "+ WALLET_SETTINGS_FILE_NAME,"");

        }

        /**
         * Get language settings on xml
         */
        try {

            Document document = (Document) builder.build(new StringReader(this.walletSettingsXml.getContent()));

            Element rootNode = document.getRootElement();

                /**
                 * Check wallet id is equals to this wallet process
                 */
                if(rootNode.getChildText("wallet_id").equals(this.walletIdInTheDevice.toString()))
                {
                    return UUID.fromString(rootNode.getChildText("skin_id").toString());
                }else
                {
                    //error invalid wallet id
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, null);

                    throw new CantGetDefaultSkinException("Error write settings data, the wallet Ids mismatched",null, "","Xml carrupted");

                }

        } catch(JDOMException|IOException e)
        {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantGetDefaultSkinException("Error parse settings file to xml object",e, "","Xml bad format");

        }

    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException {

        SAXBuilder builder = new SAXBuilder();

        /**
         * load file content
         */
        try {
            loadSettingsFile();

        } catch (CantLoadSettingsFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantSetDefaultLanguageException("Error load settings file",e, "File name: "+ WALLET_SETTINGS_FILE_NAME,"");

        }

        /**
         * Get language settings on xml
         */
        try {

            Document document = (Document) builder.build(new StringReader(this.walletSettingsXml.getContent()));

            Element rootNode = document.getRootElement();

                /**
                 * Check wallet id is equals to this wallet process
                 */
                if(rootNode.getChildText("wallet_id").equals(this.walletIdInTheDevice.toString()))
                {
                    rootNode.getChild("language_id").setText(languageId.toString());
                }else
                {
                    //error invalid wallet id
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, null);

                    throw new CantSetDefaultLanguageException("Error write settings data, the wallet Ids mismatched",null, "","Xml carrupted");

                }

            XMLOutputter xmOut=new XMLOutputter();

            walletSettingsXml.setContent(xmOut.outputString(document));

        } catch(JDOMException|IOException e)
        {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantSetDefaultLanguageException("Error parse settings file to xml object",e, "","Xml bad format");

        }

        /**
         * persist xml file
         */

        try {
            walletSettingsXml.persistToMedia();

        } catch (CantPersistFileException cantPersistFileException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);

            throw new CantSetDefaultLanguageException("Error persist settings file",cantPersistFileException, "","");

        }


    }

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException {

        SAXBuilder builder = new SAXBuilder();

        /**
         * load file content
         */
        try {
            loadSettingsFile();

        } catch (CantLoadSettingsFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantSetDefaultSkinException("Error load settings file",e, "File name: "+ WALLET_SETTINGS_FILE_NAME,"");

        }

        /**
         * Get language settings on xml
         */
        try {

            Document document = (Document) builder.build(new StringReader(this.walletSettingsXml.getContent()));

            Element rootNode = document.getRootElement();

                /**
                 * Check wallet id is equals to this wallet process
                 */
                if(rootNode.getChildText("wallet_id").equals(this.walletIdInTheDevice.toString()))
                {
                    rootNode.getChild("skin_id").setText(skinId.toString());
                }else
                {
                    //error invalid wallet id
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, null);

                    throw new CantSetDefaultSkinException("Error write settings data, the wallet Ids mismatched",null, "","Xml carrupted");

                }


            XMLOutputter xmOut=new XMLOutputter();

            walletSettingsXml.setContent(xmOut.outputString(document));

        } catch(JDOMException|IOException e)
        {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantSetDefaultSkinException("Error parse settings file to xml object",e, "","Xml bad format");

        }

        /**
         * persist xml file
         */

        try {
            walletSettingsXml.persistToMedia();

        } catch (CantPersistFileException cantPersistFileException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);

            throw new CantSetDefaultSkinException("Error persist settings file",cantPersistFileException, "","");

        }

    }


    private void loadSettingsFile() throws CantLoadSettingsFileException {
        /**
         * Check if this is the first time this plugin starts. To do so I check if the file containing  the wallets settings
         * already exists or not.
         * If not exists I created it.
         * * *
         */

        StringBuffer strXml = new StringBuffer();
        try {

            try{
                walletSettingsXml = pluginFileSystem.getTextFile(pluginId, this.walletIdInTheDevice.toString(), WALLET_SETTINGS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            }
            catch (CantCreateFileException cantCreateFileException ) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);

                throw new CantLoadSettingsFileException("I could not get file",cantCreateFileException, "File name: "+ WALLET_SETTINGS_FILE_NAME,"");
            }
            try {
                /**
                 * Now I read the content of the file and place it in memory.
                 */
                walletSettingsXml.loadFromMedia();


                //if context empty I create xml structure

                if(walletSettingsXml.getContent() == ""){
                    /**
                     * make default xml structure
                     */
                    strXml.append("<wallets_settings>");
                    strXml.append("<wallet_id>"+ this.walletIdInTheDevice.toString() +"</wallet_id>");
                    strXml.append("<language_id></language_id>");
                    strXml.append("<skin_id></skin_id>");
                    strXml.append("</wallets_settings>");

                    walletSettingsXml.setContent(strXml.toString());

                    try
                    {
                         walletSettingsXml.persistToMedia();
                    }
                    catch (CantPersistFileException cantPersistFileException ) {

                        /**
                         * If I can not save this file, then this plugin shouldn't be running at all.
                         */
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
                        throw new CantLoadSettingsFileException("I couldn't save the file",cantPersistFileException, "FIleName: "+ WALLET_SETTINGS_FILE_NAME,"");
                    }
                }


            }
            catch (CantLoadFileException cantLoadFileException) {

                /**
                 * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
                 * to prevent the plug-in from running.
                 *
                 * In the future there should be implemented a method to deal with this situation.
                 * * * *
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SETTINGS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadFileException);

                throw new CantLoadSettingsFileException("Can't load file content from media",cantLoadFileException,"","");
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            /**
             * If the file did not exist it is not a problem. It only means this is the first time this plugin is running.
             *
             * I will create the file now, with an empty content so that when a new wallet is added we wont have to deal
             * with this file not existing again.
             * * * * *
             */

            try{
                walletSettingsXml = pluginFileSystem.createTextFile(pluginId, this.walletIdInTheDevice.toString(), WALLET_SETTINGS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            }
            catch (CantCreateFileException cantCreateFileException ) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
                throw new CantLoadSettingsFileException("I can't create file",cantCreateFileException,"File name: " + WALLET_SETTINGS_FILE_NAME,"");
            }
            try {

                /**
                 * make default xml structure
                 */
                strXml.append("<wallets_settings>");
                strXml.append("<wallet_id>"+ this.walletIdInTheDevice.toString() +"</wallet_id>");
                strXml.append("<language_id></language_id>");
                strXml.append("<skin_id></skin_id>");
                strXml.append("</wallets_settings>");

                walletSettingsXml.setContent(strXml.toString());

                walletSettingsXml.persistToMedia();
            }
            catch (CantPersistFileException cantPersistFileException ) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
                throw new CantLoadSettingsFileException("I couldn't save the file",cantPersistFileException, "FIleName: "+ WALLET_SETTINGS_FILE_NAME,"");
            }
        }
    }
}
