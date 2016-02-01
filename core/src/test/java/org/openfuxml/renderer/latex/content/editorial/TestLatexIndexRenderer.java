package org.openfuxml.renderer.latex.content.editorial;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.editorial.XmlIndexFactory;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;

import java.io.IOException;

public class TestLatexIndexRenderer extends AbstractTestLatexRenderer
{
	private enum Key {index}
	LatexIndexRenderer renderer;
	@Before
	public void init()
	{
		super.initDir("content/paragraph");
		renderer = new LatexIndexRenderer(cmm,dsm);
	}
	@After
	public void close()
	{
		renderer=null;
	}

	private static Paragraph paragraph()
	{
		Paragraph p = new Paragraph();
		p.getContent().add(li.getWords(1)+" ");
		p.getContent().add(XmlIndexFactory.build("testIndex"));
		p.getContent().add(" "+li.getWords(3));
		return p;
	}

	@Test
	public void index() throws OfxAuthoringException, IOException
	{
		initFile(Key.index);
		LatexParagraphRenderer renderer = new LatexParagraphRenderer(cmm,dsm,true);
		renderer.render(paragraph());
		renderTest(renderer);
	}

	public static void main(String[] args) throws Exception
	{
		OfxCoreTestBootstrap.init();

		TestLatexIndexRenderer.initLoremIpsum();
		TestLatexIndexRenderer test = new TestLatexIndexRenderer();
		test.init();
		test.index();
	}
}
