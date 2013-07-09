package org.openfuxml.xml.xpath.content;

import net.sf.exlp.exception.ExlpXpathNotFoundException;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.xml.xpath.cmp.HtmlXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SectionXpath
{
	final static Logger logger = LoggerFactory.getLogger(HtmlXpath.class);
	
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