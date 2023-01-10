package org.openfuxml.factory.xml.ofx.content.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;

public class XmlSectionFactory
{
	public static Section build()
	{
		return new Section();
	}
	
	public static Section build(String localeCode)
	{
		Section xml = new Section();
		xml.setLang(localeCode);
		return xml;
	}
	
	public static Section build(String id, String lang, String classifier)
	{
		Section xml = new Section();
		xml.setId(id);
		xml.setLang(lang);
		xml.setClassifier(classifier);
		return xml;
	}
	
	public static Section paragraph(String text)
	{
		Section xml = new Section();
		xml.getContent().add(XmlParagraphFactory.text(text));
		return xml;
	}
	
	public static List<Table> toTables(Section section)
	{
		List<Table> tables = new ArrayList<Table>();
		for(Serializable s : section.getContent())
		{
			if(s instanceof Table)
			{
				tables.add((Table)s);
			}
		}
		return tables;
	}
	
	public static Section build(Image image) {Section xml = build();xml.getContent().add(image);return xml;}
	public static Section build(Paragraph paragraph) {Section xml = build();xml.getContent().add(paragraph);return xml;}
	
	public static Section copyOnlyAttributes(Section xml)
	{
		Section result = new Section();
		
		return result;
	}
}