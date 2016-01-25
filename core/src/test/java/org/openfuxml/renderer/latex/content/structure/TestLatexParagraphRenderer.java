package org.openfuxml.renderer.latex.content.structure;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlFontFactory;
import org.openfuxml.renderer.latex.content.AbstractLatexContentTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.ParagraphProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestLatexParagraphRenderer extends AbstractLatexContentTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	private static enum Key {withBlank,withoutBlank,marginalia,fontRelative}
	
	private LatexParagraphRenderer renderer;
	private String dir = "paragraph";
	
	@Before public void init(){renderer = new LatexParagraphRenderer(cmm,dsm,false);}
	@After public void close(){renderer=null;}
		
    @Test
    public void withBlank() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.withBlank+".txt");
    	renderer.render(ParagraphProvider.create());
    	renderTest(renderer,f);
    }
    
    @Test @Ignore
    public void withoutBlank() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.withoutBlank+".txt");
    	renderer.render(ParagraphProvider.create());
    	renderTest(renderer,f);
    }
        
    @Test
    public void fontRelative() throws IOException, OfxAuthoringException
    {
    	Paragraph p = ParagraphProvider.create();
    	p.getContent().add(XmlFontFactory.relative(-2));
    	JaxbUtil.info(p);
    	f = new File(rootDir,dir+"/"+Key.fontRelative+".txt");
    	renderer.render(p);
    	renderTest(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexParagraphRenderer.initLoremIpsum();
    	TestLatexParagraphRenderer test = new TestLatexParagraphRenderer();
    	test.init();
    	test.setEnvironment(true);
    	
//    	test.withBlank();
//  	test.withoutBlank();
//    	test.marginalia();
    	test.fontRelative();
    }
}