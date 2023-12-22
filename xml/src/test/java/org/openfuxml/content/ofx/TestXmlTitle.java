package org.openfuxml.content.ofx;

import org.openfuxml.model.xml.core.ofx.Title;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTitle extends AbstractXmlOfxTest<Title>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlTitle(){super(Title.class);}
	public static Title create(boolean withChildren){return (new TestXmlTitle()).build(withChildren);}
   
    public Title build(boolean withChilds)
    {
    	Title xml = new Title();
    	xml.setNumbering(true);
    	xml.getContent().add("myTitle");
    	xml.setLang("myLang");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlTitle test = new TestXmlTitle();
		test.saveReferenceXml();
    }
}