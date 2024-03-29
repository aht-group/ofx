package org.openfuxml.model.xml.addon.wiki;

import org.openfuxml.test.OfxBootstrap;

public class TestXmlServers extends AbstractXmlWikiTest<Servers>
{	
	public TestXmlServers(){super(Servers.class);}
	public static Servers create(boolean withChildren){return (new TestXmlServers()).build(withChildren);}
    
    public Servers build(boolean withChilds)
    {
    	Servers xml = new Servers();
    	
    	if(withChilds)
    	{
    		xml.getServer().add(TestXmlServer.create(false));
    		xml.getServer().add(TestXmlServer.create(false));
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlServers test = new TestXmlServers();
		test.saveReferenceXml();
    }
}