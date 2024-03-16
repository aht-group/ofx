package org.openfuxml.content.editorial;

import org.openfuxml.content.ofx.TestXmlComment;
import org.openfuxml.model.xml.core.editorial.Glossary;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGlossary extends AbstractXmlEditorialTest<Glossary>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
    
	public TestXmlGlossary(){super(Glossary.class);}
	public static Glossary create(boolean withChildren){return (new TestXmlGlossary()).build(withChildren);}
   
    public Glossary build(boolean withChildren)
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
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlGlossary test = new TestXmlGlossary();
		test.saveReferenceXml();
    }
}