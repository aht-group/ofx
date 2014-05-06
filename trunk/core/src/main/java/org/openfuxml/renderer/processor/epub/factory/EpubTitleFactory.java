package org.openfuxml.renderer.processor.epub.factory;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.processor.pre.ExternalContentEagerLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EpubTitleFactory
{
	final static Logger logger = LoggerFactory.getLogger(ExternalContentEagerLoader.class);
	
	private Namespace ns;
	
	public EpubTitleFactory(Namespace ns)
	{
		this.ns=ns;
	}
	
	public Element createTitle(Title title)
	{
		Element eTitle = new Element("h1",ns);
		eTitle.setText(title.getValue());
		return eTitle;
	}
}