package org.openfuxml.xml.xpath.content;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;

public class SectionXpath
{
	static Log logger = LogFactory.getLog(SectionXpath.class);
	
	public static synchronized Title getTitle(Section section) throws ExlpXpathNotFoundException
	{
		for(Object o : section.getContent())
		{
			if(o instanceof Title)
			{
				return (Title)o;
			}
		}
		throw new ExlpXpathNotFoundException("No "+Title.class.getSimpleName()+" in this section");
	}
}