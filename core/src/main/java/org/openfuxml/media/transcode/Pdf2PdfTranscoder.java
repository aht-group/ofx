package org.openfuxml.media.transcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.io.FileIO;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.apache.commons.io.IOUtils;
import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.media.cross.LatexCrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pdf2PdfTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(LatexCrossMediaManager.class);
	
	private File dir;
	private MultiResourceLoader mrl;
	
	public Pdf2PdfTranscoder(File dir)
	{
		this.dir=dir;
		mrl = new MultiResourceLoader();
	}
	
	public void transcode(Media media) throws OfxAuthoringException
	{
		File file = new File(dir,media.getDst()+".pdf");
		createParentDir(file);
		logger.info("Transcoding to :"+file.getAbsolutePath());
		
		try
		{
			InputStream is = mrl.searchIs(media.getSrc());
			byte[] bytes = IOUtils.toByteArray(is);
			FileIO.writeFileIfDiffers(bytes, file);
		}
		catch (FileNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (IOException e) {throw new OfxAuthoringException(e.getMessage());}
		
//		FileIO.writeFileIfDiffers(bytes, fTarget);
	}
	
	private void createParentDir(File file)
	{
		if(!file.getParentFile().exists()){file.getParentFile().mkdirs();}
	}
}
