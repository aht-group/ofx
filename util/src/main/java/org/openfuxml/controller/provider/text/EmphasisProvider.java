package org.openfuxml.controller.provider.text;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.controller.provider.DemoContentProvider;
import org.openfuxml.factory.xml.text.OfxEmphasisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmphasisProvider
{	
	final static Logger logger = LoggerFactory.getLogger(EmphasisProvider.class);
	
	public enum Key {bold,italic,quote}
	
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

	public static Paragraph underline()
	{
		OfxEmphasisFactory eF = new OfxEmphasisFactory(false,false,false,true);
		return create(eF);
	}

	public static Paragraph typewriter(){return create();}
	
	private static Paragraph create(OfxEmphasisFactory eF)
	{
		Paragraph p = new Paragraph();
    	p.getContent().add(DemoContentProvider.li.getWords(1)+" ");
    	p.getContent().add(eF.build(DemoContentProvider.li.getWords(2)));
    	p.getContent().add(" "+DemoContentProvider.li.getWords(3));
    	return p;
	}

	private static Paragraph create()
	{
		Paragraph p = new Paragraph();
		p.getContent().add(DemoContentProvider.li.getWords(1)+" ");
		p.getContent().add(OfxEmphasisFactory.typewriter(DemoContentProvider.li.getWords(2)));
		p.getContent().add(" "+DemoContentProvider.li.getWords(3));
		return p;
	}
}