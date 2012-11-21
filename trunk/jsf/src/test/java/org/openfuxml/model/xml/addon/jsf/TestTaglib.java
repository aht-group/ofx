package org.openfuxml.model.xml.addon.jsf;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxJsfTestBootstrap;
import org.openfuxml.xml.addon.jsf.Attribute;

public class TestTaglib extends AbstractXmlJsfTest
{
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Attribute.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testLang() throws FileNotFoundException
    {
    	Attribute actual = create();
    	Attribute expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Attribute.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Attribute create()
    {
    	Attribute xml = new Attribute();
    	xml.setId(1);
    	xml.setCode("myCode");
    	xml.setRequired(true);
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		OfxJsfTestBootstrap.init();		
			
		TestTaglib.initFiles();	
		TestTaglib test = new TestTaglib();
		test.save();
    }
}