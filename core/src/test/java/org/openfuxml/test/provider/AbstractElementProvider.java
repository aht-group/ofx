package org.openfuxml.test.provider;

import org.openfuxml.renderer.latex.content.structure.TestLatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.svenjacobs.loremipsum.LoremIpsum;

public class AbstractElementProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	protected LoremIpsum li;
	
	public AbstractElementProvider()
	{
		li = new LoremIpsum();
	}
}