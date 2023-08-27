package org.openfuxml.util.media;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.factory.xml.io.XmlFileFactory;
import net.sf.exlp.factory.xml.io.XmlHashFactory;
import net.sf.exlp.util.io.HashUtil;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.xpath.IoXpath;

import org.apache.commons.io.IOUtils;
import org.exlp.model.xml.io.Dir;
import org.openfuxml.content.media.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MediaSourceModificationTracker
{
	final static Logger logger = LoggerFactory.getLogger(MediaSourceModificationTracker.class);
	
	private File trackerDb;
	private Dir dir;
	private MultiResourceLoader mrl;
	
	public MediaSourceModificationTracker(File trackerDb)
	{
		this.trackerDb=trackerDb;
		mrl = new MultiResourceLoader();
		try
		{
			dir = JaxbUtil.loadJAXB(trackerDb, Dir.class);
		}
		catch (FileNotFoundException e)
		{
			if(CrossMediaFileUtil.isParentDirectory(trackerDb))
			{
				dir = new Dir();
			}
			else
			{
				e.printStackTrace();
			}
			
		}
	}
	
	public boolean isChanged(Media media)
	{
		String hashValue;
		boolean srcHasChanged = true;
		
		try
		{
			InputStream is = mrl.searchIs(media.getSrc());
			hashValue = HashUtil.hash(IOUtils.toByteArray(is));
		}
		catch (FileNotFoundException e) {e.printStackTrace();return true;}
		catch (IOException e) {e.printStackTrace();return true;}
		
		try
		{
			org.exlp.model.xml.io.File file = IoXpath.getFileByName(dir, media.getSrc());
			srcHasChanged = !hashValue.equals(file.getHash().getValue());
			file.getHash().setValue(hashValue);
		}
		catch (ExlpXpathNotFoundException e)
		{
			logger.error(e.getMessage());
			org.exlp.model.xml.io.File file = XmlFileFactory.build(media.getSrc());
			file.setHash(XmlHashFactory.build(hashValue));
			dir.getFile().add(file);
		}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		if(srcHasChanged){logger.info("isChanged: "+media.getSrc()+" "+srcHasChanged);}
		return srcHasChanged;
	}
	
	public void persist()
	{
		JaxbUtil.save(trackerDb, dir, true);
		JaxbUtil.trace(dir);
	}
}
