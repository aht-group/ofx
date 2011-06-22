package org.openfuxml.addon.jsftld.ofx;

import java.util.List;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;

public class TagChapterFactory
{
	static Log logger = LogFactory.getLog(TagChapterFactory.class); 
	
	private Taglib taglib;
	
	public TagChapterFactory(Taglib taglib)
	{
		this.taglib=taglib;
	}
	
	public Section create(List<Metatag> lMetatags)
	{	
		Section section = new Section();
		section.setId("jsf-taglib");
		
		Title title = new Title();
		logger.warn("Info is disabled"); //TODO Check description
		title.setValue("Info disabled in XSD");//taglib.getInfo());
		section.getContent().add(title);
		
		section.getContent().add(createExternalSubSection("../jsf-doku/tag-introduction.xml", "introduction"));
		for(Metatag metatag : lMetatags)
		{
			Tag tag = metatag.getTag();
			section.getContent().add(createExternalSubSection("section-"+tag.getName()+".xml", tag.getName()));
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
		section.getContent().add(title);
		
		return section;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		
		Section section = new Section();

		
		Title title = new Title();
		title.setValue("Titel");
		section.getContent().add(title);
		
		JaxbUtil.debug(section);
	}
}
