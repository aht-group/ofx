package org.openfuxml.util.query;

import java.io.Serializable;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;

public class XmlSectionQuery
{
	public static Section find(Sections sections, String classifier)
	{
		for(Serializable s : sections.getContent())
		{
			if(s instanceof Section)
			{
				Section section = (Section)s;
				if(section.isSetClassifier() && section.getClassifier().equals(classifier)){return section;}
			}
		}
		return null;
	}
}
