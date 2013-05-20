package pr.stam.restclient.test;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

import pr.stam.restclient.Stampr;
import pr.stam.restclient.request.MailingRequest;
import pr.stam.restclient.response.Batch;
import pr.stam.restclient.response.Config;
import pr.stam.restclient.response.Mailing;


public class SimpleSend1Test {

	@Test
	public void test() {
	Stampr stampr = Stampr.getStampr("dummy.user@example.com","hello");
		
		try{
			System.out.println(stampr.ping());
			
			String health = stampr.health();
			System.out.println(health);
			Assert.assertEquals("\"ok\"", health);
			
			Config config = stampr.createConfig();
			Batch batch = stampr.createBatch(config, "<html>Hello {{name}}!</html>", null);
			
			//send 10 mailings
			for(int i=1;i<10;i++)
			{
				MailingRequest mailingRequest = new MailingRequest(batch, "return adress", "from address", "{ name: \"Marie\" }");
//				batch.addMailingRequest(mailingRequest);
				Mailing mailing = mailingRequest.send();
				System.out.println(mailing);
				Assert.assertNotNull(mailing);
			}
			
//			batch.send();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
