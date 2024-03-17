package org.openfuxml.renderer.latex.structure;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.exlp.util.jx.JaxbUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.model.xml.core.ofx.Highlight;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexSectionRenderer extends AbstractTestLatexRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexSectionRenderer.class);

    private LatexPreamble preamble;
	private LatexSectionRenderer renderer;

    private Section section;
    
	@Before
	public void init()
	{
        preamble = new LatexPreamble(cp);
        renderer = new LatexSectionRenderer(cp,1,preamble);
        section = new Section();
        section.setId("myId");
        section.getContent().add(XmlTitleFactory.build("testTitle"));
        
        Paragraph p = XmlParagraphFactory.build();
        p.getContent().add("testParagraph");
        section.getContent().add(p);
	}
    
	@Test public void section() throws OfxAuthoringException, OfxConfigurationException
    {
		JaxbUtil.trace(section);
    	renderer.render(section);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(7, content.size());
        testLatex(content);
    }
	
	@Test public void sectionWithHeaderButContainer() throws OfxAuthoringException, OfxConfigurationException
    {
    	section.setContainer(true);
    	JaxbUtil.trace(section);
    	renderer.render(section);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(4, content.size());
    }
	
	@Test public void sectionInclude() throws OfxAuthoringException, OfxConfigurationException
    {
    	section.setContainer(true);
    	section.setInclude("test");
    	section.getContent().clear();
    	JaxbUtil.info(section);
    	renderer.render(section);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(4, content.size());
    }
	
	@Test public void sectionIncludeWithContent() throws OfxAuthoringException, OfxConfigurationException
    {
    	section.setContainer(true);
    	section.setInclude("test");
    	JaxbUtil.trace(section);
    	renderer.render(section);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(5, content.size());
    }
	
	@Test public void sectionWithHighlight() throws OfxAuthoringException, OfxConfigurationException
    {
		section.getContent().clear();
		Highlight highlight = new Highlight();
		highlight.getContent().add(XmlParagraphFactory.text("test"));
		section.getContent().add(highlight);
		
		JaxbUtil.trace(section);
    	renderer.render(section);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
//        Assert.assertEquals(7, content.size());
 //       testLatex(content);
    }
    
    public static void main(String[] args) throws Exception
    {
    	Configuration config = OfxBootstrap.init();

    	TestLatexSectionRenderer test = new TestLatexSectionRenderer();
    	test.initLatexTestEnvironment(config);
        test.init();

//       	test.section();
//     test.sectionWithHeaderButContainer();
//       test.sectionInclude();
//        test.sectionIncludeWithContent();
       	test.sectionWithHighlight();
    }
   
}