package org.openfuxml.addon.jsftld.ofx;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.xml.addon.jsftld.Attribute;
import org.openfuxml.xml.addon.jsftld.Metatag;
import org.openfuxml.xml.ns.OfxNsPrefixMapper;

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
		row.getCell().add(getCell(1,"Name"));
		row.getCell().add(getCell(2,"Required"));
		row.getCell().add(getCell(3,"Description"));
		thead.getRow().add(row);
		return thead;
	}
	
	private Body getBody(Metatag metatag)
	{
		Body tbody = new Body();
		for(Attribute att : metatag.getTag().getAttribute())
		{
			Row row = new Row();
			row.getCell().add(getCell(1,att.getName()));
			row.getCell().add(getCell(2,att.isRequired()+""));
			logger.warn("Description is disabled"); //TODO Check description
//			if(att.isSetDescription()){row.getEntry().add(getRowEntry(3,att.getDescription().trim()));}
			tbody.getRow().add(row);
		}
		return tbody;
	}
	
	private Cell getCell(int num, String value)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(value);
		
		Cell cell = new Cell();
		cell.getContent().add(p);
		return cell;
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
		doc = JDomUtil.correctNsPrefixes(doc, new OfxNsPrefixMapper());
		JDomUtil.debug(doc);
	}
}