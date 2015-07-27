package org.openfuxml.content.ofx;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlInclude extends AbstractXmlOfxTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlInclude.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix, Include.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Include actual = create();
    	Include expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Include.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Include create()
    {
    	Include xml = new Include();
    	xml.setLang("myLang");
    	xml.setValue("myRawValue");
    	return xml;
    }
    
    public void save() {save(create(),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlInclude.initFiles();
		TestXmlInclude test = new TestXmlInclude();
		test.save();
    }
}