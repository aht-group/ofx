package org.openfuxml.addon.wiki.media.chart;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartXmlUtil
{
	final static Logger logger = LoggerFactory.getLogger(ChartXmlUtil.class);
	
	public static synchronized Document loadChart(Ofxchart ofxChart)
	{
		Document doc = null;
		try
		{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(Ofxchart.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
			m.marshal(ofxChart, out);
			
			InputStream is = new ByteArrayInputStream(out.toByteArray());
			doc = new SAXBuilder().build(is);
			doc.setRootElement(unsetNameSpace(doc.getRootElement()));
					
//			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
//			xmlOut.output(doc, System.out);
		}
		catch (JAXBException e) {logger.error("",e);}
		catch (JDOMException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
		return doc;
	}
	
	private static synchronized Element unsetNameSpace(Element e)
	{
		e.setNamespace(null);
		for(Object o : e.getChildren())
		{
			Element eChild = (Element)o;
			eChild=unsetNameSpace(eChild);
		}
		return e;
	}
}