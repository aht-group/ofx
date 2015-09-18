package org.openfuxml.content.editorial;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.TestXmlParagraph;
import org.openfuxml.content.text.TestXmlText;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTerm extends AbstractXmlEditorialTest<Term>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix, Term.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Term actual = create(true);
    	Term expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Term.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Term create(boolean withChildren)
    {
    	Term xml = new Term();
    	xml.setCode("myCode");
    	
    	if(withChildren)
    	{
    		xml.getText().add(TestXmlText.create());xml.getText().add(TestXmlText.create());
    		xml.getParagraph().add(TestXmlParagraph.create(false));xml.getParagraph().add(TestXmlParagraph.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlTerm.initFiles();	
		TestXmlTerm test = new TestXmlTerm();
		test.save();
    }
}