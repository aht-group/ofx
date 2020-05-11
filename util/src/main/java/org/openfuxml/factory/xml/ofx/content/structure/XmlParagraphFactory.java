package org.openfuxml.factory.xml.ofx.content.structure;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.text.Emphasis;

public class XmlParagraphFactory
{
	public static Paragraph build(){return new Paragraph();}
	
	public static Paragraph build(Emphasis emphasis)
	{
		Paragraph xml = build();
		xml.getContent().add(emphasis);
		return xml;
	}
	
	public static Paragraph lang(String lang)
	{
		Paragraph xml = build();
		xml.setLang(lang);
		return xml;
	}
	
	public static Paragraph text(String text)
	{
		Paragraph xml = build();
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
