package pr.stam.restclient.test.old;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

import pr.stam.restclient.Stampr;
import pr.stam.restclient.Stampr.Status;
import pr.stam.restclient.response.Batch;
import pr.stam.restclient.response.Config;

public class ModifyBatchTest {

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
			
//			//MODIFY BATCH
//			Boolean modified = serviceClient.modifyBatch(batch.getBatch_id(), Status.ARCHIVE);
//			Assert.assertTrue(modified);
//			System.out.println(modified);
			
			//DELETE BATCH
			Boolean deleted = stampr.deleteBatch(batch.getBatch_id());
			Assert.assertTrue(deleted);
			System.out.println(deleted);
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
