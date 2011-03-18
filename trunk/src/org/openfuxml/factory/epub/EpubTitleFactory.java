package org.openfuxml.factory.epub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class EpubTitleFactory
{
	static Log logger = LogFactory.getLog(OfxExternalMerger.class);
	
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