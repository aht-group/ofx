package org.openfuxml.addon.wiki.processor.template.transformator;

import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.util.xml.NsPrefixMapperInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.data.jaxb.TemplateKv;
import org.openfuxml.content.factory.ofx.table.ColumnFactory;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.layout.Alignment;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Columns;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Entry;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.content.ofx.table.Specification;
import org.openfuxml.content.ofx.table.Table;

public class WikiTemplateGenericTable implements WikiTemplateTransformator
{
	static Log logger = LogFactory.getLog(WikiTemplateGenericTable.class);
	
	private final Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
	
	private NsPrefixMapperInterface nsPrefixMapper;
	
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
		columns.getColumn().add(ColumnFactory.create(Alignment.LEFT));
		columns.getColumn().add(ColumnFactory.create(Alignment.LEFT));
		
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
		
		Entry teKey = new Entry();
		teKey.setValue("Key");
		row.getEntry().add(teKey);
		
		Entry teValue = new Entry();
		teValue.setValue("Value");
		row.getEntry().add(teValue);
		
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
		
		Entry teKey = new Entry();
		teKey.setValue(kv.getKey());
		row.getEntry().add(teKey);
		
		Entry teValue = new Entry();
		teValue.setValue(kv.getMarkup().getValue());
		row.getEntry().add(teValue);
		
		return row;
	}

	@Override
	public void setNsPrefixMapperInterface(NsPrefixMapperInterface nsPrefixMapper) {}
}