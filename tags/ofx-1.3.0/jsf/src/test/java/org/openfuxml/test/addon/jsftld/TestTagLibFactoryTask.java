package org.openfuxml.test.addon.jsftld;

import java.io.File;

import junit.framework.Assert;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.addon.jsf.factory.TaglibFactoryTask;
import org.openfuxml.test.AbstractJsfTldTest;

public class TestTagLibFactoryTask extends AbstractJsfTldTest
{
	static Log logger = LogFactory.getLog(TestTagLibFactoryTask.class);
	
	private static final String rootTest = "src/main/tld";
	public static boolean useLogging;
	
	private static File fileTldConfig, fileTld, fileFacesConfig;
	private static File dirTagRoot;
	
	private TaglibFactoryTask tlFactory;
	
	@BeforeClass public static void initTest()
	{
		fileTldConfig = new File(rootTest,"tldConfig.xml");
		dirTagRoot = new File(rootTest);
		fileTld = new File("target","test.tld");
		fileFacesConfig = new File("target","faces-config.xml");
		useLogging = true;
	}
	
	@Before
	public  void init()
	{
		tlFactory = new TaglibFactoryTask();
		tlFactory.setUseLog4j(useLogging);
	}
    
    @Test
    public void testLocalParameter()
    {
    	Assert.assertNotNull(fileTldConfig);Assert.assertTrue(fileTldConfig.exists());Assert.assertTrue(fileTldConfig.isFile());
    	Assert.assertNotNull(dirTagRoot);Assert.assertTrue(dirTagRoot.exists());Assert.assertTrue(dirTagRoot.isDirectory());
    	Assert.assertNotNull(fileTld);Assert.assertTrue(fileTld.getParentFile().exists());Assert.assertTrue(fileTld.getParentFile().isDirectory());
    	Assert.assertNotNull(fileFacesConfig);Assert.assertTrue(fileFacesConfig.getParentFile().exists());Assert.assertTrue(fileFacesConfig.getParentFile().isDirectory());
    }
	
    @Test
    public void testParameter()
    {
    	setParameter();
    	tlFactory.checkParameter();
    }
    
    @Test
    public void execute()
    {
    	setParameter();
    	tlFactory.execute();
    }
    
    @Test
    public void testTldRootElement()
    {
    	setParameter();
    	tlFactory.execute();
    	Document doc = JDomUtil.load(fileTld);
    	Element root = doc.getRootElement();
    	
    	Namespace nsDefault = root.getNamespace();
    	Namespace nsXsi = root.getNamespace("xsi");

    	Attribute aSchemaLocation = root.getAttribute("schemaLocation", nsXsi);
    	Attribute aVersion = root.getAttribute("version");
    	
    	Assert.assertEquals(TaglibFactoryTask.ftNsDefault,nsDefault.getURI());
    	Assert.assertEquals(TaglibFactoryTask.ftNsXsi,nsXsi.getURI());
    	Assert.assertEquals(TaglibFactoryTask.ftAttSchemaLocation,aSchemaLocation.getValue());
    	Assert.assertEquals(TaglibFactoryTask.ftAttVersion,aVersion.getValue());

    }
    
    @Test(expected=BuildException.class)
    public void invalidTldConfig()
    {
    	setParameter();
    	tlFactory.setTldConfig("xxx");
    	tlFactory.checkParameter();
    }
    
    @Test(expected=BuildException.class)
    public void invalidTagRoot()
    {
    	setParameter();
    	tlFactory.setTagRoot("xxx");
    	tlFactory.checkParameter();
    }
    
    @Test(expected=BuildException.class)
    public void invalidTld()
    {
    	setParameter();
    	tlFactory.setTld("xx/X");
    	tlFactory.checkParameter();
    }
    
    @Test(expected=BuildException.class)
    public void invalidFacesConfig()
    {
    	setParameter();
    	tlFactory.setFacesConfig("xx/X");
    	tlFactory.checkParameter();
    }
    
    private void setParameter()
    {
    	tlFactory.setTldConfig(fileTldConfig.getAbsolutePath());
    	tlFactory.setTagRoot(dirTagRoot.getAbsolutePath());
    	tlFactory.setTld(fileTld.getAbsolutePath());
    	tlFactory.setFacesConfig(fileFacesConfig.getAbsolutePath());
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
		
		TestTagLibFactoryTask.initTest();
		TestTagLibFactoryTask.useLogging = true;
		TestTagLibFactoryTask test = new TestTagLibFactoryTask();
		test.init();
		test.testParameter();
		
//		test.execute();
		test.testTldRootElement();
    }
}