package org.openfuxml.renderer.latex.structure;

import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexMarginaliaRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.MarginaliaProvider;
import org.openfuxml.test.provider.ParagraphProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestLatexMarginaliaRenderer extends AbstractTestLatexRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexMarginaliaRenderer.class);

	private LatexMarginaliaRenderer renderer;
    
	@Before public void init() {renderer = new LatexMarginaliaRenderer(cmm,dsm);}
	@After public void close(){renderer=null;}
	
	@Test public void marginalia() throws OfxAuthoringException, OfxConfigurationException
    {
		Marginalia marginalia = MarginaliaProvider.withParagraph();
		JaxbUtil.trace(marginalia);
    	renderer.render(marginalia);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(3, content.size());
        testLatex(content);
    }
	
    @Test public void paragraph() throws IOException, OfxAuthoringException
    {
    	Paragraph p = ParagraphProvider.create(5);
    	p.getContent().add(0, MarginaliaProvider.withParagraph());
    	
    	JaxbUtil.info(p);
    	
 //   	f = new File(rootDir,dir+"/"+Key.marginalia+".txt");
    	LatexParagraphRenderer rendererParagraph = new LatexParagraphRenderer(cmm,dsm,false); ;
    	rendererParagraph.render(p);
    	
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
 //   	renderTest(renderer,f);
    }
	    
    public static void main(String[] args) throws Exception
    {
    	Configuration config = OfxCoreTestBootstrap.init();

    	TestLatexMarginaliaRenderer test = new TestLatexMarginaliaRenderer();
    	test.initLatexTestEnvironment(config);
        
//    	test.init();test.marginalia();
    	test.init();test.paragraph();
    }
}