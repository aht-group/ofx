package org.openfuxml.renderer.latex.structure;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Highlight;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexHighlightRenderer;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestLatexHighlightRenderer extends AbstractTestLatexRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexHighlightRenderer.class);

	private LatexHighlightRenderer renderer;

    private Highlight highlight;
    
	@Before
	public void init()
	{
		highlight = new Highlight();
		renderer = new LatexHighlightRenderer(cmm,dsm);
		
        highlight.getContent().add(XmlParagraphFactory.text("paragraph1"));
        highlight.getContent().add(XmlParagraphFactory.text("paragraph2"));
	}
    
	@Test public void highlight() throws OfxAuthoringException, OfxConfigurationException
    {
		JaxbUtil.trace(highlight);
    	renderer.render(highlight);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(7, content.size());
        testLatex(content);
    }
	    
    public static void main(String[] args) throws Exception
    {
    	Configuration config = OfxCoreTestBootstrap.init();

    	TestLatexHighlightRenderer test = new TestLatexHighlightRenderer();
    	test.initLatexTestEnvironment(config);
        test.init();
       	test.highlight();
    }
}