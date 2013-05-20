package pr.stam.restclient.test.old;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

import pr.stam.restclient.Stampr;
import pr.stam.restclient.response.Batch;
import pr.stam.restclient.response.Mailing;


public class SimpleSend2Test {

	@Test
	public void test() {
		
		Stampr stampr = Stampr.getStampr("dummy.user@example.com","hello");
		
		try{
			System.out.println(stampr.ping());
			String health = stampr.health();
			System.out.println(health);
			Assert.assertEquals("\"ok\"", health);
			
			Batch batch = new Batch(1000);
			
			Mailing mailing = stampr.send("1 MICROSOFT WAY", "1 MICROSOFT WAY", "test",batch);
			Assert.assertNotNull(mailing);
			System.out.println(mailing);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
