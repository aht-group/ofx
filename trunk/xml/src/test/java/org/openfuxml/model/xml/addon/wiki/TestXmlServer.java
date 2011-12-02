package org.openfuxml.model.xml.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.Server;
import org.openfuxml.test.OfxXmlTstBootstrap;

public class TestXmlServer extends AbstractXmlWikiTest
{	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"server.xml");
	}
    
    @Test
    public void testServer() throws FileNotFoundException
    {
    	Server test = create(true);
    	Server ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Server.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
   
    private static Server create() {return create(true);}
    public static Server create(boolean withChilds)
    {
    	Server xml = new Server();
    	xml.setId("myId");
    	xml.setUrl("http://");
    	xml.setName("name");
    	xml.setDefault(true);
    	if(withChilds)
    	{
    		xml.getAuth().add(TestXmlAuth.create());
    	}
    	return xml;
    }
	
    public void save() {save(create(),fXml);}
    
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlServer.initFiles();	
		TestXmlServer test = new TestXmlServer();
		test.save();
    }
}