package pr.stam.restclient.test.old;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import pr.stam.restclient.Stampr;
import pr.stam.restclient.Stampr.Format;
import pr.stam.restclient.Stampr.Status;
import pr.stam.restclient.response.Batch;
import pr.stam.restclient.response.Config;
import pr.stam.restclient.response.Mailing;


public class AdvancedSendTest {

	@Test
	public void test() {
		
		Stampr stampr = Stampr.getStampr("dummy.user@example.com","hello");
		
		try{
			System.out.println(stampr.ping());
			
			String health = stampr.health();
			System.out.println(health);
			Assert.assertEquals("\"ok\"", health);
			
			Config config = stampr.createConfig();
			Assert.assertNotNull(config);
			System.out.println(config);
			
			Batch batch = stampr.createBatch(config, "Hello {{name}}!", Status.PROCESSING);
			Assert.assertNotNull(batch);
			System.out.println(batch);
			
			Mailing mailing = stampr.createMailing(batch, "1 MICROSOFT WAY", "1 MICROSOFT WAY", Format.PDF, "$name='test'", null);
			Assert.assertNotNull(batch);
			System.out.println(mailing);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	
	}

}
