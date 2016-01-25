package org.openfuxml.test.provider;

import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.renderer.latex.content.structure.TestLatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarginaliaProvider extends AbstractElementProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	public static Marginalia withParagraph()
	{
		Marginalia marginalia = new Marginalia();
		marginalia.getContent().add(ParagraphProvider.create());
		return marginalia;
	}
}