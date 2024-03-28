package org.openfuxml.addon.wiki.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openfuxml.addon.wiki.event.WikiImageEvent;
import org.openfuxml.model.xml.addon.wiki.Ofxgallery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.core.parser.AbstractLogParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;

public class WikiGalleryParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(WikiGalleryParser.class);
	
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
		Ofxgallery.Ofximage ofxImage = new Ofxgallery.Ofximage();
		ofxImage.setWikilink(m.group(1));
		ofxImage.setValue(m.group(2));
		WikiImageEvent event = new WikiImageEvent(ofxImage);
		leh.handleEvent(event);
	}
}