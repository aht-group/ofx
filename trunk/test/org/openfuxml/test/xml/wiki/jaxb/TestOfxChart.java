package org.openfuxml.test.xml.wiki.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchartdata;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchartdataset;

import de.kisner.util.LoggerInit;

public class TestOfxChart
{
	private static Logger logger = Logger.getLogger(TestOfxChart.class);
	
	public TestOfxChart()
	{
		
	}
	
	public void xmlConstruct()
	{
		Ofxchart chart = new Ofxchart();
		chart.setType("bar");
		
		Ofxchartdata cs = new Ofxchartdata();
		cs.setType("x");
		cs.setValue(1924);
		
		Ofxchartdataset cDs = new Ofxchartdataset();
		cDs.getOfxchartdata().add(cs);
		
		chart.getOfxchartdataset().add(cDs);
		
		try
		{
			JAXBContext context = JAXBContext.newInstance(Ofxchart.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
			m.marshal(chart, System.out);
		}
		catch (JAXBException e) {logger.error(e);}
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestOfxChart test = new TestOfxChart();
			test.xmlConstruct();
	}
}