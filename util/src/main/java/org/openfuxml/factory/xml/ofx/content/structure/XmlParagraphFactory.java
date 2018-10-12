package org.openfuxml.factory.xml.ofx.content.structure;

import org.openfuxml.content.ofx.Paragraph;

public class XmlParagraphFactory
{
	public static Paragraph build()
	{
		return new Paragraph();
	}
	
	public static Paragraph lang(String lang)
	{
		Paragraph xml = new Paragraph();
		xml.setLang(lang);
		return xml;
	}
	
	public static Paragraph text(String text)
	{
		Paragraph xml = new Paragraph();
		xml.getContent().add(text);
		return xml;
	}
	
	public static Paragraph build(String lang, String text)
	{
		Paragraph xml = new Paragraph();
		xml.setLang(lang);
		xml.getContent().add(text);
		return xml;
	}
}
