package pr.stam.restclient.test;

import static org.junit.Assert.fail;

import java.util.Date;

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

			Status status = Status.PROCESSING;
			Date start = new Date();
			Date end = new Date();
			int paging = 1;
			
			Batch[] batches0 = stampr.listBatches(Status.PROCESSING);
			Batch[] batches1 = stampr.listBatches(Status.PROCESSING,start);
			Batch[] batches2 = stampr.listBatches(Status.PROCESSING,start,end);
			Batch[] batches3 = stampr.listBatches(Status.PROCESSING,start,end,paging);
			Batch[] batches4 = stampr.listBatches(start);
			Batch[] batches5 = stampr.listBatches(start,end);
			Batch[] batches6 = stampr.listBatches(start,end,paging);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
