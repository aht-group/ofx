package org.openfuxml.media.transcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.media.CrossMediaTranscoder;
import org.openfuxml.model.xml.core.media.Media;
import org.openfuxml.util.media.CrossMediaFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Svg2PdfTranscoder extends AbstractCrossMediaTranscoder implements CrossMediaTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(Svg2PngTranscoder.class);
	
	public Svg2PdfTranscoder(File dir)
	{
		super(new File(dir,"pdf"));
	}
	
	@Override public File buildTarget(Media media) {return new File(dir,media.getDst()+".pdf");}
	
	@Override public void transcode(Media media) throws OfxAuthoringException
	{
		File file = buildTarget(media);
		CrossMediaFileUtil.createParentDirs(file);
		logger.info("Transcoding to "+file.getAbsolutePath());
		
		try
		{
			InputStream is = mrl.searchIs(media.getSrc());
			Svg2PdfTranscoder.transcode(is,file);
		}
		catch (FileNotFoundException e){e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		catch (TranscoderException e) {e.printStackTrace();}
	}
	
	public static void transcode(File fSvg, File fPdf) throws TranscoderException, IOException
	{
		InputStream is = new FileInputStream(fSvg);
		Svg2PdfTranscoder.transcode(is, fPdf);
	}
	
	public static void transcode(InputStream is, File fPdf) throws TranscoderException, IOException
	{
		TranscoderInput tiSvg = new TranscoderInput(is);        

		OutputStream os = new FileOutputStream(fPdf);
		TranscoderOutput toPdf = new TranscoderOutput(os);               

		Transcoder transcoder = new PDFTranscoder();
		transcoder.transcode(tiSvg, toPdf);

		os.flush();
		os.close(); 
	}
}