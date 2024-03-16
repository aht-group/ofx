package org.openfuxml.content.list;

import org.openfuxml.model.xml.core.list.Type;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlType extends AbstractXmlListTest<Type>
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlType.class);
	
	public TestXmlType(){super(Type.class);}
	public static Type create(boolean withChildren){return (new TestXmlType()).build(withChildren);}
   
    public Type build(boolean withChildren)
    {
    	Type xml = new Type();
    	xml.setDescription(false);
    	xml.setOrdering("ordered");    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlType test = new TestXmlType();
		test.saveReferenceXml();
    }
}