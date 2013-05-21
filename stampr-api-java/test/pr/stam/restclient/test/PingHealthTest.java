package pr.stam.restclient.test;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

import pr.stam.restclient.Stampr;


public class PingHealthTest {

	@Test
	public void test() {
	Stampr stampr = Stampr.getStampr("dummy.user@example.com","hello");
		
		try{
			System.out.println(stampr.ping());
			
			String health = stampr.health();
			System.out.println(health);
			Assert.assertEquals("\"ok\"", health);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
