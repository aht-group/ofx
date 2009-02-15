package org.openfuxml.server;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.openfuxml.communication.cluster.ejb.Host;
import org.openfuxml.util.FuXmlLogger;

import de.kisner.util.HostCheck;
import de.kisner.util.architecture.ArchUtil;

public abstract class AbstractServer
{
	static Logger logger = Logger.getLogger(AbstractServer.class);
	
	protected static String fs = SystemUtils.FILE_SEPARATOR;
	public static String[] environmentParameters;
	public static String baseDir;
	
	protected Configuration config;
	
	public AbstractServer(Configuration config)
	{
		this.config=config;
	}
	
	protected Host getHost()
	{
		Host host = new Host();
			host.setHostName(HostCheck.getHostName());
			host.setHostIP(HostCheck.getHostIP());
			host.setRecord(new Date());
		return host;
	}
	
	public void setSystemProperties()
	{
		Properties sysprops = System.getProperties();
		Map<String,String> sysenv = System.getenv();
		
		baseDir = config.getString("dirs/dir[@type='basedir']");

		
		String pathLog = config.getString("dirs/dir[@type='log']");
		String pathRepo = config.getString("dirs/dir[@type='repository']");
		String pathOutput=config.getString("dirs/dir[@type='output']");
		
		if(!ArchUtil.isAbsolute(pathLog)){pathLog=baseDir+"/"+pathLog;}
		if(!ArchUtil.isAbsolute(pathRepo)){pathRepo=baseDir+"/"+pathRepo;}
		if(!ArchUtil.isAbsolute(pathOutput)){pathOutput=baseDir+"/"+pathRepo;}
		
		FuXmlLogger.initLogger(pathLog);
		
		logger.debug("baseDir="+baseDir);
		logger.debug("pathLog="+pathLog);
		logger.debug("pathRepo="+pathRepo);
		logger.debug("pathOutput="+pathOutput);
		
		String antHome = sysenv.get("ANT_HOME");
		if(antHome!=null){sysprops.put("ant.home",sysenv.get("ANT_HOME"));}
		else
		{
			antHome = baseDir+"/lib";
			logger.debug("ANT_HOME not set, using config.xml value");
			sysprops.put("ANT_HOME",antHome);
		}
			
		sysprops.put("ilona.home",baseDir);
		sysprops.put("ant.home",antHome);
		sysprops.put("logger.path",pathLog);
		sysprops.put("ilona.contentstore",pathRepo);
		sysprops.put("ilona.output",pathOutput);
		System.setProperties(sysprops);
		
		setenvP();
	}
	
	private void setenvP()
	{	
		Hashtable<String,String> htEnv = new Hashtable<String,String>();
		htEnv.putAll(System.getenv());
		
		//TODO Was passiert hier??
		List<String> lLibs = config.getList("files/file[@typ='lib']");
		String cp = ArchUtil.getClassPath(lLibs,baseDir+fs+"lib"+fs);
		htEnv.put("CLASSPATH",cp);
		logger.debug("Setting CLASSPATH for openFuXML Applications: "+cp);
		
		htEnv.put("ANT_HOME", baseDir+"/lib");
		logger.debug("Setting ANT_HOME for openFuXML Applications: "+htEnv.get("ANT_HOME"));
		
		ArchUtil.setArch();
		switch(ArchUtil.arch)
		{
			case OsX:	try
						{
							String systemPath = config.getString("dirs/dir[@type='path']");
							htEnv.put("PATH", systemPath);
						}
						catch (NoSuchElementException e)
						{
							logger.warn("No PATH defined in xmlConfig. Using System PATH "+htEnv.get("PATH"));
							logger.warn("   Errors are possible for "+SystemUtils.OS_NAME);
							logger.warn("   Please insert your $PATH <dirs><dir typ=\"path\">HERE</dir></dirs>");
						}
						break;
			case Win32:	try
						{
							String systemPath = config.getString("dirs/dir[@type='path']");
							htEnv.put("PATH", systemPath);
						}
						catch (NoSuchElementException e)
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
		
	
/*		for (Object o : sysprops.keySet())
		{
			logger.debug(o+": "+sysprops.getProperty((String) o));
		}
*/		
		for( String s : environmentParameters)
		{
			logger.debug("Env: "+s);
		}
	
	}
	
}
