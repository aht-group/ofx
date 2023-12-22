package org.openfuxml.content.ofx;

import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDocument extends AbstractXmlOfxTest<Document>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlDocument(){super(Document.class);}
	public static Document create(boolean withChildren){return (new TestXmlDocument()).build(withChildren);}
   
    public Document build(boolean withChilds)
    {
    	Document xml = new Document();
    	
    	logger.warn("Not fully implemented");
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlDocument test = new TestXmlDocument();
		test.saveReferenceXml();
    }
}