package org.openfuxml.addon.jsf.factory.ofx;

import java.io.File;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.xml.addon.jsf.tld.FaceletTaglib;
import org.openfuxml.xml.addon.jsf.tld.Metatag;
import org.openfuxml.xml.addon.jsf.tld.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TagSectionFactory
{
	final static Logger logger = LoggerFactory.getLogger(TagSectionFactory.class);
	
	private FaceletTaglib taglib;
	private TagExampleFactory factoryExamples;
	
	public TagSectionFactory(FaceletTaglib taglib, File docBase)
	{
		this.taglib=taglib;
		factoryExamples = new TagExampleFactory(docBase);
	}
	
	public Section create(Metatag metatag)
	{	
		Tag tag = metatag.getTag();
		
		Section section = new Section();
		section.setId("sec-tags-"+tag.getName());
		
		Title title = new Title();
		title.setValue("Tag "+taglib.getShortName().trim()+":"+tag.getName());
		title.setValue("Tag "+taglib.getShortName().trim()+":"+tag.getName());
		section.getContent().add(title);
		
		section.getContent().add(getDescription(tag));
		section.getContent().add(getTagTable(metatag));
		
		if(metatag.getExamples() !=null && metatag.getExamples().getExample().size()>0)
		{
			section.getContent().add(factoryExamples.create(metatag.getExamples()));
		}
		
		return section;
	}
	
	private Section getDescription(Tag tag)
	{
		Section section = new Section();
		Title title = new Title();
		title.setValue("Description and Key Features");
		section.getContent().add(title);
		logger.warn("Description disabled");//TODO CHeck description
/*		if(tag.getDescription()!=null)
		{
			Paragraph p = new Paragraph();
			p.getContent().add(tag.getDescription().trim());
			section.getContent().add(p);
		}
*/		
		return section;
	}
	
	private Section getTagTable(Metatag metatag)
	{
		TagTableFactory ttf = new TagTableFactory();
		Section section = new Section();
		Title title = new Title();
		title.setValue("Table JSF Elements");
		section.getContent().add(title);
		section.getContent().add(ttf.create(metatag));
		return section;
	}
}
