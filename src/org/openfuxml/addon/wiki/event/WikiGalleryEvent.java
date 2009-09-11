package org.openfuxml.addon.wiki.event;

import java.util.Map;

import net.sf.exlp.addon.apache.ejb.ExlpApache;
import net.sf.exlp.addon.apache.facade.ExlpApacheFacade;
import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;

import org.apache.log4j.Logger;

public class WikiGalleryEvent extends AbstractEvent implements LogEvent
{
	static Logger logger = Logger.getLogger(WikiGalleryEvent.class);
	static final long serialVersionUID=1;
	
	private String link, description;
	
	public WikiGalleryEvent(String link, String description)
	{
		this.link=link;
		this.description=description;
	}
	
	public void debug()
	{
		super.debug();
		logger.debug("** Link\t"+link);
		logger.debug("** Desc\t"+description);
	}
}