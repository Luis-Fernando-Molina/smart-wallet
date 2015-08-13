/*
 * @#WalletPublisherMiddlewareManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.LanguageDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.SkinDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishComponetException;

import java.util.List;
import java.util.Map;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager</code> mark
 * the different between the Wallet Publisher middleware and the Wallet Publisher module when need injected into other plugin
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletPublisherMiddlewareManager {

    /**
     * This method returns the information stored about the all published component
     *
     * @return List<InformationPublishedComponent>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedComponents() throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the all published wallets
     *
     * @return List<InformationPublishedComponent>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedWallets() throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the all published skins
     *
     * @return List<InformationPublishedComponent>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedSkins() throws CantGetPublishedComponentInformationException;

    /**
     * This method returns the information stored about the published language
     *
     * @return List<InformationPublishedComponent>
     * @throws CantGetPublishedComponentInformationException
     */
    public List<InformationPublishedComponent> getPublishedLanguages() throws CantGetPublishedComponentInformationException;

    /**
     * This method publishes the skin factory project <code>SkinDescriptorFactoryProject</code> with the skin information in
     * the wallet store and register relevant information of this process.
     *
     * @param skinDescriptorFactoryProject the skin factory project to publish
     * @throws CantPublishComponetException
     */
    public void publishSkin(SkinDescriptorFactoryProject skinDescriptorFactoryProject) throws CantPublishComponetException;

    /**
     * This method publishes the language factory project <code>LanguageDescriptorFactoryProject</code> with the language information in
     * the wallet store and register relevant information of this process (publication timestamp).
     *
     * @param languageDescriptorFactoryProject the wallet factory project to publish
     * @throws CantPublishComponetException
     */
    public void publishLanguage(LanguageDescriptorFactoryProject languageDescriptorFactoryProject) throws CantPublishComponetException;

}
