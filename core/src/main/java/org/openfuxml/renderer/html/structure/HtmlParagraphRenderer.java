package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HtmlParagraphRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlParagraphRenderer.class);

	public HtmlParagraphRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public void render(Paragraph paragraph)
	{
		for(Object o : paragraph.getContent())
		{
			if(o instanceof String){txt.add(((String)o).trim());}
//			else if(o instanceof Emphasis)
//			{
//			}
//			else if(o instanceof Image){imageRenderer((Image)o);}
		}
	}
}
