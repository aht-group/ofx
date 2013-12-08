package org.openfuxml.xml.xpath.content;

import java.util.List;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SectionXpath
{
	final static Logger logger = LoggerFactory.getLogger(SectionXpath.class);
	
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

    public static synchronized Section getRenderer(Section section, String id) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
        JXPathContext context = JXPathContext.newContext(section);
        List<Section> listResult = (List<Section>)context.selectNodes("//section[@id='"+id+"']");
        if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Section.class.getSimpleName()+" found for id="+id);}
        else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("No unique "+Section.class.getSimpleName()+" for id="+id);}

        return listResult.get(0);
    }
}