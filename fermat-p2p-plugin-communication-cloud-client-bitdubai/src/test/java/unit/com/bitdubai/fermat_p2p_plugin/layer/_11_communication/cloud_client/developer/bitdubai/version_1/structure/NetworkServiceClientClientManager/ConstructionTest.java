package unit.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientClientManager;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.structure.NetworkServiceClientManager;

public class ConstructionTest extends NetworkServiceClientManagerUnitTest {
	
	private static final int TCP_PORT_PADDING = 100;
	
	@Test
	public void Construction_ValidParameters_AssociatedWithNetworkService() throws Exception{
		setUp(TCP_PORT_PADDING);
		assertThat(testClient).isNotNull();
		assertThat(testClient.getNetworkService()).isEqualTo(testNetworkService);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_NullServerPublicKey_ThrowsIllegalArgumentException() throws Exception{
		setUp(TCP_PORT_PADDING);
		testClient = new NetworkServiceClientManager(testAddress, testExecutor, clientPrivateKey, null, testNetworkService);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_EmptyServerPublicKey_ThrowsIllegalArgumentException() throws Exception{
		setUp(TCP_PORT_PADDING);
		testClient = new NetworkServiceClientManager(testAddress, testExecutor, clientPrivateKey, "", testNetworkService);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_BadServerPublicKeyHex_ThrowsIllegalArgumentException() throws Exception{
		setUp(TCP_PORT_PADDING);
		testClient = new NetworkServiceClientManager(testAddress, testExecutor, clientPrivateKey, "This is a Test To See If We Can Break The Hex", testNetworkService);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_NullNetworkService_ThrowsIllegalArgumentException() throws Exception{
		setUp(TCP_PORT_PADDING);
		testClient = new NetworkServiceClientManager(testAddress, testExecutor, clientPrivateKey, serverPublicKey, null);
	}

}
