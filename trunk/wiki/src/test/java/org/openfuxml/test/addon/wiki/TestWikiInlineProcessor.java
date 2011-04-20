package org.openfuxml.test.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;

import junit.framework.TestCase;
import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Before;
import org.openfuxml.addon.wiki.WikiInlineProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.xml.renderer.cmp.Cmp;

public class TestWikiInlineProcessor extends TestCase
{
	static Log logger = LogFactory.getLog(TestWikiInlineProcessor.class);
	
	public TestWikiInlineProcessor()
	{
		
	}
	
	@Before
	public void initx()
	{
		System.out.println("Test");
		Logger root = Logger.getRootLogger();
		root.addAppender(new ConsoleAppender(
		    new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
	}
	
	public void testDummy() throws Exception
	{
		int variable =0;
		assertTrue(variable == 1);
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String propFile = "resources/properties/user.properties";
		if(args.length==1){propFile=args[0];}
		ConfigLoader.add(propFile);
		Configuration config = ConfigLoader.init();
			
		File f = new File("resources/data/wiki/wikiPlain/1.txt");
		String wikiTxt = WikiContentIO.loadTxt(f);
		
		String fNameCmp = config.getString("ofx.xml.cmp");
		Cmp cmp = (Cmp)JaxbUtil.loadJAXB(fNameCmp, Cmp.class);
		
		WikiInlineProcessor wikiInline = new WikiInlineProcessor(cmp);
		Section section = wikiInline.toOfx(wikiTxt);
		JaxbUtil.debug(section);
    }
}