package org.openfuxml.content.media;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.TestXmlComment;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlImage extends AbstractXmlMediaTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Image.class);
	}
    
    @Test
    public void jaxbStructure() throws FileNotFoundException
    {
    	Image actual = create(true);
    	Image expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Image.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Image create(boolean withChilds)
    {
    	Image xml = new Image();
    	xml.setId("myId");
    	
    	if(withChilds)
    	{
    		xml.setComment(TestXmlComment.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlImage.initFiles();	
		TestXmlImage test = new TestXmlImage();
		test.save();
    }
}