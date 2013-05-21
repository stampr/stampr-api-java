package pr.stam.restclient.test.old;

import static org.junit.Assert.fail;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import pr.stam.restclient.Stampr;
import pr.stam.restclient.Stampr.Status;
import pr.stam.restclient.response.Batch;
import pr.stam.restclient.response.Config;

public class ListBatchTest {

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
			
			//LISTS
			Batch batch1 = stampr.getBatchById(batch.getBatch_id());
			Assert.assertNotNull(batch1);
			System.out.println(batch1);
			
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
