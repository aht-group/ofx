package org.openfuxml.factory.xml.text;

import java.util.Objects;

import org.openfuxml.content.text.Emphasis;

public class OfxEmphasisFactory
{
	private boolean bold;
	private boolean italic;
	private boolean quote;
	private boolean underline;
	
	public OfxEmphasisFactory(Emphasis emphasis)
	{
		this(Objects.nonNull(emphasis.isBold()) && emphasis.isBold(), Objects.nonNull(emphasis.isItalic()) && emphasis.isItalic());
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

	public OfxEmphasisFactory(boolean bold, boolean italic, boolean quote, boolean underline)
	{
		this.bold= bold;
		this.italic= italic;
		this.quote= quote;
		this.underline= underline;
	}

	public Emphasis build(String text)
	{
		Emphasis emphasis = new Emphasis();
		emphasis.setBold(bold);
		emphasis.setItalic(italic);
		emphasis.setQuote(quote);
		emphasis.setUnderline(underline);
		emphasis.setValue(text);
		return emphasis;
	}
	
	public static Emphasis italic(String text)
	{
		Emphasis xml = new Emphasis();
		xml.setItalic(true);
		xml.setValue(text);
		return xml;
	}
	
	public static Emphasis bold(String text)
	{
		Emphasis xml = new Emphasis();
		xml.setBold(true);
		xml.setValue(text);
		return xml;
	}
	
	public static Emphasis typewriter(String text)
	{
		Emphasis xml = new Emphasis();
		xml.setStyle("typewriter");
		xml.setValue(text);
		return xml;
	}
}