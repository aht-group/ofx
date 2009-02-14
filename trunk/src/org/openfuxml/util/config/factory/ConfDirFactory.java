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
	
	public Dirs getDirs()
	{
		Dirs dirs = new Dirs();
	
		Dir dir = new Dir();
			dir.setType("basedir");
			dir.setContent(".");
			dir.setRel(true);
			dirs.getDir().add(dir);
		dir = new Dir();
			dir.setType("logs");
			dir.setContent("share/logs");
			dir.setRel(true);
			dirs.getDir().add(dir);
		dir = new Dir();
			dir.setType("output");
			dir.setContent("share/output");
			dir.setRel(true);
			dirs.getDir().add(dir);
		dir = new Dir();
			dir.setType("repository");
			dir.setContent("share/repositry");
			dir.setRel(true);
			dirs.getDir().add(dir);
		 
		return dirs;
	} 
}