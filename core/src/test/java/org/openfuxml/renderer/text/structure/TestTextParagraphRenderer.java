package org.openfuxml.renderer.text.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTextParagraphRenderer extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestTextParagraphRenderer.class);
	
	private Paragraph p;
	private TextParagraphRenderer renderer;
    
	@Before
	public void init()
	{
		p = XmlParagraphFactory.build();
    	p.getContent().add("Test");
    	
    	renderer = new TextParagraphRenderer(cmm,dsm); 
	}
	
    @Test
    public void test() throws IOException, OfxAuthoringException
    {
    	renderer.render(p);
    	this.debug(renderer);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestTextParagraphRenderer.initLoremIpsum();
    	TestTextParagraphRenderer cli = new TestTextParagraphRenderer();
    	cli.init();
    	
    	cli.test();
    }
}