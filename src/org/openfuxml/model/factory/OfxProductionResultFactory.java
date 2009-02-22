package org.openfuxml.model.factory;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.openfuxml.model.ejb.OfxProductionResult;
import org.openfuxml.model.jaxb.Productionresult;

import de.kisner.util.LoggerInit;

public class OfxProductionResultFactory extends AbstractJaxbFactory
{
	public OfxProductionResultFactory()
	{
		
	}
	
	public OfxProductionResult get(File fResult)
	{
		OfxProductionResult ofxR = new OfxProductionResult();
		ofxR.setProductionresult(getProductionResult(fResult));
		return ofxR;
	}
	
	private Productionresult getProductionResult(File fResult)
	{
		Productionresult result=null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Productionresult.class);
			Unmarshaller u = jc.createUnmarshaller();
			result = (Productionresult)u.unmarshal(fResult);
		}
		catch (JAXBException e) {logger.error(e);}
		return result;
	}
	
	public static void main(String args[]) 
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		OfxProductionResultFactory orf = new OfxProductionResultFactory();
		File f = new File("/Users/thorsten/Documents/workspace/3.4.1/openFuXML/dist/output/fuxml/helloworld/latexpdf/HelloWorld/result.xml");
		orf.get(f);
	}
}
