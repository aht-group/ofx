package org.openfuxml.addon.jsf.factory;

import java.io.File;

import org.apache.log4j.Logger;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;

public class TagSectionFactory
{
	private static Logger logger = Logger.getLogger(TagSectionFactory.class);
	
	private Taglib taglib;
	private TagExampleFactory factoryExamples;
	
	public TagSectionFactory(Taglib taglib, File docBase)
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
		title.setValue("Tag "+taglib.getShortname().trim()+":"+tag.getName());
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
		if(tag.getDescription()!=null)
		{
			Paragraph p = new Paragraph();
			p.getContent().add(tag.getDescription().trim());
			section.getContent().add(p);
		}
		
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
