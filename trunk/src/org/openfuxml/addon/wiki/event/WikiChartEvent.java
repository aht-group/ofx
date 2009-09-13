package org.openfuxml.addon.wiki.event;

import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;

import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;

public class WikiChartEvent extends AbstractEvent implements LogEvent
{
	static Logger logger = Logger.getLogger(WikiChartEvent.class);
	static final long serialVersionUID=1;
	
	private Ofxchart ofxChart;
	
	public WikiChartEvent(Ofxchart ofxChart)
	{
		this.ofxChart=ofxChart;
	}
	
	public void debug()
	{
		super.debug();
	}
	
	public Ofxchart getOfxChart() {return ofxChart;}
	public void setOfxChart(Ofxchart ofxChart) {this.ofxChart = ofxChart;}

}