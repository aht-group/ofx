package org.openfuxml.renderer.latex.content.editorial;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.doc.provider.text.ParagraphProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;

public class TestLatexIndexRenderer extends AbstractTestLatexRenderer
{
	private enum Key {simple}
	LatexIndexRenderer renderer;
	
	@Before public void init()
	{
		super.initDir("editorial/index");
		renderer = new LatexIndexRenderer(cp);
	}
	@After public void close() {renderer=null;}

	@Test
	public void index() throws OfxAuthoringException, IOException
	{
		initFile(Key.simple);
		LatexParagraphRenderer renderer = new LatexParagraphRenderer(cp,true);
		renderer.render(ParagraphProvider.paragraphWithIndex());
		renderTest(renderer);
	}

	public static void main(String[] args) throws Exception
	{
		OfxCoreBootstrap.init();
		TestLatexIndexRenderer.initLoremIpsum();
		TestLatexIndexRenderer test = new TestLatexIndexRenderer();
		test.setEnvironment(true);
		
		test.init();test.index();
	}
}
