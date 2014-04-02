package org.openfuxml.renderer.latex.content;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.content.OfxEmphasisFactory;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.renderer.latex.content.structure.TestLatexParagraphRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexEmphasisRenderer extends AbstractLatexContentTest
{	
final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	private static enum Key {bold}
	
	private LatexParagraphRenderer renderer;
	private String dir = "emphasis";
	
	@After public void close(){renderer=null;}
	
	public static Paragraph create()
	{
    	Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(10));
    	return p;
	}
	
    @Test
    public void bold() throws IOException, OfxAuthoringException
    {
    	OfxEmphasisFactory eF = new OfxEmphasisFactory(true);
    	
    	Paragraph p = new Paragraph();
    	p.getContent().add(eF.build(li.getWords(3)));
    	p.getContent().add(li.getWords(10));
    	
    	renderer = new LatexParagraphRenderer(true);
    	
    	f = new File(rootDir,dir+"/"+Key.bold+".txt");
    	renderer.render(p);
    	renderTest(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexEmphasisRenderer.initLoremIpsum();
    	TestLatexEmphasisRenderer test = new TestLatexEmphasisRenderer();
    	test.setSaveReference(true);
    	
 //   	test.withBlank();
    	test.bold();
    }
}