package org.openfuxml.server;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

import de.kisner.util.architecture.ArchUtil;
import de.kisner.util.xml.XmlConfig;
import de.kisner.util.xml.XmlElementNotFoundException;

public abstract class AbstractServer
{
	static Logger logger = Logger.getLogger(AbstractServer.class);
	
	public static String[] environmentParameters;
	public static String baseDir;
	
	public void setSystemProperties(XmlConfig xCnf)
	{
		Properties sysprops = System.getProperties();
		Map<String,String> sysenv = System.getenv();
		
		try
		{
			baseDir = xCnf.getTextException("dirs/dir[@typ=\"basedir\"]");
		}
		catch (XmlElementNotFoundException e)
		{
			baseDir = xCnf.getWorkingDir();
			logger.warn("No \"baseDir\" defined in xmlConfig. Using WorkingDir: "+baseDir);
		}
		
		String pathLog = xCnf.getText("dirs/dir[@typ=\"log\"]");
		String pathRepo=xCnf.getText("dirs/dir[@typ=\"repository\"]");
		String pathOutput=xCnf.getText("dirs/dir[@typ=\"output\"]");
		
		if(!pathLog.substring(0,1).equals("/")){pathLog=baseDir+"/"+pathLog;}
		if(!pathRepo.substring(0,1).equals("/")){pathRepo=baseDir+"/"+pathRepo;}
		if(!pathOutput.substring(0,1).equals("/")){pathOutput=baseDir+"/"+pathRepo;}
		
		logger.debug("baseDir="+baseDir);
		logger.debug("pathLog="+pathLog);
		logger.debug("pathRepo="+pathRepo);
		logger.debug("pathOutput="+pathOutput);
		
/*		String antHome = sysenv.get("ANT_HOME");
		if(antHome!=null){sysprops.put("ant.home",sysenv.get("ANT_HOME"));}
		else
		{
			antHome = xCnf.getText("dirs/dir[@typ=\"ant\"]");
			logger.debug("ANT_HOME not set, using config.xml value");
			sysprops.put("ANT_HOME",antHome);
		}
*/			
		sysprops.put("ilona.home",baseDir);
//		sysprops.put("ant.home",antHome);
		sysprops.put("logger.path",pathLog);
		sysprops.put("ilona.contentstore",pathRepo);
		sysprops.put("ilona.output",pathOutput);
		System.setProperties(sysprops);
		
		setenvP(xCnf);
	}
	
	private void setenvP(XmlConfig xCnf)
	{	
		Hashtable<String,String> htEnv = new Hashtable<String,String>();
		htEnv.putAll(System.getenv());
		
		StringBuffer sb = new StringBuffer();
		boolean nextLib = false;
		for(String lib : xCnf.getTexts("files/file[@typ=\"lib\"]"))
		{
			if(nextLib){sb.append(":");}
			sb.append(baseDir+SystemUtils.FILE_SEPARATOR+"lib"+SystemUtils.FILE_SEPARATOR+lib);
			nextLib=true;
		}
		htEnv.put("CLASSPATH",sb.toString());
		logger.debug("Setting CLASSPATH for openFuXML Applications: "+sb.toString());
		
		ArchUtil.setArch();
		switch(ArchUtil.arch)
		{
			case OsX:	try
						{
							String systemPath = xCnf.getTextException("dirs/dir[@typ=\"path\"]");
							htEnv.put("PATH", systemPath);
						}
						catch (XmlElementNotFoundException e)
						{
							logger.warn("No PATH defined in xmlConfig. Using System PATH "+htEnv.get("PATH"));
							logger.warn("   Errors are possible for "+SystemUtils.OS_NAME);
							logger.warn("   Please insert your $PATH <dirs><dir typ=\"path\">HERE</dir></dirs>");
						}
						break;
			case Win32:	try
						{
							String systemPath = xCnf.getTextException("dirs/dir[@typ=\"path\"]");
							htEnv.put("PATH", systemPath);
						}
						catch (XmlElementNotFoundException e)
						{
							logger.warn("No PATH defined in xmlConfig. Using System PATH "+htEnv.get("PATH"));
							logger.warn("   Errors are possible for "+SystemUtils.OS_NAME);
							logger.warn("   Please insert your $PATH <dirs><dir typ=\"path\">HERE</dir></dirs>");
						}
						break;
			default:	logger.fatal("Environment for "+ArchUtil.arch+" must be implemented!");break;
		}	
		
		environmentParameters = new String[htEnv.size()];
		int index=0;
		for(String key : htEnv.keySet())
		{
			environmentParameters[index]=key+"="+htEnv.get(key);
			index++;
		}
	}
	
	public void checkSystemProperties()
	{
		Properties sysprops = System.getProperties();
		
		logger.debug("System Properties:");
		logger.debug("\tilona.home="+sysprops.getProperty("ilona.home"));
		logger.debug("\tilona.contentstore="+sysprops.getProperty("ilona.contentstore"));
		logger.debug("\tilona.output="+sysprops.getProperty("ilona.output"));
		logger.debug("\tant.home="+sysprops.getProperty("ant.home"));
		logger.debug("\tANT_HOME="+sysprops.getProperty("ANT_HOME"));
		logger.debug("\tjava.class.path="+sysprops.getProperty("java.class.path"));
		logger.debug("\tPATH="+sysprops.getProperty("PATH"));
		logger.debug("\tlogger.path="+sysprops.getProperty("logger.path"));
		
		logger.debug("\tILONA_HOME="+System.getenv("ILONA_HOME"));
		logger.debug("\tFUXML_HOME="+System.getenv("FUXML_HOME"));
		
/*	
		for (Object o : sysprops.keySet())
		{
			logger.debug(o+": "+sysprops.getProperty((String) o));
		}
		
		for( String s : environmentParameters)
		{
			logger.debug("Env: "+s);
		}
*/	
	}
	
}
