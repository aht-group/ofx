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
import org.openfuxml.content.ofx.Table;
import org.openfuxml.content.ofx.TableBody;
import org.openfuxml.content.ofx.TableEntry;
import org.openfuxml.content.ofx.TableGroup;
import org.openfuxml.content.ofx.TableHead;
import org.openfuxml.content.ofx.TableRow;
import org.openfuxml.content.ofx.Title;

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
		table.setTableGroup(getTableGroup(listKv));
		return table;
	}
	
	private Title getTitle()
	{
		Title title = new Title();
		title.setValue("TestTitel");
		return title;
	}
	
	private TableGroup getTableGroup(List<TemplateKv> listKv)
	{
		TableGroup tgroup = new TableGroup();
		tgroup.setTableHead(getTableHead());
		tgroup.setTableBody(getTableBody(listKv));
		return tgroup;
	}
	
	private TableHead getTableHead()
	{
		TableHead thead = new TableHead();
		
		return thead;
	}
	
	private TableBody getTableBody(List<TemplateKv> listKv)
	{
		TableBody tbody = new TableBody();
		
		for(TemplateKv kv : listKv)
		{
			tbody.getTableRow().add(getRow(kv));
		}
		
		return tbody;
	}
	
	private TableRow getRow(TemplateKv kv)
	{
		TableRow row = new TableRow();
		
		TableEntry teKey = new TableEntry();
		teKey.setValue(kv.getKey());
		row.getTableEntry().add(teKey);
		
		TableEntry teValue = new TableEntry();
		teValue.setValue(kv.getMarkup().getValue());
		row.getTableEntry().add(teValue);
		
		return row;
	}

	@Override
	public void setNsPrefixMapperInterface(NsPrefixMapperInterface nsPrefixMapper) {}
}