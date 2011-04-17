package org.openfuxml.addon.wiki.util;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WikiConfigChecker
{
	static Log logger = LogFactory.getLog(WikiConfigChecker.class);
	
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