package org.openfuxml.content.layout;

import org.openfuxml.model.xml.core.layout.Font;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFont extends AbstractXmlLayoutTest<Font>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlFont(){super(Font.class);}
	public static Font create(boolean withChildren){return (new TestXmlFont()).build(withChildren);}
   
    public Font build(boolean withChilds)
    {
    	Font xml = new Font();
    	xml.setRelativeSize(1);
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlFont test = new TestXmlFont();
		test.saveReferenceXml();
    }
}