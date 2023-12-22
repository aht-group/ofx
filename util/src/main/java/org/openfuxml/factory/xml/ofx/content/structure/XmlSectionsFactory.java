package org.openfuxml.factory.xml.ofx.content.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.model.xml.core.ofx.Sections;

public class XmlSectionsFactory
{
	public static Sections build(){return new Sections();}
	
	public static Sections build(Section section)
	{
		Sections xml = build();
		xml.getContent().add(section);
		return xml;
	}
	
	public static List<Section> toList(Sections sections)
	{
		List<Section> list = new ArrayList<Section>();
		for(Serializable s : sections.getContent())
		{
			if(s instanceof Section) {list.add((Section)s);}
		}
		return list;
	}
}