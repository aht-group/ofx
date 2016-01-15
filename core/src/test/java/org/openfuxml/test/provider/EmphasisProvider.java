package org.openfuxml.test.provider;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.factory.xml.text.OfxEmphasisFactory;
import org.openfuxml.renderer.latex.content.structure.TestLatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmphasisProvider extends AbstractElementProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	public static enum Key {bold,italic,quote}
	
	public static Paragraph bold()
	{
		OfxEmphasisFactory eF = new OfxEmphasisFactory(true,false);
    	return create(eF);
	}
	
	public static Paragraph italic()
	{
		OfxEmphasisFactory eF = new OfxEmphasisFactory(false,true);
    	return create(eF);
	}
	
	public static Paragraph italicBold()
	{
		OfxEmphasisFactory eF = new OfxEmphasisFactory(true,true);
    	return create(eF);
	}
	
	public static Paragraph quote()
	{
		OfxEmphasisFactory eF = new OfxEmphasisFactory(false,false,true);
    	return create(eF);
	}
	
	private static Paragraph create(OfxEmphasisFactory eF)
	{
		Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(1)+" ");
    	p.getContent().add(eF.build(li.getWords(2)));
    	p.getContent().add(" "+li.getWords(3));
    	return p;
	}
}