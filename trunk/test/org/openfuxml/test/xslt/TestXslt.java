package org.openfuxml.test.xslt;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import de.kisner.util.LoggerInit;

public class TestXslt
{
	private static Logger logger = Logger.getLogger(TestXslt.class);
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing XSD Taglib");
			
		Source xmlSource = new StreamSource("resources/data/xml/xslt/en.xml");
        Source xsltSource = new StreamSource("resources/data/xml/xslt/en2de.xslt");

        TransformerFactory transFact = TransformerFactory.newInstance();
        Transformer trans = transFact.newTransformer(xsltSource);

        trans.transform(xmlSource, new StreamResult(System.out));
	}
}
