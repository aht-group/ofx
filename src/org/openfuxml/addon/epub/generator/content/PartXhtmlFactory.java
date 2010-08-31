package org.openfuxml.addon.epub.generator.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.producer.preprocessors.ExternalMerger;

public class PartXhtmlFactory
{
	static Log logger = LogFactory.getLog(ExternalMerger.class);
	
	private BodyXhtmlFactory bodyFactory;
	private Namespace nsXhtml;
	
	public PartXhtmlFactory(Namespace nsXhtml)
	{
		this.nsXhtml=nsXhtml;
		bodyFactory = new BodyXhtmlFactory(nsXhtml);
	}
	
	public Element createPart(Section section)
	{
		Element part = new Element("html",nsXhtml);
		part.addContent(getHead());
		part.addContent(bodyFactory.createBody(section));
		return part;
	}
	
	private Element getHead()
	{
		logger.debug("head");
		Element head = new Element("head",nsXhtml);
		
		Element meta = new Element("meta",nsXhtml);
		meta.setAttribute("http-equiv", "Content-Type");
		meta.setAttribute("content", "text/html; charset=UTF-8");
		head.addContent(meta);
		
		Element title = new Element("title",nsXhtml);
		head.addContent(title);
		
		return head;
	}

}