package org.openfuxml.factory.epub;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.producer.preprocessors.ExternalMerger;

public class EpubSectionFactory
{
	static Log logger = LogFactory.getLog(ExternalMerger.class);
	
	private Namespace ns;
	private EpubTitleFactory titleFactory;
	private EpubParagraphFactory paragraphFactory;
	
	public EpubSectionFactory(EpubTitleFactory titleFactory,Namespace ns)
	{
		this.ns=ns;
		this.titleFactory=titleFactory;
		paragraphFactory = new EpubParagraphFactory(ns);
	}
	
	public List<Element> createSection(Section section, int depth)
	{
		List<Element> result = new ArrayList<Element>();
		
		for(Serializable s : section.getContent())
		{
			if(s instanceof Title){result.add(titleFactory.createTitle((Title)s));}
			else if(s instanceof Section){result.addAll(createSection((Section)s, depth++));}
			else if(s instanceof Paragraph){result.add(paragraphFactory.createParagraph((Paragraph)s));}
			else if(s instanceof String){}
			else {logger.warn("Unknwon Content: "+s.getClass().getSimpleName());}
		}		
		return result;
	}
}