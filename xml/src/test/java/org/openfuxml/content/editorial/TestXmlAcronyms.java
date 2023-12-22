package org.openfuxml.content.editorial;

import org.openfuxml.content.ofx.TestXmlComment;
import org.openfuxml.model.xml.core.editorial.Acronyms;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAcronyms extends AbstractXmlEditorialTest<Acronyms>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlAcronyms(){super(Acronyms.class);}
	public static Acronyms create(boolean withChildren){return (new TestXmlAcronyms()).build(withChildren);}
	    
    public Acronyms build(boolean withChildren)
    {
    	Acronyms xml = new Acronyms();
    	
    	if(withChildren)
    	{
    		xml.setComment(TestXmlComment.create(false));
    		xml.getTerm().add(TestXmlTerm.create(false));xml.getTerm().add(TestXmlTerm.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();	
		TestXmlAcronyms test = new TestXmlAcronyms();
		test.saveReferenceXml();
    }
}