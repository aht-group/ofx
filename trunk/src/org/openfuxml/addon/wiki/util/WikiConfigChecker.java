package org.openfuxml.addon.wiki.util;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

public class WikiConfigChecker
{
	static Logger logger = Logger.getLogger(WikiConfigChecker.class);
	
	public static synchronized void check(Configuration config)
	{
		for(String dir : config.getStringArray("/ofx/dir"))
		{
			File f = new File(dir);
			if(!f.exists())
			{
				logger.debug("mkdir: "+f.getAbsolutePath());
				f.mkdirs();
			}
		}
	}
}