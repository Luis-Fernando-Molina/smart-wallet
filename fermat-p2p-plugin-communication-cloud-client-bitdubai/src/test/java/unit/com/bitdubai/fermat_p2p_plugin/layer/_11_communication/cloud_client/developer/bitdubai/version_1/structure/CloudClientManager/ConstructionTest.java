package unit.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientManager;


import org.junit.Test;

import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_client.developer.bitdubai.version_1.structure.CloudClientManager;


public class ConstructionTest extends CloudClientManagerUnitTest{
	
	private static int TCP_PORT_PADDING = 100;
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_NullServerPublicKey_ThrowsIllegalArgumentException() throws Exception{
		setUp(TCP_PORT_PADDING);
		testClient = new CloudClientManager(testAddress, testExecutor, clientPrivateKey, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_EmptyServerPublicKey_ThrowsIllegalArgumentException() throws Exception{
		setUp(TCP_PORT_PADDING);
		testClient = new CloudClientManager(testAddress, testExecutor, clientPrivateKey, "");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Construction_BadServerPublicKeyHex_ThrowsIllegalArgumentException() throws Exception{
		setUp(TCP_PORT_PADDING);
		testClient = new CloudClientManager(testAddress, testExecutor, clientPrivateKey, "This is a Test To See If We Can Break The Hex");
	}

}
