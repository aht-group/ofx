package org.openfuxml.renderer.processor.html.section;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Text;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.renderer.processor.html.interfaces.OfxSectionRenderer;
import org.openfuxml.renderer.processor.html.interfaces.OfxTableRenderer;
import org.openfuxml.renderer.processor.html.structure.ReferenceRenderer;
import org.openfuxml.renderer.processor.html.table.DefaultTableRenderer;

public class DefaultSectionRenderer implements OfxSectionRenderer
{
	static Log logger = LogFactory.getLog(DefaultSectionRenderer.class);
	
	private List<Content> list;
	private List<Content> subsections;
	private Element divParagraph;
	private Element eHeader;
	
	public DefaultSectionRenderer()
	{
		list = new ArrayList<Content>();
		subsections = new ArrayList<Content>();
		
		divParagraph = new Element("div");
		divParagraph.setAttribute("class", "text3");
	}
	
	public List<Content> render(Section section)
	{
		for(Object o : section.getContent())
		{
			if(o instanceof String){}
			else if(o instanceof Title){createHeader((Title)o);}
			else if(o instanceof Paragraph){addParagrah((Paragraph)o);}
			else if(o instanceof Section){addSection((Section)o);}
			else if(o instanceof Table){OfxTableRenderer r = new DefaultTableRenderer();divParagraph.addContent(r.render((Table)o));}
			else {logger.warn("Unknown content: "+o.getClass().getSimpleName());}
		}

		if(eHeader!=null){list.add(eHeader);}
		
		if(divParagraph.getContentSize()>0){list.add(divParagraph);}
		list.addAll(subsections);
		
		return list;
	}
	
	private void addParagrah(Paragraph ofxParagraph)
	{
		ParagraphRenderer pR = new ParagraphRenderer();
		Content c = pR.render(ofxParagraph);
		divParagraph.addContent(c);
	}
	
	private void addSection(Section section)
	{
		DefaultSection2Renderer render2 = new DefaultSection2Renderer();
		subsections.add(render2.render(section));
	}
	
	private void createHeader(Title title)
	{
		Element h1 = new Element("h1");
		h1.setText(title.getValue());
		
		Element hgroup = new Element("hgroup");
		hgroup.setContent(h1);
		
		Element header = new Element("header");
		header.setContent(hgroup);
		
		eHeader = new Element("div");
		eHeader.setAttribute("class", "text1");
		eHeader.setContent(header);
	}
	
	private class DefaultSection2Renderer
	{
		private Element divParagraph;
		
		public DefaultSection2Renderer()
		{
			divParagraph = new Element("div");
			divParagraph.setAttribute("class", "text2");
		}
		
		public Content render(Section section)
		{
			for(Object o : section.getContent())
			{
				if(o instanceof String){}
				else if(o instanceof Title){createHeader((Title)o);}
				else if(o instanceof Paragraph){addParagrah((Paragraph)o);}
				else if(o instanceof Table){OfxTableRenderer r = new DefaultTableRenderer();divParagraph.addContent(r.render((Table)o));}
				else {logger.warn("Unknown content: "+o.getClass().getSimpleName());}
			}			
			return divParagraph;
		}
		
		private void addParagrah(Paragraph ofxParagraph)
		{
			Element p = new Element("p");
			
			for(Object o : ofxParagraph.getContent())
			{
				if(o instanceof String){p.addContent(new Text((String)o));}
				else if(o instanceof Reference){ReferenceRenderer r = new ReferenceRenderer();p.addContent(r.render((Reference)o));}
				else {logger.warn("Unknown content: "+o.getClass().getSimpleName());}
			}
			divParagraph.addContent(p);
		}
		
		private void createHeader(Title title)
		{
			Element h1 = new Element("h3");
			h1.setText(title.getValue());
			
			Element hgroup = new Element("hgroup");
			hgroup.setContent(h1);
			
			Element eHeader = new Element("header");
			eHeader.setContent(hgroup);
			divParagraph.addContent(eHeader);
			
		}
	}
}