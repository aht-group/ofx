package org.openfuxml.addon.wiki.media.image;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import net.sourceforge.jwbf.actions.mediawiki.queries.ImageInfo;
import net.sourceforge.jwbf.actions.mediawiki.util.VersionException;
import net.sourceforge.jwbf.actions.util.ActionException;
import net.sourceforge.jwbf.actions.util.ProcessException;
import net.sourceforge.jwbf.bots.MediaWikiBot;

import org.apache.log4j.Logger;
import org.apache.xmlgraphics.java2d.ps.EPSDocumentGraphics2D;

public class WikiImageProcessor
{
	static Logger logger = Logger.getLogger(WikiImageProcessor.class);
	
	private BufferedImage image;
	
	public WikiImageProcessor()
	{
		
	}
	
	public void fetch(String imageName)
	{
		try
		{
			MediaWikiBot bot = new MediaWikiBot("http://de.wikipedia.org/w/");
			ImageInfo wikiImage = new ImageInfo(bot, imageName);
			logger.debug(wikiImage.getUrlAsString());
			image = wikiImage.getAsImage();
		} 
		catch (MalformedURLException e) {logger.error(e);}
		catch (VersionException e) {logger.error(e);}
		catch (ProcessException e) {logger.error(e);}
		catch (ActionException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}
	}
	
	public void save()
	{
		File baseDir = new File("dist");
		savePNG(baseDir, "image.png");
		saveEPS(baseDir, "image.eps");
	}
	
	private void savePNG(File baseDir, String fileName)
	{
		File f = new File(baseDir,fileName);
		try
		{
			ImageIO.write( image, "png", f);
		}
		catch (IOException e) {logger.error(e);}
	}
	
	private void saveEPS(File baseDir, String fileName)
	{	
		File f = new File(baseDir,fileName);
		try
		{
			EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);
	        g2d.setGraphicContext(new org.apache.xmlgraphics.java2d.GraphicContext());
			FileOutputStream out = new FileOutputStream(f);
			g2d.setupDocument(out, image.getWidth(), image.getHeight());
			g2d.drawImage(image, 0, 0, null);
	        g2d.finish();
	        out.close();
		}
		catch (FileNotFoundException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}
	}
}