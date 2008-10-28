package org.openfuxml.server;

import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import de.kisner.util.xml.XmlConfig;

public abstract class AbstractServer
{
	static Logger logger = Logger.getLogger(AbstractServer.class);
	
	public void setSystemProperties(XmlConfig xCnf)
	{
		Properties sysprops = System.getProperties();
		Map<String,String> sysenv = System.getenv();
		String baseDir = xCnf.getWorkingDir();
		
		logger.debug("baseDir="+baseDir);
		String pathLog = xCnf.getText("dirs/dir[@typ=\"log\"]");
		String pathRepo=xCnf.getText("dirs/dir[@typ=\"repository\"]");;
		if(!pathLog.substring(0,1).equals("/")){pathLog=baseDir+"/"+pathLog;}
		if(!pathRepo.substring(0,1).equals("/")){pathRepo=baseDir+"/"+pathRepo;}
		
		sysprops.put("ilona.home",baseDir);
		sysprops.put("ant.home",sysenv.get("ANT_HOME"));
		sysprops.put("logger.path",pathLog);
		sysprops.put("ilona.contentstore",pathRepo);
		System.setProperties(sysprops);
	}
	
	public void checkSystemProperties()
	{
		Properties sysprops = System.getProperties();
		
		logger.debug("System Properties:");
		logger.debug("\tilona.home="+sysprops.getProperty("ilona.home"));
		logger.debug("\tilona.contentstore="+sysprops.getProperty("ilona.contentstore"));
		logger.debug("\tilona.output="+sysprops.getProperty("ilona.output"));
		logger.debug("\tant.home="+sysprops.getProperty("ant.home"));
		logger.debug("\tlogger.path="+sysprops.getProperty("logger.path"));
		
		logger.debug("\tILONA_HOME="+System.getenv("ILONA_HOME"));
		logger.debug("\tFUXML_HOME="+System.getenv("FUXML_HOME"));
	}
}
