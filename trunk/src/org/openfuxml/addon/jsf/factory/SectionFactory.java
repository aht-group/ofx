package org.openfuxml.addon.jsf.factory;

import java.util.List;

import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.Section;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;
import org.openfuxml.addon.jsf.data.jaxb.Title;

public class SectionFactory
{
	private Taglib taglib;
	
	public SectionFactory(Taglib taglib)
	{
		this.taglib=taglib;
	}
	
	public Section create(List<Metatag> lMetatags)
	{	
		Section section = new Section();
		section.setId("jsf-taglib");
		
		Title title = new Title();
		title.setValue(taglib.getInfo());
		section.setTitle(title);
		
		section.getSection().add(createExternalSubSection("../jsf-doku/tag-introduction.xml", "introduction"));
		for(Metatag metatag : lMetatags)
		{
			Tag tag = metatag.getTag();
			section.getSection().add(createExternalSubSection("sec-"+tag.getName()+".xml", tag.getName()));
		}
		return section;
	}
	
	private Section createExternalSubSection(String source, String sTitle)
	{
		Section section = new Section();
		section.setExternal(true);
		section.setSource(source);
		
		Title title = new Title();
		title.setValue(sTitle);
		section.setTitle(title);
		
		return section;
	}
}
