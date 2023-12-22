package org.openfuxml.media.transcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.media.CrossMediaTranscoder;
import org.openfuxml.model.xml.core.media.Media;
import org.openfuxml.util.media.CrossMediaFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.FileIO;

public class Pdf2PdfTranscoder extends AbstractCrossMediaTranscoder implements CrossMediaTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(Svg2PngTranscoder.class);
	
	public Pdf2PdfTranscoder(File dir)
	{
		super(new File(dir,"pdf"));
	}
	
	@Override public File buildTarget(Media media) {return new File(dir,media.getDst()+".pdf");}
	
	@Override
	public void transcode(Media media) throws OfxAuthoringException
	{
		File file = buildTarget(media);
		CrossMediaFileUtil.createParentDirs(file);
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
}