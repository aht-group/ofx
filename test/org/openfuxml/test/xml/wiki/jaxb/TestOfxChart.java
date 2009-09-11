package org.openfuxml.test.xml.wiki.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchartcontainer;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchartdata;

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
		
		Ofxchartcontainer ds = new Ofxchartcontainer();
		ds.getOfxchartdata().add(cs);
		ds.setType("dataset");
		
		Ofxchartcontainer s = new Ofxchartcontainer();
		s.getOfxchartcontainer().add(ds);
		s.setType("dataseries");
		
		chart.getOfxchartcontainer().add(s);
		
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