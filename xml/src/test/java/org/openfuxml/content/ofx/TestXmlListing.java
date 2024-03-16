package org.openfuxml.content.ofx;

import org.openfuxml.content.text.TestXmlRaw;
import org.openfuxml.model.xml.core.ofx.Listing;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlListing extends AbstractXmlOfxTest<Listing>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlListing(){super(Listing.class);}
	public static Listing create(boolean withChildren){return (new TestXmlListing()).build(withChildren);}
   
    public Listing build(boolean withChilds)
    {
        Listing xml = new Listing();
    	xml.setId("myId");
        xml.setCodeLang("myCodeLang");

    	if(withChilds)
    	{
    		xml.setTitle(TestXmlTitle.create(false));
            xml.setRaw(TestXmlRaw.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlListing test = new TestXmlListing();
		test.saveReferenceXml();
    }
}