package org.openfuxml.addon.wiki.parser;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.addon.wiki.processor.markup.TestWikiInlineProcessor;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.core.handler.EhDebug;
import net.sf.exlp.core.listener.LogListenerXml;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;

@RunWith(Parameterized.class)
public class TestWikiGalleryParser extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	public static void main(String args[])
	{
		OfxBootstrap.init();
			
		LogEventHandler leh = new EhDebug();
		LogParser lp = new WikiGalleryParser(leh);
		LogListener ll = new LogListenerXml("resources/data/gallery.xml",lp);
		ll.processSingle("/wikiinjection/wikicontent");
	}
}