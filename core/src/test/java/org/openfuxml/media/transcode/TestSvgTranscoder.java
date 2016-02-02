package org.openfuxml.media.transcode;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import net.sf.exlp.util.io.StreamUtil;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

public class TestSvgTranscoder extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestSvgTranscoder.class);

	public static final String svgTest = "data/svg/under-construction.svg";
	
	private Media media;
	private File fTarget;
	
	@Before
	public void init()
	{
		fTarget = new File("/Volumes/ramdisk");
		
		media = new Media();
		media.setSrc(svgTest);
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
		MultiResourceLoader mrl = new MultiResourceLoader();
		InputStream is = mrl.searchIs(svgTest); 
		OutputStream os = new FileOutputStream(new File(fTarget,"test.png"));
		Svg2PngTranscoder.transcode(20, is, os);
		os.close();
	}
	
	public void url() throws MalformedURLException, IOException, ParserConfigurationException, SAXException
	{
		String s = "https://openclipart.org/download/228858";
		InputStream is = new URL(s).openStream();
		
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = f.newDocumentBuilder();
        Document doc = builder.parse(is);

        SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(doc);
        SVGGraphics2D svg = new SVGGraphics2D(ctx,false);
        
        Dimension d = svg.getSVGCanvasSize();
        Rectangle r = svg.getClipBounds();
        
        System.out.println(svg.toString());
        System.out.println("Dimension null? "+(d==null));
        System.out.println("Rectangle null? "+(r==null));
	}
	
	public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestSvgTranscoder test = new TestSvgTranscoder();
    	test.init();
    	
//    	test.pdf();
//    	test.png();
//    	test.pngHeight();
    	test.url();
    }
}