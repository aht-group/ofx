package org.openfuxml.addon.jsf.factory;

import org.apache.log4j.Logger;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.Paragraph;
import org.openfuxml.addon.jsf.data.jaxb.Section;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;
import org.openfuxml.addon.jsf.data.jaxb.Title;

public class TagSectionFactory
{
	private static Logger logger = Logger.getLogger(TagSectionFactory.class);
	
	private Taglib taglib;
	
	public TagSectionFactory(Taglib taglib)
	{
		this.taglib=taglib;
	}
	
	public Section create(Metatag metatag)
	{	
		Tag tag = metatag.getTag();
		
		Section section = new Section();
		section.setId("sec-tags-"+tag.getName());
		
		Title title = new Title();
		title.setValue("Tag "+taglib.getShortname().trim()+":"+tag.getName());
		section.setTitle(title);
		
		section.getSection().add(getDescription(tag));
		section.getSection().add(getTagTable(metatag));
		
		if(metatag.getExamples() !=null && metatag.getExamples().getExample().size()>0)
		{
			logger.warn("NYI examples");
//			abRoot.addContent(factoryExamples.createSecExamples(metatag.getExamples()));
		}
		
		return section;
	}
	
	private Section getDescription(Tag tag)
	{
		Section section = new Section();
		Title title = new Title();
		title.setValue("Description and Key Features");
		section.setTitle(title);
		if(tag.getDescription()!=null)
		{
			Paragraph p = new Paragraph();
			p.setValue(tag.getDescription().trim());
			section.getParagraph().add(p);
		}
		
		return section;
	}
	
	private Section getTagTable(Metatag metatag)
	{
		TagTableFactory ttf = new TagTableFactory();
		Section section = new Section();
		Title title = new Title();
		title.setValue("Table JSF Elements");
		section.setTitle(title);
		section.getTable().add(ttf.create(metatag));
		return section;
	}
}
