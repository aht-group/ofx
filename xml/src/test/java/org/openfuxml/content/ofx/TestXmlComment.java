package org.openfuxml.content.ofx;

import org.openfuxml.content.text.TestXmlRaw;
import org.openfuxml.model.xml.core.ofx.Comment;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlComment extends AbstractXmlOfxTest<Comment>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlComment(){super(Comment.class);}
	public static Comment create(boolean withChildren){return (new TestXmlComment()).build(withChildren);}
   
    public Comment build(boolean withChilds)
    {
    	Comment xml = new Comment();
    	
    	if(withChilds)
    	{
    		xml.getRaw().add(TestXmlRaw.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlComment test = new TestXmlComment();
		test.saveReferenceXml();
    }
}