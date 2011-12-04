package org.openfuxml.addon.wiki.processor.template.transformator;

import java.io.Serializable;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.data.jaxb.TemplateKv;
import org.openfuxml.addon.wiki.processor.markup.WikiInlineProcessor;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.content.ofx.table.Columns;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.content.ofx.table.Specification;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.factory.ofx.table.ColumnFactory;

public class WikiTemplateGenericTable implements WikiTemplateTransformator
{
	static Log logger = LogFactory.getLog(WikiTemplateGenericTable.class);
	
	private final Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
	
	private NsPrefixMapperInterface nsPrefixMapper;
	private WikiInlineProcessor wikiInlineProcessor;
	
	public WikiTemplateGenericTable(NsPrefixMapperInterface nsPrefixMapper) 
	{
		this.nsPrefixMapper=nsPrefixMapper;
	}
	
	public Element transform(Template template)
	{
		Section section = new Section();
		section.setTransparent(true);
		
		Table table = getTable(template.getTemplateKv());	
		section.getContent().add(table);
		
		Element result = JaxbUtil.toDocument(section, nsPrefixMapper).getRootElement();
		result.detach();
		return result;
	}
	
	private Table getTable(List<TemplateKv> listKv)
	{
		Table table = new Table();
		table.setTitle(getTitle());
		table.setSpecification(getSpecification());
		table.setContent(getTableContent(listKv));
		return table;
	}
	
	private Specification getSpecification()
	{
		Specification specification = new Specification();
		
		Columns columns = new Columns();
		columns.getColumn().add(ColumnFactory.create("left",2));
		columns.getColumn().add(ColumnFactory.create("left",4));
		
		specification.setColumns(columns);
		return specification;
	}
	
	private Title getTitle()
	{
		Title title = new Title();
		title.setValue("TestTitel");
		return title;
	}
	
	private Content getTableContent(List<TemplateKv> listKv)
	{
		Content tgroup = new Content();
		tgroup.setHead(getTableHead());
		tgroup.getBody().add(getTableBody(listKv));
		return tgroup;
	}
	
	private Head getTableHead()
	{
		Head head = new Head();
		
		Row row = new Row();
		
		Cell cellKey = new Cell();
		cellKey.getContent().add("Key");
		row.getCell().add(cellKey);
		
		Cell teValue = new Cell();
		teValue.getContent().add("Value");
		row.getCell().add(teValue);
		
		head.getRow().add(row);
		return head;
	}
	
	private Body getTableBody(List<TemplateKv> listKv)
	{
		Body tbody = new Body();
		
		for(TemplateKv kv : listKv)
		{
			tbody.getRow().add(getRow(kv));
		}
		
		return tbody;
	}
	
	private Row getRow(TemplateKv kv)
	{
		Row row = new Row();
		
		Paragraph p = new Paragraph();
		p.getContent().add(kv.getKey());
		
		Cell cellKey = new Cell();
		cellKey.getContent().add(p);
		row.getCell().add(cellKey);
		
		Cell cellValue = new Cell();
		
		try
		{
			Section section = wikiInlineProcessor.toOfx(kv.getMarkup().getValue());
			for(Object s : section.getContent())
			{
				cellValue.getContent().add((Serializable)s);
			}
			
		}
		catch (OfxInternalProcessingException e) {logger.error(e);}
		
		row.getCell().add(cellValue);
		
		return row;
	}

	@Override
	public void setNsPrefixMapperInterface(NsPrefixMapperInterface nsPrefixMapper) {}

	@Override
	public void setWikiInlineProcessor(WikiInlineProcessor wikiInlineProcessor) {this.wikiInlineProcessor=wikiInlineProcessor;}
}