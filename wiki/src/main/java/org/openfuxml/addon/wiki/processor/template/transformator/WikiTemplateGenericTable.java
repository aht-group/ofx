package org.openfuxml.addon.wiki.processor.template.transformator;

import java.io.Serializable;
import java.util.List;

import org.exlp.interfaces.io.NsPrefixMapperInterface;
import org.jdom2.Element;
import org.openfuxml.addon.wiki.processor.markup.WikiInlineProcessor;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.factory.ofx.table.ColumnFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.model.xml.addon.wiki.Template;
import org.openfuxml.model.xml.addon.wiki.TemplateKv;
import org.openfuxml.model.xml.core.table.Body;
import org.openfuxml.model.xml.core.table.Cell;
import org.openfuxml.model.xml.core.table.Columns;
import org.openfuxml.model.xml.core.table.Content;
import org.openfuxml.model.xml.core.table.Head;
import org.openfuxml.model.xml.core.table.Row;
import org.openfuxml.model.xml.core.table.Specification;
import org.openfuxml.model.xml.core.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class WikiTemplateGenericTable implements WikiTemplateTransformator
{
	final static Logger logger = LoggerFactory.getLogger(WikiTemplateGenericTable.class);

	private NsPrefixMapperInterface nsPrefixMapper;
	private WikiInlineProcessor wikiInlineProcessor;
	
	public WikiTemplateGenericTable(NsPrefixMapperInterface nsPrefixMapper) 
	{
		this.nsPrefixMapper=nsPrefixMapper;
	}
	
	public Element transform(Template template)
	{
		Section section = new Section();
		section.setContainer(true);
		
		Table table = getTable(template.getTemplateKv());	
		section.getContent().add(table);
		
		Element result = JaxbUtil.toDocument(section, nsPrefixMapper).getRootElement();
		result.detach();
		return result;
	}
	
	private Table getTable(List<TemplateKv> listKv)
	{
		Table table = new Table();
		table.setTitle(XmlTitleFactory.build("TestTitle"));
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
		catch (OfxInternalProcessingException e) {logger.error("",e);}
		
		row.getCell().add(cellValue);
		
		return row;
	}

	@Override
	public void setNsPrefixMapperInterface(NsPrefixMapperInterface nsPrefixMapper) {}

	@Override
	public void setWikiInlineProcessor(WikiInlineProcessor wikiInlineProcessor) {this.wikiInlineProcessor=wikiInlineProcessor;}
}