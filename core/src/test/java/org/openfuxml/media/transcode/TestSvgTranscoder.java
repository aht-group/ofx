package org.openfuxml.media.transcode;

import java.io.File;

import org.junit.Before;
import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSvgTranscoder extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestSvgTranscoder.class);

	private Media media;
	private File fTarget;
	
	@Before
	public void init()
	{
		fTarget = new File("/Volumes/ramdisk");
		
		media = new Media();
		media.setSrc("data/svg/under-construction.svg");
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
	
	public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestSvgTranscoder test = new TestSvgTranscoder();
    	test.init();
    	test.pdf();
    	test.png();
    }
}