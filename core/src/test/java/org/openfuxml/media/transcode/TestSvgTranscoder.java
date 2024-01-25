package org.openfuxml.media.transcode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.batik.transcoder.TranscoderException;
import org.junit.Before;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.core.media.Media;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

public class TestSvgTranscoder extends AbstractOfxCoreTest
{
	final static Logger logger = LoggerFactory.getLogger(TestSvgTranscoder.class);

	public static final String svgTest1 = "data/svg/under-construction.svg";
	public static final String svgTest2 = "/Volumes/ramdisk/landscape.svg";
	public static final String svgTest3 = "/Volumes/ramdisk/tv2.svg";

	private Media media;
	private File fTarget;

	@Before
	public void init()
	{
		fTarget = new File("/Volumes/ramdisk");

		media = new Media();
		media.setSrc(svgTest1);
		media.setDst("test");
	}

	public void pdf() throws OfxAuthoringException
	{
		Svg2PdfTranscoder transcoder = new Svg2PdfTranscoder(fTarget);
		transcoder.transcode(media);
	}

	public void png() throws OfxAuthoringException
	{
		Svg2PngTranscoder transcoder = new Svg2PngTranscoder(fTarget);
		transcoder.transcode(media);
	}

	public void pngHeight() throws TranscoderException, IOException
	{
		MultiResourceLoader mrl = MultiResourceLoader.instance();
		InputStream is = mrl.searchIs(svgTest3);
		OutputStream os = new FileOutputStream(new File(fTarget,"test.png"));
		Svg2PngTranscoder.transcode(20, is, os);
		os.close();
	}

	public static void main(String[] args) throws Exception
    {
    	OfxCoreBootstrap.init();

    	TestSvgTranscoder test = new TestSvgTranscoder();
    	test.init();

    	test.pdf();
    	test.png();
 //   	test.pngHeight();
//    	test.url();
//    	test.stack();
    }
}