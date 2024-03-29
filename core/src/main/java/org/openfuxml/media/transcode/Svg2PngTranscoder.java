package org.openfuxml.media.transcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.media.XmlImageFactory;
import org.openfuxml.interfaces.media.CrossMediaTranscoder;
import org.openfuxml.model.xml.core.media.Image;
import org.openfuxml.model.xml.core.media.Media;
import org.openfuxml.util.media.CrossMediaFileUtil;
import org.openfuxml.util.media.ImageDimensionRatio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StreamUtil;

public class Svg2PngTranscoder extends AbstractCrossMediaTranscoder implements CrossMediaTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(Svg2PngTranscoder.class);

	public Svg2PngTranscoder(File dir)
	{
		super(new File(dir,"png"));
	}

	@Override public File buildTarget(Media media) {return new File(dir,media.getDst()+".png");}

	@Override
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
		InputStream is = new ByteArrayInputStream(Svg2SvgTranscoder.transcode(g));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		transcode(height,is,os);
	    return os.toByteArray();
	}

	public static void transcode(Integer height, InputStream is, OutputStream os) throws TranscoderException, IOException
	{
		List<InputStream> streams = StreamUtil.clone(is,2);

		Image image = sizeOfPng(streams.get(0));

		if(height!=null)
		{
			JaxbUtil.trace(image);
			image = ImageDimensionRatio.height(image, height);
			JaxbUtil.trace(image);
		}

		TranscoderInput tIn = new TranscoderInput(streams.get(1));
	    TranscoderOutput tOut = new TranscoderOutput(os);
	    PNGTranscoder t = new PNGTranscoder();

	    t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, Double.valueOf(image.getHeight().getValue()).floatValue());
	    t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, Double.valueOf(image.getWidth().getValue()).floatValue());

	    t.transcode(tIn,tOut);
	    os.flush();
	    os.close();
	}

	@Deprecated private static Image sizeOfPng(InputStream is) throws TranscoderException, IOException
	{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TranscoderInput tIn = new TranscoderInput(is);
	    TranscoderOutput tOut = new TranscoderOutput(os);
	    PNGTranscoder t = new PNGTranscoder();

	    t.transcode(tIn,tOut);
	    os.flush();
	    os.close();

	    ByteArrayInputStream input = new ByteArrayInputStream(os.toByteArray());

	    BufferedImage bimg = ImageIO.read(input);
	    int width          = bimg.getWidth();
	    int height         = bimg.getHeight();
	    return XmlImageFactory.px(width, height);
	}

//	@Deprecated private static Image sizeOfSvg(InputStream is) throws TranscoderException, IOException
//	{
//		SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
//
//	    Document document = factory.createDocument(null, is);
//	    UserAgent agent = new UserAgentAdapter();
//	    DocumentLoader loader= new DocumentLoader(agent);
//	    BridgeContext context = new BridgeContext(agent, loader);
//	    context.setDynamic(true);
//	    GVTBuilder builder= new GVTBuilder();
//	    GraphicsNode root= builder.build(context, document);
//
//	    return XmlImageFactory.size(root.getPrimitiveBounds().getWidth(), root.getPrimitiveBounds().getHeight());
//	}
}