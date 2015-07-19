package unit.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectResource;

import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectSkin;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.File;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class GetDatabaseListTest extends TestCase {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetDatabaseListTest_NotNull3() throws Exception {
        try {

            File file = new File("/home/lnacosta/Desktop/skins.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectSkin.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            WalletFactoryMiddlewareProjectSkin que = (WalletFactoryMiddlewareProjectSkin) jaxbUnmarshaller.unmarshal(file);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            jaxbMarshaller.marshal(que, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}