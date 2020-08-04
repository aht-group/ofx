package org.openfuxml.test.provider;

import org.openfuxml.content.ofx.Highlight;
import org.openfuxml.renderer.latex.structure.TestLatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HighlightProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	public static Highlight simple()
	{
		Highlight highlight = new Highlight();
        highlight.getContent().add(ParagraphProvider.create(8));
        highlight.getContent().add(ParagraphProvider.create(4));
        highlight.getContent().add(ParagraphProvider.create(5));
        return highlight;
	}
}