package org.openfuxml.content.list;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.openfuxml.xml.content.list.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlType extends AbstractXmlListTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlType.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"type.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Type actual = create();
    	Type expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Type.class);
    	assertJaxbEquals(expected, actual);
    }
   
    private static Type create(){return create(true);}
    public static Type create(boolean withChilds)
    {
    	Type xml = new Type();
    	xml.setDescription(false);
    	xml.setOrdering("ordered");    	
    	return xml;
    }
    
    public void save() {save(create(),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlType.initFiles();	
		TestXmlType test = new TestXmlType();
		test.save();
    }
}