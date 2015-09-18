package org.openfuxml.content.editorial;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.TestXmlComment;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGlossary extends AbstractXmlEditorialTest<Glossary>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix, Glossary.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Glossary actual = create(true);
    	Glossary expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Glossary.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Glossary create(boolean withChildren)
    {
    	Glossary xml = new Glossary();
    	xml.setCode("myCode");
    	
    	if(withChildren)
    	{
    		xml.setComment(TestXmlComment.create(false));
    		xml.getTerm().add(TestXmlTerm.create(false));xml.getTerm().add(TestXmlTerm.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlGlossary.initFiles();	
		TestXmlGlossary test = new TestXmlGlossary();
		test.save();
    }
}