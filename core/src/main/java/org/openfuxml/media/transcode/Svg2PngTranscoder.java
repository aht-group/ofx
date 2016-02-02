package org.openfuxml.media.transcode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.media.CrossMediaTranscoder;
import org.openfuxml.util.media.CrossMediaFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Svg2PngTranscoder extends AbstractCrossMediaTranscoder implements CrossMediaTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(Svg2PngTranscoder.class);
	
	public Svg2PngTranscoder(File dir)
	{
		super(new File(dir,"png"));
	}
	
	@Override public File buildTarget(Media media) {return new File(dir,media.getDst()+".png");}
	
	public void transcode(Media media) throws OfxAuthoringException
	{
		File file = buildTarget(media);
		CrossMediaFileUtil.createParentDirs(file);
		logger.info("Transcoding to "+file.getAbsolutePath());
		
		try
		{
			InputStream is = mrl.searchIs(media.getSrc());
		    OutputStream os = new FileOutputStream(file);
		    
		    transcode(null,is,os);
		}
		catch (FileNotFoundException e){e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		catch (TranscoderException e) {e.printStackTrace();}
	}
	
	public static byte[] transcode(Integer height, byte[] bin) throws TranscoderException, IOException
	{
		InputStream is = new ByteArrayInputStream(bin);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		transcode(height,is,os);
		return os.toByteArray();
	}
		
	public static byte[] transcode(SVGGraphics2D g) throws IOException, TranscoderException
	{
		return transcode(null,g);
	}
	
	public static byte[] transcode(Integer height, SVGGraphics2D g) throws IOException, TranscoderException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer w = new OutputStreamWriter(baos, "UTF-8");
		g.stream(w);
	    
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		transcode(height,is,os);
	    return os.toByteArray();
	}
	
	public static void transcode(Integer height, InputStream is, OutputStream os) throws TranscoderException, IOException
	{
//		List<InputStream> list = StreamUtil.clone(is1, 2);
		if(height!=null)
		{
//			SVGGraphics2D svg = getSVGGraphics2D(is);		
//			logger.info(svg.toString());
		}
		
		TranscoderInput tIn = new TranscoderInput(is);
	    TranscoderOutput tOut = new TranscoderOutput(os);
	    PNGTranscoder t = new PNGTranscoder();
	    if(height!=null){t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT,new Float(height));}
	    
	    t.transcode(tIn,tOut);
	    os.flush();
	    os.close();
	}
	
	public static SVGGraphics2D getSVGGraphics2D(InputStream is){

	    SVGGraphics2D svg = null;

	    try
	    {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document parse = builder.parse(is);

	        SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(parse);
	        svg = new SVGGraphics2D(ctx,false);

	    } catch (ParserConfigurationException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (SAXException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    return svg;
	}
}