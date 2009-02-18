package org.openfuxml.model.factory;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.openfuxml.client.control.OpenFuxmlClientControl;
import org.openfuxml.util.config.jaxb.Openfuxml;

public class AbstractJaxbFactory
{
	static Logger logger = Logger.getLogger(OpenFuxmlClientControl.class);
	
	public void writeJaxb(OutputStream os, Object o)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(o.getClass());
			Marshaller m = context.createMarshaller(); 
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE ); 
			m.marshal( o , os);
			os.close();
		}
		catch (JAXBException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}
	}
}
