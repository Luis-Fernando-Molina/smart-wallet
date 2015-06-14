package integration.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientManager;

import static org.fest.assertions.api.Assertions.*;
import static com.googlecode.catchexception.CatchException.*;


import org.junit.Test;

import com.bitdubai.fermat_api.layer._10_communication.cloud.CloudConnectionException;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientManager;

public class StartTest extends NetworkServiceClientManagerIntegrationTest {
	
	private static final int TCP_PORT_PADDING = 100;
	
	//TODO improve this test, it's flaky
	@Test
	public void start_ValidClient_RequestAndRegisterConnectionToServer() throws Exception{
		setUp(TCP_PORT_PADDING + 1);
		Thread.sleep(getThreadSleepMillis());
		assertThat(testClient.isRunning()).isTrue();
		assertThat(testClient.isRegistered()).isTrue();
	}
	
	@Test
	public void start_ConnectionIsAlreadyStarted_ThrowsCloudConnectionException() throws Exception{
		setUp(TCP_PORT_PADDING + 2);
		catchException(testClient).start();
		assertThat(caughtException()).isInstanceOf(CloudConnectionException.class);
	}
	
	//TODO improve this test, it's flaky
	@Test
	public void requestConnection_BadNetworkServiceServer_ThrowsCloudConnectionException() throws Exception{
		setUpParameters(TCP_PORT_PADDING + 3);
		setUpServer(TCP_PORT_PADDING + 3);
		testClient= new NetworkServiceClientManager(testAddress, testExecutor, clientPrivateKey, serverPublicKey, NetworkServices.MONEY);
		testClient.start();
		Thread.sleep(getThreadSleepMillis());
		assertThat(testClient.isRunning()).isFalse();
		assertThat(testClient.isRegistered()).isFalse();
	}

}
