package integration.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientManager;

import static org.fest.assertions.api.Assertions.*;
import static com.googlecode.catchexception.CatchException.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.rules.TestName;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_api.layer._10_communication.cloud.CloudConnectionException;
import com.bitdubai.fermat_api.layer._1_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;
import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.ECCKeyPair;

public class RegisterNetworkServiceTest extends CloudClientManagerIntegrationTest {
	
	private static final int TCP_PORT_PADDING = 200;
	
	private CloudNetworkServiceManager testNetworkServiceServer;
	private NetworkServices testNetworkService;
	private Set<Integer> testVPNPorts; 
	private CommunicationChannelAddress testNetworkServiceServerAddress;
	
	private void setUpWithNetworkServiceManager(int tcpPadding) throws Exception{
		setUp(tcpPadding);
		testNetworkService = NetworkServices.INTRA_USER;
		testNetworkServiceServerAddress = CommunicationChannelAddressFactory.constructCloudAddress(testAddress.getHost(), testAddress.getPort()+tcpPadding+(tcpPadding*10));
		testVPNPorts = new HashSet<Integer>(); 
		testVPNPorts.add(testBasePort+tcpPadding+(tcpPadding*10)+1);
		testNetworkServiceServer = new CloudNetworkServiceManager(testNetworkServiceServerAddress, testExecutor, new ECCKeyPair(), testNetworkService, testVPNPorts);
		testServer.registerNetworkServiceManager(testNetworkServiceServer);
		testClient.requestConnectionToServer();
		Thread.sleep(getThreadSleepMillis());
	}
	
	//TODO improve this test, it's flaky
	@Test
	public void RegisterNetworkService_ServerSupportsNetworkService_NetworkServiceClientManagerIsRegistered() throws Exception{
		int tcpPadding = TCP_PORT_PADDING + 1;
		setUpWithNetworkServiceManager(tcpPadding);
		testClient.registerNetworkService(testNetworkService);
	}
}
