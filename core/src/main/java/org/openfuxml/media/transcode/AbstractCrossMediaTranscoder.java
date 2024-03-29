package org.openfuxml.media.transcode;

import java.io.File;

import org.openfuxml.media.cross.LatexCrossMediaManager;
import org.openfuxml.model.xml.core.media.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

public abstract class AbstractCrossMediaTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(LatexCrossMediaManager.class);
	
	protected File dir;
	protected MultiResourceLoader mrl;
	
	public AbstractCrossMediaTranscoder(File dir)
	{
		this.dir=dir;
		mrl = MultiResourceLoader.instance();
	}
	
	public boolean isTargetExisting(Media media)
	{
		File file = buildTarget(media);
		return file.exists() && file.isFile();
	}
	
	public File buildTarget(Media media){return null;}
}