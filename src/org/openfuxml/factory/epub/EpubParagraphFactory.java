package org.openfuxml.factory.epub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.producer.preprocessors.ExternalMerger;

public class EpubParagraphFactory
{
	static Log logger = LogFactory.getLog(ExternalMerger.class);
	
	private Namespace ns;
	
	public EpubParagraphFactory(Namespace ns)
	{
		this.ns=ns;
	}
	
	public Element createParagraph(Paragraph p)
	{
		Element eParagraph = new Element("p",ns);
				
		return eParagraph;
	}
}