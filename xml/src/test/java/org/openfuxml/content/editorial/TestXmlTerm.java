package org.openfuxml.content.editorial;

import org.openfuxml.content.ofx.TestXmlParagraph;
import org.openfuxml.content.text.TestXmlText;
import org.openfuxml.model.xml.core.editorial.Term;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTerm extends AbstractXmlEditorialTest<Term>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	   
	public TestXmlTerm(){super(Term.class);}
	public static Term create(boolean withChildren){return (new TestXmlTerm()).build(withChildren);}
   
    public Term build(boolean withChildren)
    {
    	Term xml = new Term();
    	xml.setCode("myCode");
    	
    	if(withChildren)
    	{
    		xml.getText().add(TestXmlText.create(false));xml.getText().add(TestXmlText.create(false));
    		xml.getParagraph().add(TestXmlParagraph.create(false));xml.getParagraph().add(TestXmlParagraph.create(false));
    	}
    	
    	return xml;
    }
    	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlTerm test = new TestXmlTerm();
		test.saveReferenceXml();
    }
}