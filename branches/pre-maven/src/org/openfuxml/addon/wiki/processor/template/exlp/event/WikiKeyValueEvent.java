package org.openfuxml.addon.wiki.processor.template.exlp.event;

import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.TemplateKv;

public class WikiKeyValueEvent extends AbstractEvent implements LogEvent
{
	static Log logger = LogFactory.getLog(WikiKeyValueEvent.class);
	static final long serialVersionUID=1;
	
	private TemplateKv kv;

	public WikiKeyValueEvent(TemplateKv kv)
	{
		this.kv=kv;
	}
	
	public void debug()
	{
		super.debug();
		logger.debug("Key: "+kv.getKey());
		logger.debug("Value: "+kv.getMarkup().getValue());
	}
	
	public TemplateKv getKv() {return kv;}
}