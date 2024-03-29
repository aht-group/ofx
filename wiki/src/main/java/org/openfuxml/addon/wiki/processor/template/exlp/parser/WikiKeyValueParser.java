package org.openfuxml.addon.wiki.processor.template.exlp.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openfuxml.addon.wiki.processor.template.exlp.event.WikiKeyValueEvent;
import org.openfuxml.model.xml.addon.wiki.Markup;
import org.openfuxml.model.xml.addon.wiki.TemplateKv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.core.parser.AbstractLogParser;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;

public class WikiKeyValueParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(WikiKeyValueParser.class);
	
	private TemplateKv wikiKV;
	
	public WikiKeyValueParser(LogEventHandler leh)
	{
		super(leh);
		pattern.add(Pattern.compile("^$"));
		pattern.add(Pattern.compile("^\\|([a-zA-Z]*)=(.*)"));
		pattern.add(Pattern.compile("(.*)"));
		logger.debug("Pattern defined: "+pattern.size());
	}

	public void parseLine(String line)
	{
		allLines++;
		boolean unknownPattern = true;
		for(int i=0;i<pattern.size();i++)
		{
			Matcher m=pattern.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: blank();break;
					case 1: key(m);break;
					case 2: value(m);break;
				}
				i=pattern.size();
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			logger.warn("Unknown pattern: " +line);
			unknownLines++;
		}
	}
	
	private void blank()
	{
		if(wikiKV!=null)
		{
			
		}
	}
	
	private void key(Matcher m)
	{
		if(wikiKV!=null){event();}
		
		wikiKV = new TemplateKv();
		wikiKV.setKey(m.group(1));
		wikiKV.setMarkup(new Markup());
		wikiKV.getMarkup().setValue(m.group(2));
	}
	
	public void value(Matcher m)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(wikiKV.getMarkup().getValue());
		sb.append(System.lineSeparator());
		sb.append(m.group(0));
		wikiKV.getMarkup().setValue(sb.toString());
	}
	
	@Override
	public void close()
	{
		if(wikiKV!=null){event();};
	}
	
	public void event()
	{
		LogEvent event = new WikiKeyValueEvent(wikiKV);
		leh.handleEvent(event);
	}
}