package org.openfuxml.addon.jsf.factory;

import java.util.ArrayList;
import java.util.List;

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
import org.openfuxml.content.ofx.Table;
import org.openfuxml.content.ofx.TableBody;
import org.openfuxml.content.ofx.TableColSpec;
import org.openfuxml.content.ofx.TableEntry;
import org.openfuxml.content.ofx.TableGroup;
import org.openfuxml.content.ofx.TableHead;
import org.openfuxml.content.ofx.TableRow;
import org.openfuxml.content.ofx.Title;

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
		
		TableGroup tgroup = new TableGroup();
		tgroup.getTableColSpec().addAll(getColSpecs());
		tgroup.setTableHead(getThead());
		tgroup.setTableBody(getBody(metatag));
		
		table.setTableGroup(tgroup);
		return table;
	}
	
	private TableHead getThead()
	{
		TableHead thead = new TableHead();
		TableRow row = new TableRow();
		row.getTableEntry().add(getRowEntry(1,"Name"));
		row.getTableEntry().add(getRowEntry(2,"Required"));
		row.getTableEntry().add(getRowEntry(3,"Description"));
		thead.setTableRow(row);
		return thead;
	}
	
	private TableBody getBody(Metatag metatag)
	{
		TableBody tbody = new TableBody();
		for(Attribute att : metatag.getTag().getAttribute())
		{
			TableRow row = new TableRow();
			row.getTableEntry().add(getRowEntry(1,att.getName()));
			row.getTableEntry().add(getRowEntry(2,att.isRequired()+""));
			logger.warn("Description is disabled"); //TODO Check description
//			if(att.isSetDescription()){row.getEntry().add(getRowEntry(3,att.getDescription().trim()));}
			tbody.getTableRow().add(row);
		}
		return tbody;
	}
	
	private TableEntry getRowEntry(int num, String value)
	{
		TableEntry row = new TableEntry();
		row.setColname("col"+num);
		row.setValue(value);
		return row;
	}
	
	private List<TableColSpec> getColSpecs()
	{
		List<TableColSpec> lColSpecs = new ArrayList<TableColSpec>();
		lColSpecs.add(getcolSpec(1, "4*"));
		lColSpecs.add(getcolSpec(2, "2*"));
		lColSpecs.add(getcolSpec(3, "9*"));
		return lColSpecs;
	}
	
	private TableColSpec getcolSpec(int num, String width)
	{
		TableColSpec colspec = new TableColSpec();
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