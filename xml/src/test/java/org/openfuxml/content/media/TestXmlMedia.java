package org.openfuxml.content.media;

import org.openfuxml.model.xml.core.media.Media;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMedia extends AbstractXmlMediaTest<Media>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlMedia(){super(Media.class);}
	public static Media create(boolean withChildren){return (new TestXmlMedia()).build(withChildren);}
   
    public Media build(boolean withChilds)
    {
    	Media xml = new Media();
    	xml.setId("myId");
    	xml.setSrc("mySrc");
    	xml.setDst("myDst");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();	
		TestXmlMedia test = new TestXmlMedia();
		test.saveReferenceXml();
    }
}