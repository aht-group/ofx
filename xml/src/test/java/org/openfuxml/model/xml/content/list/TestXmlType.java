package org.openfuxml.model.xml.content.list;

import org.openfuxml.model.xml.core.list.Type;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlType extends AbstractXmlListTest<Type>
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlType.class);
	
	public static TestXmlType instance() {return new TestXmlType();}
	private TestXmlType() {super(Type.class);}
   
    @Override public Type build(boolean wChildren)
    {
    	Type xml = new Type();
    	xml.setDescription(false);
    	xml.setOrdering("ordered");    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlType.instance().saveReferenceXml();
    }
}