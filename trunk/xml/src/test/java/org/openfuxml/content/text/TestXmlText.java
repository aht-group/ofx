package org.openfuxml.content.text;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlText extends AbstractXmlOfxTextTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlOfxTextTest.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix, Text.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Text actual = create();
    	Text expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Text.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Text create()
    {
    	Text xml = new Text();
    	xml.setLang("myLang");
    	xml.setValue("myRawValue");
    	return xml;
    }
    
    public void save() {save(create(),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlText.initFiles();
		TestXmlText test = new TestXmlText();
		test.save();
    }
}