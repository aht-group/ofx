package org.openfuxml.factory.xml.ofx.content.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;

public class XmlSectionFactory
{
	public static Section build()
	{
		return new Section();
	}
	
	public static Section build(String lang)
	{
		Section xml = new Section();
		xml.setLang(lang);
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
}