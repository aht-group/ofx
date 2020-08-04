package org.openfuxml.test.provider;

import org.openfuxml.content.ofx.Title;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.renderer.latex.structure.TestLatexParagraphRenderer;
import org.openfuxml.util.provider.DemoContentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TitleProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	public static Title create(){return create(4);}
	private static Title create(int words)
	{
		return XmlTitleFactory.build(DemoContentProvider.li.getWords(words));
	}
}