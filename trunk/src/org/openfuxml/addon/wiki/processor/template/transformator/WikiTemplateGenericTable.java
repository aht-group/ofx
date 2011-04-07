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
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Entry;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Row;
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
		table.setContent(getTableContent(listKv));
		return table;
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
		Head thead = new Head();
		
		return thead;
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