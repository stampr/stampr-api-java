package pr.stam.restclient.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import pr.stam.restclient.Stampr;
import pr.stam.restclient.response.Config;

public class ListConfigTest {

	@Test
	public void test() {
		Stampr stampr = Stampr.getStampr("dummy.user@example.com","hello");
		
		try{
			System.out.println(stampr.ping());
			
			Config config = stampr.createConfig();
			System.out.println("Created config: " + config);
			
			Config configCopy = stampr.getConfigById(config.getConfig_id());
			System.out.println("Config copy: " + configCopy);
			
			try{
				Config[] allConfigs = stampr.listConfigsAll();
				System.out.println("All configs:" + allConfigs);
			}catch(Exception e)
			{	e.printStackTrace();}
			
			try{
				Config[] allConfigs10 = stampr.listConfigsPaged(10);
				System.out.println("Page 10: " + allConfigs10);
			}catch(Exception e)
			{   e.printStackTrace();}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}

