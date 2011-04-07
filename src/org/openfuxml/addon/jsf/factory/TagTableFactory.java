package org.openfuxml.addon.jsf.factory;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.openfuxml.addon.jsf.data.jaxb.Attribute;
import org.openfuxml.addon.jsf.data.jaxb.JsfNsPrefixMapper;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Entry;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.content.ofx.table.Table;

public class TagTableFactory
{
	static Log logger = LogFactory.getLog(TagTableFactory.class);
	
	public TagTableFactory()
	{
	}
	
	public Table create(Metatag metatag)
	{	
		Table table = new Table();
		table.setTitle(getTitel());
		
		Content tgroup = new Content();
//		table.(getColSpecs());
		tgroup.setHead(getThead());
		tgroup.getBody().add(getBody(metatag));
		
		table.setContent(tgroup);
		return table;
	}
	
	private Head getThead()
	{
		Head thead = new Head();
		Row row = new Row();
		row.getEntry().add(getEntry(1,"Name"));
		row.getEntry().add(getEntry(2,"Required"));
		row.getEntry().add(getEntry(3,"Description"));
		thead.getRow().add(row);
		return thead;
	}
	
	private Body getBody(Metatag metatag)
	{
		Body tbody = new Body();
		for(Attribute att : metatag.getTag().getAttribute())
		{
			Row row = new Row();
			row.getEntry().add(getEntry(1,att.getName()));
			row.getEntry().add(getEntry(2,att.isRequired()+""));
			logger.warn("Description is disabled"); //TODO Check description
//			if(att.isSetDescription()){row.getEntry().add(getRowEntry(3,att.getDescription().trim()));}
			tbody.getRow().add(row);
		}
		return tbody;
	}
	
	private Entry getEntry(int num, String value)
	{
		Entry row = new Entry();
		row.setColname("col"+num);
		row.setValue(value);
		return row;
	}
	
/*	private List<ColSpec> getColSpecs()
	{
		List<ColSpec> lColSpecs = new ArrayList<ColSpec>();
		lColSpecs.add(getcolSpec(1, "4*"));
		lColSpecs.add(getcolSpec(2, "2*"));
		lColSpecs.add(getcolSpec(3, "9*"));
		return lColSpecs;
	}
	
	private ColSpec getcolSpec(int num, String width)
	{
		ColSpec colspec = new ColSpec();
		colspec.setColnum(num);
		colspec.setColname("col"+num);
		colspec.setColwidth(width);
		return colspec;
	}
*/	
	private Title getTitel()
	{
		Title title = new Title();
		title.setValue("Summary of Attributes");
		return title;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		Metatag metatag = (Metatag)JaxbUtil.loadJAXB(args[0], Metatag.class);
		TagTableFactory ttf = new TagTableFactory();
		
		Section section = new Section();
		section.getContent().add(ttf.create(metatag));
		
		Document doc = JaxbUtil.toDocument(section);
		doc = JDomUtil.correctNsPrefixes(doc, new JsfNsPrefixMapper());
		JDomUtil.debug(doc);
	}
}