package org.openfuxml.test.xml.jsftaglib.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.sf.exlp.util.JaxbUtil;

import org.apache.log4j.Logger;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.ObjectFactory;
import org.openfuxml.addon.jsf.data.jaxb.Tag;

import de.kisner.util.LoggerInit;

public class TestMetatag
{
	private static Logger logger = Logger.getLogger(TestMetatag.class);
	
	public TestMetatag()
	{
		
	}
	
	public void xmlConstruct()
	{	
		Metatag metatag = new Metatag();
		
		Tag tag = new Tag();
		tag.setName("Testame");
		
		metatag.setTag(tag);
		
		try
		{
			JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
//			m.setProperty("com.sun.xml.bind.namespacePrefixMapper",new JsfNsPrefixMapper());

			m.marshal(metatag, System.out);
		}
		catch (JAXBException e) {logger.debug(e);}
	}
		
	public void load()
	{
		Metatag metatag = (Metatag)JaxbUtil.loadJAXB("../jWAN/resources/jsf/tld/html/link.xml", Metatag.class);
		JaxbUtil.debug(metatag);
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing Metatag");
			
		TestMetatag test = new TestMetatag();
		test.xmlConstruct();
//		test.load();
	}
}