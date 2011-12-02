package org.openfuxml.model.xml.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.Servers;
import org.openfuxml.test.OfxXmlTstBootstrap;

public class TestXmlServers extends AbstractXmlWikiTest
{	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"servers.xml");
	}
    
    @Test
    public void testServers() throws FileNotFoundException
    {
    	Servers test = create();
    	Servers ref = (Servers)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Servers.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
    
    private static Servers create(){return create(true);}
    public static Servers create(boolean withChilds)
    {
    	Servers xml = new Servers();
    	
    	if(withChilds)
    	{
    		xml.getServer().add(TestXmlServer.create(false));
    		xml.getServer().add(TestXmlServer.create(false));
    	}
    	return xml;
    }

    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlServers.initFiles();	
		TestXmlServers test = new TestXmlServers();
		test.save();
    }
}