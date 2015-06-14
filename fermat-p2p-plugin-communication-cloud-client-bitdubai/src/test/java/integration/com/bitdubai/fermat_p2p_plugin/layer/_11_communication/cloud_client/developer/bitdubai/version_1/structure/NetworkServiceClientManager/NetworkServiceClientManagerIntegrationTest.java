package integration.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientManager;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_api.layer._1_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientManager;
import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;
import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.ECCKeyPair;

public abstract class NetworkServiceClientManagerIntegrationTest {
	
	protected String clientPrivateKey = "362b22fdc53e753f17edf3ec98a39b855e7c7ac0614fb5a33a3bdebeff18932e";
	
	protected String serverPrivateKey = "a06a1274b8bd327c0ddeebc75597101a1e09c3551915e25ad2d8949e0507c142";
	protected String serverPublicKey = "047F9F57BDE5771B4A9DA604B1CA138AA3B593B7EFBC3C890E384CF8B3EB3080E70DDA5130A26768DDEF30379AA6F9B924339407B0D429964503372CB71D3328D0";

	protected String testHost = "localhost";
	protected Integer testBasePort = 51000;
	protected CommunicationChannelAddress testAddress;
	protected ExecutorService testExecutor = Executors.newFixedThreadPool(30);
	protected ECCKeyPair testServerKeyPair;
	protected CloudNetworkServiceManager testServer;
	protected NetworkServiceClientManager testClient;
	
	protected NetworkServices testNetworkService;
	protected Set<Integer> testVPNPorts; 
	
	protected void setUp(int tcpPadding) throws Exception{
		setUpParameters(tcpPadding);
		setUpServer(tcpPadding);
		testClient = new NetworkServiceClientManager(testAddress, testExecutor, clientPrivateKey, serverPublicKey, testNetworkService);
		testClient.start();
	}
	
	protected void setUpParameters(int tcpPadding) throws Exception{
		testAddress = CommunicationChannelAddressFactory.constructCloudAddress(testHost, testBasePort+tcpPadding);
		testServerKeyPair = new ECCKeyPair(serverPrivateKey);
		testNetworkService = NetworkServices.INTRA_USER;
		testVPNPorts = new HashSet<Integer>(); 
		testVPNPorts.add(testBasePort+tcpPadding+(tcpPadding*10)+1);
	}
	
	protected void setUpServer(int tcpPadding) throws Exception{
		testServer = new CloudNetworkServiceManager(testAddress, testExecutor, testServerKeyPair, testNetworkService, testVPNPorts);
		testServer.start();
	}
	
	protected int getThreadSleepMillis(){
		return 1000;
	}

}
