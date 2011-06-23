package org.openfuxml.test.xml.addon.jsftld;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.xml.AbstractOfxXmlTest;
import org.openfuxml.xml.addon.jsftld.Taglib;
import org.openfuxml.xml.ns.OfxNsPrefixMapper;

public class TestTaglib extends AbstractOfxXmlTest
{
	static Log logger = LogFactory.getLog(TestTaglib.class);
	
	private static final String rootDir = "src/test/resources/data/xml/jsftld";
	
	private static File fXml;
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"taglib.xml");
	}
    
    @Test
    public void testLang() throws FileNotFoundException
    {
    	Taglib test = createTaglib();
    	Taglib ref = (Taglib)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Taglib.class);
    	Assert.assertEquals(JaxbUtil.toString(test),JaxbUtil.toString(ref));
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Taglib lang = createTaglib();
    	JaxbUtil.debug2(this.getClass(),lang, nsPrefixMapper);
    	JaxbUtil.save(fXml, lang, nsPrefixMapper, true);
    }
   
    public static Taglib createTaglib()
    {
    	Taglib xml = new Taglib();
    	xml.setTlibVersion("1.1");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestTaglib.initFiles();	
		TestTaglib test = new TestTaglib();
		test.save();
    }
}