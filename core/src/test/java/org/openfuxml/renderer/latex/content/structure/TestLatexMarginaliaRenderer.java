package org.openfuxml.renderer.latex.content.structure;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.renderer.latex.content.AbstractLatexContentTest;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestLatexMarginaliaRenderer extends AbstractLatexContentTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexMarginaliaRenderer.class);

	private LatexMarginaliaRenderer renderer;

    private Marginalia marginalia;
    
	@Before
	public void init()
	{
		marginalia = new Marginalia();
		renderer = new LatexMarginaliaRenderer(cmm,dsm);
		
		marginalia.getContent().add(XmlParagraphFactory.text("paragraph1"));
	}
    
	@Test public void marginalia() throws OfxAuthoringException, OfxConfigurationException
    {
		JaxbUtil.trace(marginalia);
    	renderer.render(marginalia);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(3, content.size());
        testLatex(content);
    }
	    
    public static void main(String[] args) throws Exception
    {
    	Configuration config = OfxCoreTestBootstrap.init();

    	TestLatexMarginaliaRenderer test = new TestLatexMarginaliaRenderer();
    	test.initLatexTestEnvironment(config);
        test.init();
       	test.marginalia();
    }
}