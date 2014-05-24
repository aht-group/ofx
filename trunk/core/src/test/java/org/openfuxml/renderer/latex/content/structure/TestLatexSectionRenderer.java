package org.openfuxml.renderer.latex.content.structure;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.latex.content.AbstractLatexContentTest;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexSectionRenderer extends AbstractLatexContentTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexSectionRenderer.class);
	
	private static enum Key {withBlank,withoutBlank}

    private LatexPreamble preamble;
	private LatexSectionRenderer renderer;

    private Section section;

	@Before
	public void init()
	{
        preamble = new LatexPreamble();
        renderer = new LatexSectionRenderer(new NoOpCrossMediaManager(),1,preamble);
        section = new Section();
        section.setId("myId");
        section.getContent().add(XmlTitleFactory.build("test"));
	}
    
	@Test public void section() throws OfxAuthoringException
    {
//    	section.setTransparent(true);
    	renderer.render(section);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(5, content.size());
    }
	
	@Test public void sectionWithHeaderButTransparent() throws OfxAuthoringException
    {
    	section.setContainer(true);
    	renderer.render(section);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(2, content.size());
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();

    	TestLatexSectionRenderer test = new TestLatexSectionRenderer();
        test.init();

//     	test.section();
        test.sectionWithHeaderButTransparent();
    }
   
}