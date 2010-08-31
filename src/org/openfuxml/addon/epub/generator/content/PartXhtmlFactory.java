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
	
	private Namespace nsXhtml;
	
	public PartXhtmlFactory(Namespace nsXhtml)
	{
		this.nsXhtml=nsXhtml;
	}
	
	public Element createPart(Section section)
	{
		Element part = new Element("html",nsXhtml);
		return part;
	}

}