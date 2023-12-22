package org.openfuxml.content.table;

import org.openfuxml.content.layout.TestXmlAlignment;
import org.openfuxml.content.layout.TestXmlFloat;
import org.openfuxml.content.layout.TestXmlWidth;
import org.openfuxml.model.xml.core.table.Specification;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSpecification extends AbstractXmlTableTest<Specification>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlSpecification(){super(Specification.class);}
	public static Specification create(boolean withChildren){return (new TestXmlSpecification()).build(withChildren);}
   
    public Specification build(boolean withChilds)
    {
    	Specification xml = new Specification();
    	xml.setLong(false);
    	
    	if(withChilds)
    	{
    		xml.setAlignment(TestXmlAlignment.create(false));
    		xml.setWidth(TestXmlWidth.create(false));
    		xml.setColumns(TestXmlColumns.create(false));
    		xml.setFloat(TestXmlFloat.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlSpecification test = new TestXmlSpecification();
		test.saveReferenceXml();
    }
}