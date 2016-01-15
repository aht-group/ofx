package org.openfuxml.renderer.latex.content.structure;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlFontFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.renderer.latex.content.AbstractLatexContentTest;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxCoreTestBootstrap;
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
	
	public static Paragraph create(){return create(10);}
	public static Paragraph create(int words)
	{
    	Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(words));
    	return p;
	}
	
    @Test
    public void withBlank() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.withBlank+".txt");
    	renderer.render(create());
    	renderTest(renderer,f);
    }
    
    @Test @Ignore
    public void withoutBlank() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.withoutBlank+".txt");
    	renderer.render(create());
    	renderTest(renderer,f);
    }
    
    @Test
    public void marginalia() throws IOException, OfxAuthoringException
    {
    	Marginalia m = new Marginalia();
    	m.getContent().add(XmlParagraphFactory.text("marg"));
    	
    	Paragraph p = new Paragraph();
    	p.getContent().add(m);
    	p.getContent().add(li.getWords(5));
    	
    	JaxbUtil.info(p);
    	
    	f = new File(rootDir,dir+"/"+Key.marginalia+".txt");
    	renderer.render(p);
    	
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
 //   	renderTest(renderer,f);
    }
    
    @Test
    public void fontRelative() throws IOException, OfxAuthoringException
    {
    	Paragraph p = create();
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
    	test.setSaveReference(true);
    	
//    	test.withBlank();
//  	test.withoutBlank();
//    	test.marginalia();
    	test.fontRelative();
    }
}