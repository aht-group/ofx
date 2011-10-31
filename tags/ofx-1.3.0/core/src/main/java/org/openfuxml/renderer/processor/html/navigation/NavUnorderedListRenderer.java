package org.openfuxml.renderer.processor.html.navigation;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.renderer.processor.html.interfaces.OfxNavigationRenderer;
import org.openfuxml.xml.xpath.content.SectionXpath;

public class NavUnorderedListRenderer implements OfxNavigationRenderer
{
	static Log logger = LogFactory.getLog(NavUnorderedListRenderer.class);
	
	public NavUnorderedListRenderer()
	{

	}
	
	public Element render(Ofxdoc ofxDoc, Section actualSection)
	{
		Element rootElement = new Element("ul");
		
		for(Object o : ofxDoc.getContent().getContent())
		{
			if(o instanceof Section){rootElement.addContent(getSection((Section)o));}
		}
		
		return rootElement;
	}
	
	private Element getSection(Section section)
	{
		
		Element a = new Element("a");
		try
		{
			Title title = SectionXpath.getTitle(section);
			a.setAttribute("href", section.getId()+".html");
			a.setText(title.getValue());
		}
		catch (ExlpXpathNotFoundException e)
		{
			logger.debug(e);
		}
		Element li = new Element("li");
		li.setContent(a);
		
		return li;
	}
}