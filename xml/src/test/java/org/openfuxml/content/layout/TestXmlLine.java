package org.openfuxml.content.layout;

import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLine extends AbstractXmlLayoutTest<Line>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlLine(){super(Line.class);}
	public static Line create(boolean withChildren){return (new TestXmlLine()).build(withChildren);}
   
    public Line build(boolean withChilds)
    {
    	Line xml = new Line();
    	xml.setOrientation("bottom");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlLine test = new TestXmlLine();
		test.saveReferenceXml();
    }
}