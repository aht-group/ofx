package org.openfuxml.renderer.latex.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.text.OfxTextFactory;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexTitleRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.SectionProvider;
import org.openfuxml.test.provider.TitleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexTitleRenderer extends AbstractTestLatexRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexTitleRenderer.class);

	private static enum Key {withoutId,withId,specialChar,text}
	
    private LatexPreamble preamble;
	private LatexTitleRenderer renderer;

	@Before
	public void init()
	{
		super.initDir("structure/title");
        preamble = new LatexPreamble(cp);
        renderer = new LatexTitleRenderer(cp);
	}
	
    @Test
    public void withoutId() throws IOException, OfxAuthoringException
    {
    	initFile(Key.withoutId);
    	Section section = SectionProvider.build();
        section.setId(null);
        renderer.render(TitleProvider.create(),section,1,preamble);
    	renderTest(renderer);
    }

    @Test
    public void withId() throws IOException, OfxAuthoringException
    {
    	initFile(Key.withId);
        renderer.render(TitleProvider.create(), SectionProvider.build(),1,preamble);
        renderTest(renderer);
    }
    
    @Test
    public void specialChars() throws IOException, OfxAuthoringException
    {
    	initFile(Key.specialChar);
        renderer.render(XmlTitleFactory.build("M & E"), SectionProvider.build(),1,preamble);
        renderTest(renderer);
    }
    
    public void text() throws IOException
    {
    	initFile(Key.text);
    	Title title = new Title();
    	title.getContent().add("Test");
    	title.getContent().add("Test2");
    	title.getContent().add(OfxTextFactory.build("Test3"));
    	
    	renderer.render(title, SectionProvider.build(),1,preamble);
    	renderTest(renderer);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
    	TestLatexTitleRenderer test = new TestLatexTitleRenderer();
        test.setEnvironment(true);

        test.init();test.withoutId();
		test.init();test.withId();
		test.init();test.specialChars();
		test.init();test.text();
    }
}