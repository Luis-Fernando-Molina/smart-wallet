/*
 * @#CloudServiceManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure;

import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.IncorrectFMPPacketDestinationException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.exceptions.NetworkServiceAlreadyRegisteredException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager</code> represent
 * the cloud server
 * <p/>
 *
 * Created by Jorge Gonzales
 * Update by Roberto Requena - (rart3001@gmail.com) on 09/06/15.
 *
 * @version 1.0
 */
public class CloudServiceManager extends CloudFMPConnectionManager {

	/**
	 * Represent the networkServicesRegistry
	 */
	private final Map<NetworkServices, CloudNetworkServiceManager> networkServicesRegistry;

	/**
	 * Constructor whit parameters
	 *
	 * @param address
	 * @param executor
	 * @param keyPair
	 * @throws IllegalArgumentException
	 */
	public CloudServiceManager(final CommunicationChannelAddress address, final ExecutorService executor, final com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair keyPair) throws IllegalArgumentException{
		super(address, executor, keyPair.getPrivateKey(), keyPair.getPublicKey(), CloudFMPConnectionManagerMode.FMP_SERVER);
		networkServicesRegistry = new ConcurrentHashMap<NetworkServices, CloudNetworkServiceManager>();
		networkServicesRegistry.clear();
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionAccept(FMPPacket)
     */
	@Override
	public void handleConnectionAccept(final FMPPacket packet) throws FMPException{
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionAcceptForward(FMPPacket)
     */
	@Override
	public void handleConnectionAcceptForward(final FMPPacket packet) throws FMPException{
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionDeny(FMPPacket)
     */
	@Override
	public void handleConnectionDeny(final FMPPacket packet) throws FMPException{
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionRequest(FMPPacket)
     */
	@Override
	public void handleConnectionRequest(final FMPPacket packet) throws FMPException{

		System.out.println("CloudServiceManager - Starting method handleConnectionRequest");

		/*
		 * Validate that the destination are I
		 */
		if(!packet.getDestination().equals(getIdentityPublicKey())) {
            throw constructIncorrectFMPPacketDestinationException(packet, identity.getPublicKey());
        }

        /*
         * Validate is the sender have a register connection and the packet
         * have a define network service type
         */
		if(registeredConnections.containsKey(packet.getSender()) &&
                packet.getNetworkServices() != NetworkServices.UNDEFINED) {

            /*
             * Is a network services connection request, process this
             */
            processNetworkServiceConnectionRequest(registeredConnections.get(packet.getSender()), packet);

        }else {

            /*
             * Is a CloudClientCommunicationManager connection request, process this
             */
            processConnectionRequest(unregisteredConnections.get(packet.getSender()), packet);
        }
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionRegister(FMPPacket)
     */
	@Override
	public void handleConnectionRegister(final FMPPacket packet) throws FMPException{

        System.out.println("CloudServiceManager - Starting method handleConnectionRegister");

        /*
         * Validate is the sender are in a unregister connection
         */
        if(unregisteredConnections.containsKey(packet.getSender())) {

            /*
             * Process to register a new connection
             */
            processConnectionRegister(packet);
        }
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionDeregister(FMPPacket)
     */
	@Override
	public void handleConnectionDeregister(final FMPPacket packet) throws FMPException{
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleConnectionEnd(FMPPacket)
     */
	@Override
	public void handleConnectionEnd(final FMPPacket packet) throws FMPException{
	}

    /**
     * (non-Javadoc)
     *
     * @see CloudFMPConnectionManager#handleDataTransmit(FMPPacket)
     */
	@Override
	public void handleDataTransmit(final FMPPacket packet) throws FMPException{
	}

    /**
     * This method register register a new CloudNetworkServiceManager
     *
     * @param networkServiceManager
     * @throws CloudCommunicationException
     */
	public void registerNetworkServiceManager(final CloudNetworkServiceManager networkServiceManager) throws CloudCommunicationException {

		if(networkServiceManager == null) {
            throw new IllegalArgumentException();
        }

		if(networkServicesRegistry.containsKey(networkServiceManager.getNetworkService())) {
            throw constructNetworkServiceAlreadyRegistered(networkServiceManager.getNetworkService());
        }

		if(!networkServiceManager.isRunning()) {
            networkServiceManager.start();
        }

		networkServicesRegistry.put(networkServiceManager.getNetworkService(), networkServiceManager);
	}

    /**
     * This method process a connection request, create a new package with the public key identity
     * and the type CONNECTION_ACCEPT
     *
     * @param connection
     * @param fMPPacketReceive
     * @throws FMPException
     */
	private void processConnectionRequest(final SelectionKey connection, final FMPPacket fMPPacketReceive) throws FMPException{

        System.out.println("CloudServiceManager - Starting method processConnectionRequest");
        System.out.println("dataPacket received = "+fMPPacketReceive);
        System.out.println(" ----------------------------------------------------------  ");


        /**
         * Put the connection into de unregister connections
         */
        unregisteredConnections.put(fMPPacketReceive.getSender(), connection);

        /*
         * Construct the response packet
         */
		FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(), //sender
                                                                                              fMPPacketReceive.getSender(),  //destination
                                                                                              identity.getPublicKey(), // message
                                                                                              FMPPacketType.CONNECTION_ACCEPT,
                                                                                              NetworkServices.UNDEFINED,
                                                                                              identity.getPrivateKey());

        System.out.println("responsePacket = "+responsePacket);
        System.out.println(" ----------------------------------------------------------  ");

        /*
         * Put the packet into PendingOutgoingPacketCache
         */
		putIntoPendingOutgoingPacketCache(responsePacket.getDestination(), responsePacket);

        /*
         * Attach the destination of the packet
         */
		connection.attach(responsePacket.getDestination());

        /*
         * Mark the connection to write
         */
		connection.interestOps(SelectionKey.OP_WRITE);

	}

    /**
     * This method process a network services connection request, and respond whit
     * the host and port to connect
     *
     * @param connection
     * @param fMPPacketReceive
     * @throws FMPException
     */
	private void processNetworkServiceConnectionRequest(final SelectionKey connection, final FMPPacket fMPPacketReceive) throws FMPException{

        System.out.println("CloudServiceManager - Starting method processNetworkServiceConnectionRequest");
        System.out.println("fMPPacketReceive = "+fMPPacketReceive);
        System.out.println(" ----------------------------------------------------------  ");

        /*
         * Initialize variables
         */
        FMPPacket responsePacket = null;

        /*
         * Obtain the network service type
         */
		NetworkServices networkService = fMPPacketReceive.getNetworkServices();

        /*
         * Validate if it is a network service supported
         */
		if(networkServicesRegistry.containsKey(networkService)){

            /*
             * Get identity of the remote network service in the value of the message
             */
            Gson gson = new Gson();
            JsonObject messageReceived = gson.fromJson(fMPPacketReceive.getMessage(), JsonObject.class);

            /*
             * Construct the message structure info
             */
			JsonObject messageRespond = new JsonObject();
            messageRespond.add("host", new JsonPrimitive(networkServicesRegistry.get(networkService).getCommunicationChannelAddress().getHost()));
            messageRespond.add("port", new JsonPrimitive(networkServicesRegistry.get(networkService).getCommunicationChannelAddress().getPort()));
            messageRespond.add("identityCloudNetworkServiceManager", new JsonPrimitive(networkServicesRegistry.get(networkService).getIdentityPublicKey()));
            messageRespond.add("identityNetworkServiceRegistered", new JsonPrimitive(messageReceived.get("identityNetworkServicePublicKey").toString()));

            /*
             * Construct a connection accept forward packet respond
             */
            responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),      //sender
                                                                                        fMPPacketReceive.getSender(), //destination
                                                                                        messageRespond.getAsString(),  // message
                                                                                        FMPPacketType.CONNECTION_ACCEPT_FORWARD,
                                                                                        networkService,
                                                                                        identity.getPrivateKey());
			
		} else {

            /*
             * Construct a connection deny packet respond
             */
            responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),      //sender
                                                                                        fMPPacketReceive.getSender(), //destination
                                                                                        "NETWORK SERVICE " + networkService + " IS NOT SUPPORTED BY THE CLOUD SERVER",  // message
                                                                                        FMPPacketType.CONNECTION_DENY,
                                                                                        networkService,
                                                                                        identity.getPrivateKey());
		}



        System.out.println("responsePacket = "+responsePacket);
        System.out.println(" ----------------------------------------------------------  ");

        /*
         * Put the packet into PendingOutgoingPacketCache
         */
        putIntoPendingOutgoingPacketCache(responsePacket.getDestination(), responsePacket);

        /*
         * Attach the destination of the packet
         */
        connection.attach(responsePacket.getDestination());

        /*
         * Mark the connection to write
         */
        connection.interestOps(SelectionKey.OP_WRITE);
	}

    /**
     * This method process a connection register.
     *
     * @param fMPPacketReceive
     * @throws FMPException
     */
	private void processConnectionRegister(final FMPPacket fMPPacketReceive) throws FMPException{

        System.out.println("CloudServiceManager - Starting method processConnectionRegister");
        System.out.println("fMPPacketReceive = "+fMPPacketReceive);
        System.out.println(" ----------------------------------------------------------  ");

        /*
         * Get and remove the connection from unregistered connections
         */
		SelectionKey connection = unregisteredConnections.remove(fMPPacketReceive.getSender());

        /*
         * Put the connection into the registered connections
         */
		registeredConnections.put(fMPPacketReceive.getSender(), connection);

        /*
         * Construct the response packet
         */
        FMPPacket responsePacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged(identity.getPublicKey(),      //sender
                                                                                              fMPPacketReceive.getSender(), //destination
                                                                                              "REGISTERED",                 // message
                                                                                              FMPPacketType.DATA_TRANSMIT,
                                                                                              NetworkServices.UNDEFINED,
                                                                                              identity.getPrivateKey());


        System.out.println("responsePacket = "+responsePacket);
        System.out.println(" ----------------------------------------------------------  ");

        /*
         * Put the packet into PendingOutgoingPacketCache
         */
        putIntoPendingOutgoingPacketCache(responsePacket.getDestination(), responsePacket);

        /*
         * Attach the destination of the packet
         */
        connection.attach(responsePacket.getDestination());

        /*
         * Mark the connection to write
         */
        connection.interestOps(SelectionKey.OP_WRITE);
	}

    /**
     * Created a instance of the IncorrectFMPPacketDestinationException whit the
     * parameters
     *
     * @param packet
     * @param supposedDestination
     * @return IncorrectFMPPacketDestinationException
     */
	private IncorrectFMPPacketDestinationException constructIncorrectFMPPacketDestinationException(final FMPPacket packet, final String supposedDestination) {
		String message = IncorrectFMPPacketDestinationException.DEFAULT_MESSAGE;
		FermatException cause = null;
		String context = "Supposed Destination: " + supposedDestination;
		context += "Packet Info: " + packet.toString();
		String possibleReason = "This is a very weird error, we should check the sender to see if it's registered, we should be aware of this";
		return new IncorrectFMPPacketDestinationException(message, cause, context, possibleReason);
	}

    /**
     * Created a instance of the NetworkServiceAlreadyRegisteredException whit the
     * parameters
     *
     * @param networkService
     * @return NetworkServiceAlreadyRegisteredException
     */
	private NetworkServiceAlreadyRegisteredException constructNetworkServiceAlreadyRegistered(final NetworkServices networkService){
		String message = NetworkServiceAlreadyRegisteredException.DEFAULT_MESSAGE;
		FermatException cause = null;
		String context = "Network Service: " + networkService.toString();
		String possibleReason = "We are registring a NetworkService that has already been registered, check the invocations to this method";
		return new NetworkServiceAlreadyRegisteredException(message, cause, context, possibleReason);
	}
}
