package org.openfuxml.media.transcode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.openfuxml.media.cross.LatexCrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Svg2PngTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(LatexCrossMediaManager.class);
	
	public static byte[] transcode(SVGGraphics2D g) throws IOException, TranscoderException
	{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Writer w = new OutputStreamWriter(os, "UTF-8");
		g.stream(w);
	    
		InputStream isFromFirstData = new ByteArrayInputStream(os.toByteArray()); 
		File pngFile = new File("/Volumes/ramdisk/test.png");
	    PNGTranscoder t = new PNGTranscoder();
	   
	    TranscoderInput input = new TranscoderInput(isFromFirstData);
	    pngFile.createNewFile();
	     
	    ByteArrayOutputStream ostream = new ByteArrayOutputStream();
	    TranscoderOutput output = new TranscoderOutput(ostream);
	     
	    t.transcode(input, output);
	    ostream.flush();
	    ostream.close();
	    return ostream.toByteArray();
	}
}
