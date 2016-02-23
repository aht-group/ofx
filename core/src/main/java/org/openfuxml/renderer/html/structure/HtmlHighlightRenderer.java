package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Highlight;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlHighlightRenderer extends AbstractOfxHtmlRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlHighlightRenderer.class);
//	static int count=0;

	public HtmlHighlightRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(HtmlElement parent, Highlight highlight)
	{
		HtmlElement ele = new HtmlElement("p");

//		parent.setAttribute("id","highlight"+ ++count);
		ele.setAttribute("style", "background-color:lightgray;");
		for(Object o : highlight.getContent())
		{
			if(o instanceof String){ele.addContent(((String)o));}
			else if(o instanceof Marginalia){marginaliaRenderer(ele, ((Marginalia)o));}
			else if(o instanceof Paragraph){paragraphContentRenderer(parent, ((Paragraph)o));}
		}
		parent.addContent(ele);
	}
}
