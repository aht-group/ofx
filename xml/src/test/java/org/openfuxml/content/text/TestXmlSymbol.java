package org.openfuxml.content.text;

import org.openfuxml.model.xml.core.text.Symbol;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSymbol extends AbstractXmlOfxTextTest<Symbol>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlOfxTextTest.class);
	
	public TestXmlSymbol(){super(Symbol.class);}
	public static Symbol create(boolean withChildren){return (new TestXmlSymbol()).build(withChildren);}
   
    public Symbol build(boolean withChildren)
    {
    	Symbol xml = new Symbol();
    	xml.setMath(false);
    	xml.setCode("myCode");
    	xml.setValue("myValue");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlSymbol test = new TestXmlSymbol();
		test.saveReferenceXml();
    }
}