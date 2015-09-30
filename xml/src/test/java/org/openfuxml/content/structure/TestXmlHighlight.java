package org.openfuxml.content.structure;

import org.openfuxml.content.ofx.Highlight;
import org.openfuxml.content.ofx.TestXmlParagraph;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlHighlight extends AbstractXmlStructureTest<Highlight>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlHighlight(){super(Highlight.class);}
	public static Highlight create(boolean withChildren){return (new TestXmlHighlight()).build(withChildren);}
   
    public Highlight build(boolean withChilds)
    {
    	Highlight xml = new Highlight();
    	
    	if(withChilds)
    	{
    		xml.getContent().add(TestXmlParagraph.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlHighlight test = new TestXmlHighlight();
		test.saveReferenceXml();
    }
}