package org.openfuxml.renderer.epub;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EpubParagraphFactory
{
	final static Logger logger = LoggerFactory.getLogger(EpubParagraphFactory.class);
	
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