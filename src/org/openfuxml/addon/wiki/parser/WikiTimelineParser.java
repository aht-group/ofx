package org.openfuxml.addon.wiki.parser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
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

public class WikiTimelineParser extends AbstractLogParser implements LogParser  
{
	private static Logger logger = Logger.getLogger(WikiTimelineParser.class);
	private static enum Section {none,bardata,plotdata};
	
	private Section section;
	private ArrayList<Pattern> alP,alPBarData,alPPlotData;
	
	private Map<String,String> mapBarLabel;
	
	private StringBuffer sbParseLine;
	
	public WikiTimelineParser(LogEventHandler leh)
	{
		super(leh);
		alP=new ArrayList<Pattern>();
		alPBarData=new ArrayList<Pattern>();
		alPPlotData=new ArrayList<Pattern>();
		
		alP.add(Pattern.compile("BarData=(.*)"));
		alP.add(Pattern.compile("PlotData=(.*)"));
		
		alPBarData.add(Pattern.compile("[ ]*bar:(\\d*) text:([\\d\\w]*)(.*)"));
		
		alPPlotData.add(Pattern.compile("[ ]*bar:(\\d*) from:(\\d*) till: (\\d*)(.*)"));
		alPPlotData.add(Pattern.compile("[ ]*bar:(\\d*) at:(\\d*)(.*)"));
		alPPlotData.add(Pattern.compile("[ ]*color:(\\w*)(.*)"));
	}

	public void parseLine(String line)
	{
		sbParseLine.append(" parseLine:"+line);
		allLines++;
		boolean unknownPattern = true;
		for(int i=0;i<alP.size();i++)
		{
			Matcher m=alP.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: section = Section.bardata;sbParseLine.append(" new-BarData");break;
					case 1: section = Section.plotdata;sbParseLine.append(" new-PlotData");break;		
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
	
	private void parseBarData(String line)
	{
		sbParseLine.append(" parseBarData:"+line);
		boolean unknownPattern = true;
		for(int i=0;i<alPBarData.size();i++)
		{
			Matcher m=alPBarData.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: mapBarLabel.put(m.group(1), m.group(2));break;	
				}
				i=alPBarData.size();
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			sbParseLine.append(" UNKNOW ... parsing line!");
			section = Section.none;
			parseLine(line);
		}
	}
	
	private void parsePlotData(String line)
	{
		sbParseLine.append(" parsePlotData:"+line);
		boolean unknownPattern = true;
		for(int i=0;i<alPPlotData.size();i++)
		{
			Matcher m=alPPlotData.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: break;	
				}
				i=alPPlotData.size();
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			section = Section.none;
			parseLine(line);
		}
	}
	
	private void parse(String line)
	{
		sbParseLine = new StringBuffer();
		sbParseLine.append("Section:"+section);
		switch(section)
		{
			case none: parseLine(line);break;
			case bardata: parseBarData(line);break;
			case plotdata: parsePlotData(line);break;
		}
		logger.trace(sbParseLine);
	}
	
	@Override
	public void parseItem(ArrayList<String> item)
	{
		initDataContainer();
		section = Section.none;
		logger.debug("Item received with "+item.size()+" entries");
		for(String line : item){parse(line);}
		debugDataContainer();
	}
	
	private void initDataContainer()
	{
		mapBarLabel = new Hashtable<String,String>();
	}
	
	private void debugDataContainer()
	{
		logger.debug("Barlabel: "+mapBarLabel.size());
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		LogEventHandler leh = new EhDebug();
		LogParser lp = new WikiTimelineParser(leh);
		LogListener ll = new LogListenerXml("resources/data/timeline.xml",lp);
		ll.processMulti("/wikiinjection/wikicontent");
	}
}