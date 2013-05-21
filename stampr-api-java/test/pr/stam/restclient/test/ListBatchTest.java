package pr.stam.restclient.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import pr.stam.restclient.Stampr;
import pr.stam.restclient.Stampr.Status;
import pr.stam.restclient.response.Batch;

public class ListBatchTest {

	@Test
	public void test() {
	Stampr stampr = Stampr.getStampr("dummy.user@example.com","hello");
		
		try{
			System.out.println(stampr.ping());
			
//			Batch[] listBatchesPaged(Status status)
//			Batch[] listBatchesPaged(Status status, Integer start)
//			Batch[] listBatchesPaged(Status status, Integer start, Integer end)
//			Batch[] listBatchesPaged(Status status, Integer start, Integer end, Integer paging)

			Batch[] batches = stampr.listBatches(Status.PROCESSING);
			
			System.out.println("List Batches: " + batches);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
