package org.openfuxml.content.editorial;

import org.openfuxml.model.xml.core.editorial.Index;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlIndex extends AbstractXmlEditorialTest<Index>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
    
	public TestXmlIndex(){super(Index.class);}
	public static Index create(boolean withChildren){return (new TestXmlIndex()).build(withChildren);}

    public Index build(boolean withChildren)
    {
    	Index xml = new Index();
    	xml.setCode("myCode");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlIndex test = new TestXmlIndex();
		test.saveReferenceXml();
    }
}