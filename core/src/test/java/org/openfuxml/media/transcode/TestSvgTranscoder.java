package org.openfuxml.media.transcode;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.util.XMLResourceDescriptor;
import org.junit.Before;
import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
		MultiResourceLoader mrl = new MultiResourceLoader();
		InputStream is = mrl.searchIs(svgTest3); 
		OutputStream os = new FileOutputStream(new File(fTarget,"test.png"));
		Svg2PngTranscoder.transcode(20, is, os);
		os.close();
	}
	
	public void url() throws MalformedURLException, IOException, ParserConfigurationException, SAXException
	{
//		String s = "https://openclipart.org/download/228858";
//		InputStream is = new URL(s).openStream();
		
        File file = new File("/Volumes/ramdisk/tv2.svg");
        InputStream is = new FileInputStream(file);
		
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
	
	public void stack() throws MalformedURLException, IOException
	{
		SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());

//		https://github.com/apache/batik/blob/trunk/samples/chessboard.svg
        File file = new File("/Volumes/ramdisk/tv2.svg");
        InputStream is = new FileInputStream(file);

        Document document = factory.createDocument(null, is);
        UserAgent agent = new UserAgentAdapter();
        DocumentLoader loader= new DocumentLoader(agent);
        BridgeContext context = new BridgeContext(agent, loader);
        context.setDynamic(true);
        GVTBuilder builder= new GVTBuilder();
        GraphicsNode root= builder.build(context, document);

        System.out.println(root.getPrimitiveBounds().getWidth());
        System.out.println(root.getPrimitiveBounds().getHeight());
	}
	
	public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestSvgTranscoder test = new TestSvgTranscoder();
    	test.init();
    	
//    	test.pdf();
//    	test.png();
    	test.pngHeight();
//    	test.url();
//    	test.stack();
    }
}