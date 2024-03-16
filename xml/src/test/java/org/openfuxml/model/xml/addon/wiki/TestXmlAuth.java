package org.openfuxml.model.xml.addon.wiki;

import org.openfuxml.test.OfxBootstrap;

public class TestXmlAuth extends AbstractXmlWikiTest<Auth>
{	
	public TestXmlAuth(){super(Auth.class);}
	public static Auth create(boolean withChildren){return (new TestXmlAuth()).build(withChildren);}
   
    public Auth build(boolean withChildren)
    {
    	Auth xml = new Auth();
    	xml.setType("wiki");
    	xml.setName("name");
    	xml.setPassword("xyz");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();	
		TestXmlAuth test = new TestXmlAuth();
		test.saveReferenceXml();
    }
}