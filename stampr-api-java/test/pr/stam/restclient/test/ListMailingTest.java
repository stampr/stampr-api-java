package pr.stam.restclient.test;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

import pr.stam.restclient.Stampr;
import pr.stam.restclient.Stampr.Status;
import pr.stam.restclient.request.MailingRequest;
import pr.stam.restclient.response.Batch;
import pr.stam.restclient.response.Config;
import pr.stam.restclient.response.Mailing;

public class ListMailingTest {

	@Test
	public void test() {
		Stampr stampr = Stampr.getStampr("dummy.user@example.com","hello");
		
		try{
			System.out.println(stampr.ping());
			
			Status status = Status.HOLD;
			Integer paging = 1;
			String start = "";
			String end = "";
			
			String health = stampr.health();
			System.out.println(health);
			Assert.assertEquals("\"ok\"", health);
			
			Config config = stampr.createConfig();
			Batch batch = stampr.createBatch(config, "<html>Hello {{name}}!</html>", status);
			
			//send 10 mailings
			for(int i=1;i<10;i++)
			{
				MailingRequest mailingRequest = new MailingRequest(batch, "return adress", "from address", "{ name: \"Marie\" }");
				batch.addMailingRequest(mailingRequest);
			}
			batch.send();
			
			Mailing[] mailings0 = stampr.listBatchMailings(batch.getBatch_id());
			Mailing[] mailings1 = stampr.listBatchMailings(batch.getBatch_id(),status);
			Mailing[] mailings2 = stampr.listBatchMailings(batch.getBatch_id(),status,start);
			Mailing[] mailings3 = stampr.listBatchMailings(batch.getBatch_id(),status,start,end);
			Mailing[] mailings4 = stampr.listBatchMailings(batch.getBatch_id(),status,start,end,paging);
			Mailing[] mailings5 = stampr.listBatchMailings(batch.getBatch_id(),start);
			Mailing[] mailings6 = stampr.listBatchMailings(batch.getBatch_id(),start,end);
			Mailing[] mailings7 = stampr.listBatchMailings(batch.getBatch_id(),start,end,paging);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
