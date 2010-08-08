package org.openfuxml.addon.jsf.factory;

import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jdom.Document;
import org.openfuxml.addon.jsf.data.jaxb.Attribute;
import org.openfuxml.addon.jsf.data.jaxb.JsfNsPrefixMapper;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.content.Row;
import org.openfuxml.content.Section;
import org.openfuxml.content.Table;
import org.openfuxml.content.Title;

public class TagTableFactory
{
	
	public TagTableFactory()
	{
	}
	
	public Table create(Metatag metatag)
	{	
		Table table = new Table();
		table.setTitle(getTitel());
		
		Table.Tgroup tgroup = new Table.Tgroup();
		tgroup.setCols(3);
		tgroup.getColspec().addAll(getColSpecs());
		tgroup.setThead(getThead());
		tgroup.setTbody(getBody(metatag));
		
		table.setTgroup(tgroup);
		return table;
	}
	
	private Table.Tgroup.Thead getThead()
	{
		Table.Tgroup.Thead thead = new Table.Tgroup.Thead();
		Row row = new Row();
		row.getEntry().add(getRowEntry(1,"Name"));
		row.getEntry().add(getRowEntry(2,"Required"));
		row.getEntry().add(getRowEntry(3,"Description"));
		thead.setRow(row);
		return thead;
	}
	
	private Table.Tgroup.Tbody getBody(Metatag metatag)
	{
		Table.Tgroup.Tbody tbody = new Table.Tgroup.Tbody();
		for(Attribute att : metatag.getTag().getAttribute())
		{
			Row row = new Row();
			row.getEntry().add(getRowEntry(1,att.getName()));
			row.getEntry().add(getRowEntry(2,att.isRequired()+""));
			if(att.isSetDescription()){row.getEntry().add(getRowEntry(3,att.getDescription().trim()));}
			tbody.getRow().add(row);
		}
		return tbody;
	}
	
	private Row.Entry getRowEntry(int num, String value)
	{
		Row.Entry row = new Row.Entry();
		row.setColname("col"+num);
		row.setValue(value);
		return row;
	}
	
	private List<Table.Tgroup.Colspec> getColSpecs()
	{
		List<Table.Tgroup.Colspec> lColSpecs = new ArrayList<Table.Tgroup.Colspec>();
		lColSpecs.add(getcolSpec(1, "4*"));
		lColSpecs.add(getcolSpec(2, "2*"));
		lColSpecs.add(getcolSpec(3, "9*"));
		return lColSpecs;
	}
	
	private Table.Tgroup.Colspec getcolSpec(int num, String width)
	{
		Table.Tgroup.Colspec colspec = new Table.Tgroup.Colspec();
		colspec.setColnum(num);
		colspec.setColname("col"+num);
		colspec.setColwidth(width);
		return colspec;
	}
	
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