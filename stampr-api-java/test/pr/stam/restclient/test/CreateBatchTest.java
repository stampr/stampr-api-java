package pr.stam.restclient.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import pr.stam.restclient.Stampr;
import pr.stam.restclient.Stampr.Status;
import pr.stam.restclient.response.Batch;
import pr.stam.restclient.response.Config;

public class CreateBatchTest {

	@Test
	public void test() {
	Stampr stampr = Stampr.getStampr("dummy.user@example.com","hello");
		
		try{
			System.out.println(stampr.ping());
//			
//			Batch createBatch(Config config,String template,Status status)
//			Boolean modifyBatch(Integer batch_id, Status status)
//			Boolean deleteBatch(Integer batch_id)
//			Batch getBatchById(Integer batch_id)
			
			Config config = stampr.createConfig();
			Batch batch = stampr.createBatch(config, "<html>Hello {{name}}!</html>", Status.HOLD);
			
			System.out.println("Created batch:" + batch);
			
			Batch batchCopy = stampr.getBatchById(batch.getBatch_id());
			
			System.out.println("Batch copy:" + batchCopy);
			
			try{
				Boolean modified = stampr.modifyBatch(batch.getBatch_id(), Status.PROCESSING);
				System.out.println("Modified:" +modified);
			}catch(Exception e)
			{   e.printStackTrace();}
			

			try{
				Boolean deleted = stampr.deleteBatch(batch.getBatch_id());
				System.out.println("Deleted:" + deleted);
			}catch(Exception e)
			{   e.printStackTrace();}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
