package org.openfuxml.content.text;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.Raw;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlRaw extends AbstractXmlOfxTextTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlOfxTextTest.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix, Raw.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
        Raw actual = create();
        Raw expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Raw.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Raw create()
    {
        Raw xml = new Raw();
    	xml.setValue("myRawValue");
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlRaw.initFiles();
		TestXmlRaw test = new TestXmlRaw();
		test.save();
    }
}