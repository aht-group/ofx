package org.openfuxml.util.query;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.jxpath.JXPathContext;
import org.openfuxml.content.ofx.Listing;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;
import org.openfuxml.content.ofx.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class XmlSectionQuery
{
	final static Logger logger = LoggerFactory.getLogger(XmlSectionQuery.class);
	
	public static Title getTitle(Section section) throws ExlpXpathNotFoundException
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
	
	public static Paragraph getFirstParagraph(Section section) throws ExlpXpathNotFoundException
	{
		for(Object o : section.getContent())
		{
			if(o instanceof Paragraph)
			{
				return (Paragraph)o;
			}
		}
		throw new ExlpXpathNotFoundException("No "+Paragraph.class.getSimpleName()+" in this section");
	}

    public static Section getRenderer(Section section, String id) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Section result = null;
    	for(Object o : section.getContent())
    	{
    		if(o instanceof Section)
    		{
    			Section child = (Section)o;
    			if(child.getId().equals(id))
    			{
    				if(result!=null){throw new ExlpXpathNotUniqueException("No unique "+Section.class.getSimpleName()+" for id="+id);}
    				result = child;
    			}
    		}
    	}
    	if(result==null){throw new ExlpXpathNotFoundException("No "+Section.class.getSimpleName()+" found for id="+id);}
    	return result;
    }
    
    public static Listing getSeed(Section section, String id) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(section);
		
		StringBuffer sb = new StringBuffer();
		sb.append("//*[@id='"+id+"']");
		
		@SuppressWarnings("unchecked")
		List<Listing> listResult = (List<Listing>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Listing.class.getSimpleName()+" for id="+id);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Listing.class.getSimpleName()+" for id="+id);}
		return listResult.get(0);
	}
    
    public static <E extends Enum<E>> Section sectionForUniqueClassifier(Sections sections, E classifier) throws ExlpXpathNotUniqueException, ExlpXpathNotFoundException
    {
		Section section = null;
		Section sec = null;
		boolean found = false;
	
		for(Serializable s  : sections.getContent())
		{
			if(s instanceof Section)
			{
				section = (Section)s;
				if(section.getClassifier().contentEquals(classifier.toString()))
				{
					sec = section;
					if(found==true) {throw new ExlpXpathNotUniqueException("Section not unique");}
					found=true;
					if (found) {return sec;}
				}
			}
		}
		if(found==false) {throw new ExlpXpathNotFoundException("Section not available");}
		return section;
    }
    
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