package org.openfuxml.util.filter;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxSectionFilter
{
	final static Logger logger = LoggerFactory.getLogger(OfxSectionFilter.class);
	
	public static List<Paragraph> toParagraphs(Section s)
	{
		List<Paragraph> list = new ArrayList<>();
		for (Object o : s.getContent())
		{	
			if(o instanceof org.openfuxml.content.ofx.Paragraph)
			{
				list.add((org.openfuxml.content.ofx.Paragraph)o);
			}
		}
		return list;
	}
	
	public static Section toMaxDepth(Section s, int depth)
	{
		
		return s;
	}
	
	
}