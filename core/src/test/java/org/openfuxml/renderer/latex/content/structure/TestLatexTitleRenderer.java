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
import org.openfuxml.factory.xml.text.OfxTextFactory;
import org.openfuxml.renderer.latex.content.AbstractLatexContentTest;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexTitleRenderer extends AbstractLatexContentTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexTitleRenderer.class);

    private LatexPreamble preamble;
	private LatexTitleRenderer renderer;

    private Section section;
    private Title title;

	@Before
	public void init()
	{
        preamble = new LatexPreamble(cmm,dsm);
        renderer = new LatexTitleRenderer(cmm,dsm);
        section = new Section();
        section.setId("myId");
        title = XmlTitleFactory.build("test");
	}
	
    @Test
    public void withoutId() throws IOException, OfxAuthoringException
    {
        section.setId(null);
        renderer.render(title,section,1,preamble);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(2, content.size());
    }

    @Test
    public void withId() throws IOException, OfxAuthoringException
    {
        renderer.render(title,section,1,preamble);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(3, content.size());
    }
    
    @Test
    public void specialChars() throws IOException, OfxAuthoringException
    {
    	title = XmlTitleFactory.build("M & E");
        renderer.render(title,section,1,preamble);
        List<String> content = renderer.getContent();
        OfxContentDebugger.debug(content);
        Assert.assertEquals(3, content.size());
    }
    
    public void text()
    {
    	Title title = new Title();
    	title.getContent().add("Test");
    	title.getContent().add("Test2");
    	title.getContent().add(OfxTextFactory.build("Test3"));
    	
    	renderer.render(title,section,1,preamble);
    	List<String> content = renderer.getContent();
    	for(String s : content){System.out.println(s);}
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();

    	TestLatexTitleRenderer test = new TestLatexTitleRenderer();
        test.init();

//    	test.withoutId();
//        test.withId();
//        test.specialChars();
        test.text();
    }
   
}