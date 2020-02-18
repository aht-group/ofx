package org.openfuxml.util.query;

import java.io.Serializable;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;

import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class XmlSectionQuery
{
	public static <E extends Enum<E>> Section find(Sections sections, E classifier) throws ExlpXpathNotFoundException
	{
		return find(sections,classifier.toString());
	}
	
	private static Section find(Sections sections, String classifier) throws ExlpXpathNotFoundException
	{
		for(Serializable s : sections.getContent())
		{
			if(s instanceof Section)
			{
				Section section = (Section)s;
				if(section.isSetClassifier() && section.getClassifier().equals(classifier)){return section;}
			}
		}
		throw new ExlpXpathNotFoundException("No section found for classifier="+classifier) ;
	}
}