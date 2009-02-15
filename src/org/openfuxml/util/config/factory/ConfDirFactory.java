package org.openfuxml.util.config.factory;

import org.apache.log4j.Logger;
import org.openfuxml.util.config.jaxb.Dirs;
import org.openfuxml.util.config.jaxb.Dirs.Dir;

public class ConfDirFactory
{
	static Logger logger = Logger.getLogger(ClientConfFactory.class);
	
	public ConfDirFactory()
	{
		
	}
	
	public Dirs getDirs(AbstractConfFactory.StartUpEnv startupenv, String baseDir)
	{
		Dirs dirs = new Dirs();
	
		Dir dirBase = new Dir();
			dirBase.setType("basedir");
			dirBase.setContent(baseDir);
			dirBase.setRel(false);
			
		Dir dirLog = new Dir();
			dirLog.setType("logs");
			dirLog.setRel(true);
			
		Dir dirOutput = new Dir();
			dirOutput.setType("output");
			dirOutput.setRel(true);
			
		Dir dirRepo = new Dir();
			dirRepo.setType("repository");
			dirRepo.setRel(true);	
		
		switch(startupenv)
		{
			case DEVELOPER:		dirLog.setContent("dist/logs");
								dirOutput.setContent("dist/output");
								dirRepo.setContent("resources/repositry");break;
			case PRODUCTION: 	dirLog.setContent("share/logs");
								dirOutput.setContent("share/output");
								dirRepo.setContent("share/repositry");break;
		}
			
		dirs.getDir().add(dirBase);	
		dirs.getDir().add(dirLog);
		dirs.getDir().add(dirOutput);
		dirs.getDir().add(dirRepo);
		
		return dirs;
	} 
}