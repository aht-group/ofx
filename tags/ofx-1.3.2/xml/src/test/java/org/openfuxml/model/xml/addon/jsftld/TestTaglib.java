package org.openfuxml.model.xml.addon.jsftld;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

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
    	FaceletTaglib actual = create();
    	FaceletTaglib expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), FaceletTaglib.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static FaceletTaglib create()
    {
    	FaceletTaglib xml = new FaceletTaglib();
    	xml.setTlibVersion("1.1");
    	return xml;
    }
    
    public void save() {save(create(),fXml,true);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();		
			
		TestTaglib.initFiles();	
		TestTaglib test = new TestTaglib();
		test.save();
    }
}