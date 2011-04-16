package org.openfuxml.test.xml.util;

import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.util.xml.NsPrefixMapperInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.chart.data.jaxb.Chart;
import org.openfuxml.content.ofx.Metadata;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.xml.util.OfxNsPrefixMapper;

public class TstPrefixMapper
{
	static Log logger = LogFactory.getLog(TstPrefixMapper.class);
	
	private Chart chart;
	private Ofxdoc ofxDoc;
	
	private NsPrefixMapperInterface nsPrefixMapper;
	
	public TstPrefixMapper()
	{
		nsPrefixMapper = new OfxNsPrefixMapper();
		
		chart = new Chart();
		ofxDoc = new Ofxdoc();
		ofxDoc.setMetadata(new Metadata());
	}
	
	public void debug()
	{
		JaxbUtil.debug2(this.getClass(),chart,nsPrefixMapper);
		JaxbUtil.debug2(this.getClass(),ofxDoc,nsPrefixMapper);
	}
	
	public void directDebug()
	{
		directDebug(chart);
		directDebug(ofxDoc);
	}
	
	public void directDebug(Object jaxb)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(jaxb.getClass());
			 
			XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(new PrintWriter( System.out ) );
			xmlStreamWriter.setPrefix("ofx", "http://www.openfuxml.org");
			
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(jaxb, xmlStreamWriter);
		}
		catch (JAXBException e) {logger.error(e);}
		catch (XMLStreamException e) {logger.error(e);}
		catch (FactoryConfigurationError e) {logger.error(e);}
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
		
		TstPrefixMapper test = new TstPrefixMapper();
		test.debug();
//		test.directDebug();
	}
}
