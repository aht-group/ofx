

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import de.kisner.util.ConfigLoader;
import de.kisner.util.LoggerInit;

public class TestConfiguration
{
	static Logger logger = Logger.getLogger(TestConfiguration.class);
	
	Configuration config;
	
	public TestConfiguration(Configuration config)
	{
	    this.config=config;    
	}

	public void read()
	{
		String sC = config.getString("dirs/dir[@type='output']");
		logger.debug("config: "+sC);
	}
	
	public void save()
	{
		ConfigLoader.update("dirs/dir[@type='output']", "xxxx");
	}
	
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("openFuXML.xml");
	
		Configuration config = ConfigLoader.init();	

		TestConfiguration tc = new TestConfiguration(config);
		
		tc.read();
		tc.save();
		tc.read();
		//txml.setContent();	
	}
}
