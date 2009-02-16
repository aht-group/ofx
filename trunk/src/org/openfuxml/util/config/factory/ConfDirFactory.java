package org.openfuxml.util.config.factory;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.openfuxml.util.config.jaxb.Dirs;
import org.openfuxml.util.config.jaxb.Dirs.Dir;

public class ConfDirFactory
{
	static Logger logger = Logger.getLogger(ClientConfFactory.class);
	protected static String fs = SystemUtils.FILE_SEPARATOR;
	
	public ConfDirFactory()
	{
		
	}
	
	public Dirs getDirs(AbstractConfFactory.StartUpEnv startupenv, String baseDir, String openFuxmlVersion)
	{
		Dirs dirs = new Dirs();
	
		Dir dirBase = new Dir();
			dirBase.setType("basedir");
			dirBase.setContent(baseDir);
			dirBase.setRel(false);
			
		Dir dirLog = new Dir();
			dirLog.setType("log");
			dirLog.setRel(true);
			
		Dir dirOutput = new Dir();
			dirOutput.setType("output");
			dirOutput.setRel(true);
			
		Dir dirRepo = new Dir();
			dirRepo.setType("repository");
			dirRepo.setRel(true);
			
		Dir dirLib = new Dir();
			dirLib.setType("lib");
			dirLib.setRel(true);	
		
		switch(startupenv)
		{
			case DEVELOPER:		dirLog.setContent("dist"+fs+"logs");
								dirOutput.setContent("dist"+fs+"output");
								dirRepo.setContent("resources"+fs+"repository");
								dirLib.setContent("build"+fs+"app"+fs+"openFuXML-"+openFuxmlVersion+fs+"lib");break;
			case PRODUCTION: 	dirLog.setContent("share"+fs+"logs");
								dirOutput.setContent("share"+fs+"output");
								dirRepo.setContent("share"+fs+"repository");
								dirLib.setContent("libs");break;
		}
			
		dirs.getDir().add(dirBase);	
		dirs.getDir().add(dirLog);
		dirs.getDir().add(dirOutput);
		dirs.getDir().add(dirRepo);
		dirs.getDir().add(dirLib);
		
		return dirs;
	} 
}