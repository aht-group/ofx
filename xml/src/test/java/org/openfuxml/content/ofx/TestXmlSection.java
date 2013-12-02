package org.openfuxml.content.ofx;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSection extends AbstractXmlOfxTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Section.class);
	}
    
    @Ignore @Test
    public void jaxbStructure() throws FileNotFoundException
    {
    	Section actual = create(true);
    	Section expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Section.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Section create(boolean withChilds)
    {
    	Section xml = new Section();
    	
    	logger.warn("Not fully implemented");
    	if(withChilds)
    	{
    		xml.getContent().add(TestXmlTitle.create(false));
    		xml.getContent().add(TestXmlTitle.create(false));
    		xml.getContent().add(TestXmlParagraph.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlSection.initFiles();	
		TestXmlSection test = new TestXmlSection();
		test.save();
    }
}