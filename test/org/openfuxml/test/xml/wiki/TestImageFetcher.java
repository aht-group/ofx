package org.openfuxml.test.xml.wiki;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import net.sf.exlp.io.ConfigLoader;
import net.sourceforge.jwbf.actions.mediawiki.queries.ImageInfo;
import net.sourceforge.jwbf.actions.mediawiki.util.VersionException;
import net.sourceforge.jwbf.actions.util.ActionException;
import net.sourceforge.jwbf.actions.util.ProcessException;
import net.sourceforge.jwbf.bots.MediaWikiBot;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.media.image.WikiImageProcessor;
import org.openfuxml.addon.wiki.util.WikiConfigChecker;

import de.kisner.util.LoggerInit;

public class TestImageFetcher
{
	static Logger logger = Logger.getLogger(TestImageFetcher.class);
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/config/wiki/wiki.xml");
		Configuration config = ConfigLoader.init();
		WikiConfigChecker.check(config);
			
		WikiImageProcessor wip = new WikiImageProcessor(config);
		wip.fetch("Bellagio waterfront.jpg");
		wip.save("bellagio");
    }
}