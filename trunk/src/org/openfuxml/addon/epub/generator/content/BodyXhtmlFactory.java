package org.openfuxml.addon.epub.generator.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.epub.EpubSectionFactory;
import org.openfuxml.factory.epub.EpubTitleFactory;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class BodyXhtmlFactory
{
	static Log logger = LogFactory.getLog(OfxExternalMerger.class);
	
	private Namespace ns;
	private EpubSectionFactory sectionFactory;
	private EpubTitleFactory titleFactory;
	
	public BodyXhtmlFactory(Namespace ns)
	{
		this.ns=ns;
		titleFactory = new EpubTitleFactory(ns);
		sectionFactory = new EpubSectionFactory(titleFactory,ns);
	}
	
	public Element createBody(Section section)
	{
		logger.debug("add body");
		Element body = new Element("body",ns);
		
		body.addContent(sectionFactory.createSection(section,1));
		
		return body;
	}
}