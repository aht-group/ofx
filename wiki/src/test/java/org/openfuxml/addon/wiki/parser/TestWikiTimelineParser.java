package org.openfuxml.addon.wiki.parser;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.addon.wiki.event.WikiChartEvent;
import org.openfuxml.addon.wiki.processor.markup.TestWikiInlineProcessor;
import org.openfuxml.model.xml.addon.wiki.Ofxchart;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.core.handler.EhResultContainer;
import net.sf.exlp.core.listener.LogListenerXml;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;

@RunWith(Parameterized.class)
public class TestWikiTimelineParser extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	public static void main(String args[])
	{
		OfxBootstrap.init();
			
		LogEventHandler leh = new EhResultContainer();
		LogParser lp = new WikiTimelineParser(leh);
		LogListener ll = new LogListenerXml("resources/data/timeline.xml",lp);
		ll.processMulti("/wikiinjection/wikicontent");
		EhResultContainer results = (EhResultContainer)leh;
		logger.debug("Results: "+results.getAlResults().size());
		WikiChartEvent event = (WikiChartEvent)results.getAlResults().get(0);
		Ofxchart ofxChart = event.getOfxChart();
		
		try
		{
			JAXBContext context = JAXBContext.newInstance(Ofxchart.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
			m.marshal(ofxChart, new File("resources/data/timeline-ofxchart.xml"));
		}
		catch (JAXBException e) {logger.error("",e);}
	}
}