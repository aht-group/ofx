package org.openfuxml.renderer.processor.epub.factory;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class EpubParagraphFactory
{
	static Log logger = LogFactory.getLog(EpubParagraphFactory.class);
	
	private Namespace ns;
	
	public EpubParagraphFactory(Namespace ns)
	{
		this.ns=ns;
	}
	
	public Element createParagraph(Paragraph p)
	{
		Element eParagraph = new Element("p",ns);
		
		for(Object s : p.getContent())
		{
			if(s instanceof String){eParagraph.addContent((String)s);}
			else {logger.warn("Unknwon Content: "+s.getClass().getSimpleName());}
		}
		
		return eParagraph;
	}
}