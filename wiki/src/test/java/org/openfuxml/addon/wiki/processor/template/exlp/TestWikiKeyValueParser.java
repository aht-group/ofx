package org.openfuxml.addon.wiki.processor.template.exlp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.addon.wiki.processor.markup.TestWikiInlineProcessor;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestWikiKeyValueParser extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	public static void main(String args[])
	{
		OfxBootstrap.init();
			
		logger.warn("This is only a pattern test-class!");
		
		String sPattern = "^\\|([a-zA-Z]*)=(.*)";
		String sTest    = "|Goal=blabla bla blablub";
		
		logger.debug("Pattern: "+sPattern);
		logger.debug("Test:    "+sTest);
		
		Pattern p = Pattern.compile(sPattern);
		Matcher m = p.matcher(sTest);
		logger.debug(""+m.matches());
		if(m.matches())
		{
			logger.debug("Group Count "+m.groupCount());
			for(int i=0;i<=m.groupCount();i++)
			{
				logger.debug(i+" "+m.group(i));
			}
		}
	}
}