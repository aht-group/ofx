package org.openfuxml.addon.epub.generator.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class PartXhtmlFactory
{
	static Log logger = LogFactory.getLog(OfxExternalMerger.class);
	
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
		
		Element metaContent = new Element("meta",nsXhtml);
		metaContent.setAttribute("http-equiv", "Content-Type");
		metaContent.setAttribute("content", "text/html; charset=UTF-8");
		head.addContent(metaContent);
		
		Element metaName = new Element("meta",nsXhtml);
		metaName.setAttribute("name", "DC.identifier");
		metaName.setAttribute("content", "id_Hello_World");
		head.addContent(metaName);
		
		Element link = new Element("link",nsXhtml);
		link.setAttribute("rel", "schema.DC");
		link.setAttribute("href", "http://purl.org/dc/elements/1.1/");
		head.addContent(link);
		
		Element title = new Element("title",nsXhtml);
		title.setText("TestTitle");
		head.addContent(title);
		
		return head;
	}

}