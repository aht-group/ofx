package org.openfuxml.addon.wiki.processor.template.transformator;

import net.sf.exlp.event.LogEvent;
import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerString;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.util.xml.NsPrefixMapperInterface;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.processor.template.exlp.event.WikiKeyValueEvent;
import org.openfuxml.addon.wiki.processor.template.exlp.parser.WikiKeyValueParser;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Table;
import org.openfuxml.content.ofx.TableBody;
import org.openfuxml.content.ofx.TableGroup;
import org.openfuxml.content.ofx.TableHead;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.util.xml.OfxNsPrefixMapper;

public class WikiTemplateGenericTable extends AbstractWikiProcessor implements WikiProcessor
{
	static Log logger = LogFactory.getLog(WikiTemplateGenericTable.class);
	
	private final Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
	
	private NsPrefixMapperInterface nsPrefixMapper;
	
	public WikiTemplateGenericTable(NsPrefixMapperInterface nsPrefixMapper) 
	{
		this.nsPrefixMapper=nsPrefixMapper;
	}
	
	public Element transform(String wikiMarkup)
	{
		EhResultContainer leh = new EhResultContainer();
		LogParser lp = new WikiKeyValueParser(leh);
		LogListener ll = new LogListenerString(wikiMarkup,lp);
		ll.processSingle();
		
		logger.debug(leh.getAlResults().size());
		
		Element e = new Element("section");
		e.setNamespace(nsOfx);
		e.setAttribute("transparent","true");
		e.setText("This is the content!!");
		
		Section section = new Section();
		section.setTransparent(true);
		
		Table table = getTable();
		for(LogEvent logEvent : leh.getAlResults())
		{
			WikiKeyValueEvent kvEvent= (WikiKeyValueEvent)logEvent;
			Element eKv = new Element("paragraph");
			eKv.setNamespace(nsOfx);
			eKv.setText(kvEvent.getKv().getKey()+": ");
			e.addContent(eKv);
		}
		
		section.getContent().add(table);
		Element result = JaxbUtil.toDocument(section, nsPrefixMapper).getRootElement();
		result.detach();
		return result;
	}
	
	private Table getTable()
	{
		Table table = new Table();
		table.setTitle(getTitle());
		table.setTableGroup(getTableGroup());
		return table;
	}
	
	private Title getTitle()
	{
		Title title = new Title();
		title.setValue("TestTitel");
		return title;
	}
	
	private TableGroup getTableGroup()
	{
		TableGroup tgroup = new TableGroup();
		tgroup.setTableHead(getTableHead());
		tgroup.setTableBody(new TableBody());
		return tgroup;
	}
	
	private TableHead getTableHead()
	{
		TableHead thead = new TableHead();
		
		return thead;
	}
}