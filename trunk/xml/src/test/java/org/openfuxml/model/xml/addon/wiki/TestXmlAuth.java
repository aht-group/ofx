package org.openfuxml.model.xml.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.Auth;
import org.openfuxml.test.OfxXmlTstBootstrap;

public class TestXmlAuth extends AbstractXmlWikiTest
{	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"auth.xml");
	}
    
    @Test
    public void testAuth() throws FileNotFoundException
    {
    	Auth test = create();
    	Auth ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Auth.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
   
    public static Auth create()
    {
    	Auth xml = new Auth();
    	xml.setType("wiki");
    	xml.setName("name");
    	xml.setPassword("xyz");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlAuth.initFiles();	
		TestXmlAuth test = new TestXmlAuth();
		test.save();
    }
}