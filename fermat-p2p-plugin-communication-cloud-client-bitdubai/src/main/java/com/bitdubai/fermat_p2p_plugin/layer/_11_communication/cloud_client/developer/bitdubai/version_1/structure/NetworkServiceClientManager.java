package com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.structure;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_api.layer._10_communication.cloud.CloudConnectionException;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPException;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket;
import com.bitdubai.fermat_api.layer._10_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_api.layer._1_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_api.layer._1_definition.communication.cloud.CloudFMPConnectionManager;
import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalPacketSenderException;
import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.exceptions.IllegalSignatureException;

public class NetworkServiceClientManager extends CloudFMPConnectionManager {
	
	private static final String CHARSET_NAME = "UTF-8";
	
	private final Queue<FMPPacket> pendingMessages = new ConcurrentLinkedQueue<FMPPacket>();
	private Map<String, SelectionKey> requestedConnections = new ConcurrentHashMap<String, SelectionKey>();
	
	private final String serverPublicKey;
	
	private final NetworkServices networkService;

	public NetworkServiceClientManager(final CommunicationChannelAddress serverAddress, final ExecutorService executor, final String clientPrivateKey, final String serverPublicKey, final NetworkServices networkService) throws IllegalArgumentException {
		super(serverAddress, executor, clientPrivateKey, AsymmectricCryptography.derivePublicKey(clientPrivateKey), CloudFMPConnectionManagerMode.FMP_CLIENT);
		checkBigIntegerHexString(serverPublicKey);
		this.serverPublicKey = serverPublicKey;
		if(networkService == null)
			throw new IllegalArgumentException();
		this.networkService = networkService;
	}
	
	@Override
	public void writeToKey(final SelectionKey key) throws CloudConnectionException{
		try{
			SocketChannel channel = (SocketChannel) key.channel();
			FMPPacket dataPacket = pendingMessages.remove();
			byte[] data = dataPacket.toString().getBytes(CHARSET_NAME);
			channel.write(ByteBuffer.wrap(data));
			if(!pendingMessages.isEmpty())
				writeToKey(key);
			key.interestOps(SelectionKey.OP_READ);
		}catch(NoSuchElementException ex){
			key.interestOps(SelectionKey.OP_READ);
		}catch(IOException ex){
			throw new CloudConnectionException(ex.getMessage());
		}
	}

	@Override
	public void processDataPacket(final String data, final SelectionKey key)
			throws CloudConnectionException {
		try {
			FMPPacket dataPacket = FMPPacketFactory.constructCloudPacket(data);
			key.attach(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_REQUEST)
				handleConnectionRequest(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_ACCEPT)
				handleConnectionAccept(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_ACCEPT_FORWARD)
				handleConnectionAcceptForward(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_DENY)
				handleConnectionDeny(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_REGISTER)
				handleConnectionRegister(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_DEREGISTER)
				handleConnectionDeregister(dataPacket);
			if(dataPacket.getType() == FMPPacketType.CONNECTION_END)
				handleConnectionEnd(dataPacket);
			if(dataPacket.getType() == FMPPacketType.DATA_TRANSMIT)
				handleDataTransmit(dataPacket);
		} catch(FMPException ex){
			throw new CloudConnectionException(ex.getMessage());
		}

	}
	
	@Override
	public void handleConnectionAccept(final FMPPacket dataPacket) throws FMPException {
		if(!serverPublicKey.equals(dataPacket.getSender()))
			throw new IllegalPacketSenderException("should be " + serverPublicKey + " but is " + dataPacket.getSender());
		if(!validatePacketSignature(dataPacket))
			throw new IllegalSignatureException("bad signature for the packet: \n"+dataPacket.toString());
		if(requestedConnections.isEmpty())
			return;
		String serverAddress = dataPacket.getSender();
		SelectionKey serverConnection = requestedConnections.get(serverAddress);
		
		String sender = eccPublicKey;
		String destination = dataPacket.getSender();
		FMPPacketType type = FMPPacketType.CONNECTION_REGISTER;
		String message = eccPublicKey;
		String signature = AsymmectricCryptography.createMessageSignature(message, eccPrivateKey);
			
		FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, message, signature);
		pendingMessages.add(packet);
		serverConnection.interestOps(SelectionKey.OP_WRITE);
	}

	@Override
	public void handleConnectionAcceptForward(final FMPPacket dataPacket)
			throws FMPException {
		System.out.println(dataPacket.toString());
	}

	@Override
	public void handleConnectionDeny(final FMPPacket dataPacket) throws FMPException {
		running.set(false);
	}

	@Override
	public void handleConnectionDeregister(final FMPPacket dataPacket) throws FMPException {
		System.out.println(dataPacket.toString());
	}

	@Override
	public void handleConnectionEnd(final FMPPacket dataPacket) throws FMPException {
		System.out.println(dataPacket.toString());
	}

	@Override
	public void handleConnectionRegister(final FMPPacket dataPacket) throws FMPException {
		System.out.println(dataPacket.toString());
	}

	@Override
	public void handleConnectionRequest(final FMPPacket dataPacket) throws FMPException {
		System.out.println(dataPacket.toString());
	}

	@Override
	public void handleDataTransmit(final FMPPacket dataPacket) throws FMPException {
		String message = AsymmectricCryptography.decryptMessagePrivateKey(dataPacket.getMessage(), eccPrivateKey);
		if(requestedConnections.isEmpty() || registeredConnections.containsKey(dataPacket.getSender()))
			return;
		if(!message.equals("REGISTERED"))
			return;
		SelectionKey serverConnection = requestedConnections.get(dataPacket.getSender());
		requestedConnections.remove(dataPacket.getSender());
		registeredConnections.put(dataPacket.getSender(), serverConnection);
	}
	
	@Override
	public void start() throws CloudConnectionException{
		if(running.get())
			throw new CloudConnectionException();
		try{
			selector = Selector.open();
			clientChannel = SocketChannel.open();
			clientChannel.configureBlocking(false);
			SelectionKey serverConnection = clientChannel.register(selector, SelectionKey.OP_WRITE);
			clientChannel.connect(address.getSocketAddress());
			if(clientChannel.isConnectionPending())
				clientChannel.finishConnect();
			running.set(clientChannel.isConnected());
			unregisteredConnections.put(serverPublicKey, serverConnection);
			requestConnectionToServer();
			executor.execute(this);
		}catch(IOException ex){	
			throw new CloudConnectionException(ex.getMessage());
		}
	}
	
	private void requestConnectionToServer() throws CloudConnectionException{
		String sender = eccPublicKey;
		String destination = serverPublicKey;
		FMPPacketType type = FMPPacketType.CONNECTION_REQUEST;
		String message = AsymmectricCryptography.encryptMessagePublicKey(networkService.toString(), serverPublicKey);
		String signature = AsymmectricCryptography.createMessageSignature(message, eccPrivateKey);
		try{
			FMPPacket packet = FMPPacketFactory.constructCloudPacket(sender, destination, type, message, signature);
			pendingMessages.add(packet);
			SelectionKey serverConnection = unregisteredConnections.get(serverPublicKey);
			serverConnection.interestOps(SelectionKey.OP_WRITE);
			unregisteredConnections.remove(serverPublicKey);
			requestedConnections.put(serverPublicKey, serverConnection);
		} catch(FMPException ex){
			throw new CloudConnectionException(ex.getMessage());
		}
	}

	public boolean isRegistered() {
		return registeredConnections.containsKey(serverPublicKey);
	}
	
	public NetworkServices getNetworkService(){
		return networkService;
	}
	
	private boolean validatePacketSignature(final FMPPacket dataPacket){
		String message = dataPacket.getMessage();
		String signature = dataPacket.getSignature();
		String sender = dataPacket.getSender();
		
		return AsymmectricCryptography.verifyMessageSignature(signature, message, sender);
	}
	
	private void checkBigIntegerHexString(final String hex) throws IllegalArgumentException {
		if(hex == null || hex.isEmpty())
			throw new IllegalArgumentException();
		try{
			new BigInteger(hex,16);
		} catch(NumberFormatException ex){
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
}
