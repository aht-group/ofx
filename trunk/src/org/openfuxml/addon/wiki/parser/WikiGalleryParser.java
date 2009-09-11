package org.openfuxml.addon.wiki.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhDebug;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerXml;
import net.sf.exlp.parser.AbstractLogParser;
import net.sf.exlp.parser.LogParser;

import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.event.WikiGalleryEvent;

public class WikiGalleryParser extends AbstractLogParser implements LogParser  
{
	private static Logger logger = Logger.getLogger(WikiGalleryParser.class);
	
	private ArrayList<Pattern> alP;
	
	public WikiGalleryParser(LogEventHandler leh)
	{
		super(leh);
		alP=new ArrayList<Pattern>();
		
		alP.add(Pattern.compile("[ ]*Bild:([&\\w\\s\\.]+)\\|(.*)"));
	}

	@Override
	public void parseLine(String line)
	{
		allLines++;
		boolean unknownPattern = true;
		for(int i=0;i<alP.size();i++)
		{
			Matcher m=alP.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: event(m);break;
				}
				i=alP.size();
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			logger.warn("Unknown pattern: " +line);
			unknownLines++;
		}
	}
	
	private void event(Matcher m)
	{
		WikiGalleryEvent event = new WikiGalleryEvent(m.group(1),m.group(2));
		leh.handleEvent(event);
	}

	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		LogEventHandler leh = new EhDebug();
		LogParser lp = new WikiGalleryParser(leh);
		LogListener ll = new LogListenerXml("resources/data/gallery.xml",lp);
		ll.processSingle("/wikiinjection/wikicontent");
	}
}