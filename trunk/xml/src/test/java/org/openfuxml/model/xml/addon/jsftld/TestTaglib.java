package org.openfuxml.model.xml.addon.jsftld;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.openfuxml.xml.addon.jsf.tld.FaceletTaglib;

public class TestTaglib extends AbstractXmlJsfTldTest
{
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"taglib.xml");
	}
    
    @Test
    public void testLang() throws FileNotFoundException
    {
    	FaceletTaglib test = create();
    	FaceletTaglib ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), FaceletTaglib.class);
    	Assert.assertEquals(JaxbUtil.toString(test),JaxbUtil.toString(ref));
    }
   
    public static FaceletTaglib create()
    {
    	FaceletTaglib xml = new FaceletTaglib();
    	xml.setTlibVersion("1.1");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();		
			
		TestTaglib.initFiles();	
		TestTaglib test = new TestTaglib();
		test.save();
    }
}