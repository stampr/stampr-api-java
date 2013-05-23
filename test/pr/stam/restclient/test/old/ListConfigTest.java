package pr.stam.restclient.test.old;

import static org.junit.Assert.fail;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import pr.stam.restclient.Stampr;
import pr.stam.restclient.response.Config;

public class ListConfigTest {

	@Test
	public void test() {
		Stampr stampr = Stampr.getStampr("dummy.user@example.com","hello");
		
		try{
			System.out.println(stampr.ping());
			
			String health = stampr.health();
			System.out.println(health);
			Assert.assertEquals("\"ok\"", health);
			
//			Config[] config1 = serviceClient.listConfigsAll();
//			Assert.assertNotNull(config1);
//			System.out.println(config1);
			
			Config config2 = stampr.getConfigById(1000);
			Assert.assertNotNull(config2);
			System.out.println(config2);
			
//			Config[] config3 = serviceClient.listConfigsPaged(1);
//			Assert.assertNotNull(config3);
//			System.out.println(config3);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		
	}

}
