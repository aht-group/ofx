package org.openfuxml.content.ofx;

import org.openfuxml.model.xml.core.ofx.Include;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlInclude extends AbstractXmlOfxTest<Include>
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlInclude.class);
	
	public TestXmlInclude(){super(Include.class);}
	public static Include create(boolean withChildren){return (new TestXmlInclude()).build(withChildren);}
   
    public Include build(boolean withChildren)
    {
    	Include xml = new Include();
    	xml.setLang("myLang");
    	xml.setValue("myRawValue");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlInclude test = new TestXmlInclude();
		test.saveReferenceXml();
    }
}