package org.openfuxml.media.transcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;
import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.media.CrossMediaTranscoder;
import org.openfuxml.media.cross.LatexCrossMediaManager;
import org.openfuxml.util.media.CrossMediaFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Svg2PdfTranscoder implements CrossMediaTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(LatexCrossMediaManager.class);
	
	private File dir;
	private MultiResourceLoader mrl;
	
	public Svg2PdfTranscoder(File dir)
	{
		this.dir=new File(dir,"pdf");
		mrl = new MultiResourceLoader();
	}
	
	@Override
	public void transcode(Media media) throws OfxAuthoringException
	{
		File file = buildTarget(media);
		CrossMediaFileUtil.createParentDirs(file);
		logger.info("Transcoding to "+file.getAbsolutePath());
		
		
	       
			try
			{
				 TranscoderInput input_svg_image = new TranscoderInput(mrl.searchIs(media.getSrc()));        
			        //Step-2: Define OutputStream to PDF file and attach to TranscoderOutput
				 OutputStream pdf_ostream = new FileOutputStream(file);
				 TranscoderOutput output_pdf_file = new TranscoderOutput(pdf_ostream);               
			        // Step-3: Create a PDF Transcoder and define hints
			        Transcoder transcoder = new PDFTranscoder();
			        // Step-4: Write output to PDF format
			        transcoder.transcode(input_svg_image, output_pdf_file);
			        // Step 5- close / flush Output Stream
			        pdf_ostream.flush();
			        pdf_ostream.close();      
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TranscoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          
	}
	
	@Override
	public boolean isTargetExisting(Media media)
	{
		File file = buildTarget(media);
		return file.exists() && file.isFile();
	}

	@Override
	public File buildTarget(Media media)
	{
		return new File(dir,media.getDst()+".pdf");
	}
}
