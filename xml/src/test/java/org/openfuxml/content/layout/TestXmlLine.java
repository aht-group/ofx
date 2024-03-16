package org.openfuxml.content.layout;

import org.openfuxml.model.xml.core.layout.Line;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxBootstrap;
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
		OfxBootstrap.init();
		TestXmlLine test = new TestXmlLine();
		test.saveReferenceXml();
    }
}