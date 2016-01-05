package org.openfuxml.factory.xml.text;

import org.openfuxml.content.text.Emphasis;

public class OfxEmphasisFactory
{
	private boolean bold;
	private boolean italic;
	private boolean quote;
	
	public OfxEmphasisFactory(Emphasis emphasis)
	{
		this(emphasis.isSetBold()&&emphasis.isBold(),emphasis.isSetItalic()&&emphasis.isItalic());
	}
	
	public OfxEmphasisFactory(boolean bold, boolean italic)
	{
		this.bold=bold;
		this.italic=italic;
	}
	
	public OfxEmphasisFactory(boolean bold, boolean italic, boolean quote)
	{
		this.bold=bold;
		this.italic=italic;
		this.quote=quote;
	}
	
	public Emphasis build(String text)
	{
		Emphasis emphasis = new Emphasis();
		emphasis.setBold(bold);
		emphasis.setItalic(italic);
		emphasis.setQuote(quote);
		emphasis.setValue(text);
		return emphasis;
	}
	
	public static Emphasis typewriter(String text)
	{
		Emphasis xml = new Emphasis();
		xml.setStyle("typewriter");
		xml.setValue(text);
		return xml;
	}
}