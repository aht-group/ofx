package org.openfuxml.media.transcode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Svg2SvgTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(Svg2SvgTranscoder.class);
	
	public static byte[] transcode(SVGGraphics2D g) throws IOException, TranscoderException
	{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		boolean useCSS = true; // we want to use CSS style attributes
	    Writer out = new OutputStreamWriter(os, "UTF-8");
	    g.stream(out, useCSS);
	    return os.toByteArray();
	}
	
	public static void transcode(SVGGraphics2D g, File f) throws IOException, TranscoderException
	{
		FileUtils.writeByteArrayToFile(f, transcode(g));
	}
}