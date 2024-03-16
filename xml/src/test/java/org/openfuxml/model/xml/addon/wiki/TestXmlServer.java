package org.openfuxml.model.xml.addon.wiki;

import org.openfuxml.test.OfxBootstrap;

public class TestXmlServer extends AbstractXmlWikiTest<Server>
{	
	public TestXmlServer(){super(Server.class);}
	public static Server create(boolean withChildren){return (new TestXmlServer()).build(withChildren);}
   
    public Server build(boolean withChilds)
    {
    	Server xml = new Server();
    	xml.setId("myId");
    	xml.setUrl("http://");
    	xml.setName("name");
    	xml.setDefault(true);
    	if(withChilds)
    	{
    		xml.getAuth().add(TestXmlAuth.create(false));
    	}
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlServer test = new TestXmlServer();
		test.saveReferenceXml();
    }
}