package org.openfuxml.addon.wiki.processor.template.exlp.event;

import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.model.WikiTemplateKeyValue;

public class WikiKeyValueEvent extends AbstractEvent implements LogEvent
{
	static Log logger = LogFactory.getLog(WikiKeyValueEvent.class);
	static final long serialVersionUID=1;
	
	private WikiTemplateKeyValue kv;

	public WikiKeyValueEvent(WikiTemplateKeyValue kv)
	{
		this.kv=kv;
	}
	
	public void debug()
	{
		super.debug();
		logger.debug("Key: "+kv.getKey());
		logger.debug("Value: "+kv.getValueMarkup());
	}
	
	public WikiTemplateKeyValue getKv() {return kv;}
}