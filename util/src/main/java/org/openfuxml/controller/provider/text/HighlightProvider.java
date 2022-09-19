package org.openfuxml.controller.provider.text;

import org.openfuxml.content.ofx.Highlight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HighlightProvider
{	
	final static Logger logger = LoggerFactory.getLogger(HighlightProvider.class);
	
	public static Highlight simple()
	{
		Highlight highlight = new Highlight();
        highlight.getContent().add(ParagraphProvider.create(8));
        highlight.getContent().add(ParagraphProvider.create(4));
        highlight.getContent().add(ParagraphProvider.create(5));
        return highlight;
	}
}