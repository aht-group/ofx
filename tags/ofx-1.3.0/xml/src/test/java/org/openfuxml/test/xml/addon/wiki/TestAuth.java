package org.openfuxml.test.xml.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.Auth;
import org.openfuxml.test.xml.AbstractOfxXmlTest;

public class TestAuth extends AbstractOfxXmlTest
{
	static Log logger = LogFactory.getLog(TestAuth.class);
	
	private static final String rootDir = "src/test/resources/data/xml/wiki";
	
	private static File fXml;
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"auth.xml");
	}
    
    @Test
    public void testAuth() throws FileNotFoundException
    {
    	Auth test = createAuth();
    	Auth ref = (Auth)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Auth.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
 
    public void save()
    {
    	Auth xml = createAuth();
    	JaxbUtil.debug2(this.getClass(),xml, nsPrefixMapper);
    	JaxbUtil.save(fXml, xml, nsPrefixMapper, true);
    }
   
    public static Auth createAuth()
    {
    	Auth xml = new Auth();
    	xml.setType("wiki");
    	xml.setName("name");
    	xml.setPassword("xyz");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestAuth.initFiles();	
		TestAuth test = new TestAuth();
		test.save();
    }
}