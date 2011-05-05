package org.openfuxml.test.renderer;

import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.renderer.OfxRenderer;

public class TstOfxRenderer
{
	static Log logger = LogFactory.getLog(TstOfxRenderer.class);
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();

		String propFile = "src/test/resources/properties/user.properties";
			
		if(args.length==1){propFile=args[0];}
			
		ConfigLoader.add(propFile);
		Configuration config = ConfigLoader.init();
		
		OfxRenderer ofx = new OfxRenderer();
		ofx.initCmpUtil(config.getString("ofx.xml.cmp"));
		
		ofx.preProcessor(config);
		ofx.renderTarget();
	}
}