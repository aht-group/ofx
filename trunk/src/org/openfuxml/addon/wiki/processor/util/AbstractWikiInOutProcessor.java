package org.openfuxml.addon.wiki.processor.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractWikiInOutProcessor
{
	static Log logger = LogFactory.getLog(AbstractWikiInOutProcessor.class);
	
	protected File srcDir,dstDir;
	
	public void setDirectories(File srcDir, File dstDir)
	{
		this.srcDir=srcDir;
		this.dstDir=dstDir;
		logger.debug("Directory Src: "+srcDir.getAbsolutePath());
		logger.debug("Directory Dst: "+dstDir.getAbsolutePath());
	}
}
