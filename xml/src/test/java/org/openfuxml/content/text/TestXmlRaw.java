package org.openfuxml.content.text;

import org.openfuxml.content.ofx.Raw;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRaw extends AbstractXmlOfxTextTest<Raw>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlOfxTextTest.class);
	
	public TestXmlRaw(){super(Raw.class);}
	public static Raw create(boolean withChildren){return (new TestXmlRaw()).build(withChildren);}
   
    public Raw build(boolean withChildren)
    {
        Raw xml = new Raw();
    	xml.setValue("myRawValue");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlRaw test = new TestXmlRaw();
		test.saveReferenceXml();
    }
}