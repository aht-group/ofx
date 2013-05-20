package org.openfuxml.renderer.processor.html.section;

import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Text;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.renderer.processor.html.structure.ReferenceRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParagraphRenderer
{
	final static Logger logger = LoggerFactory.getLogger(ParagraphRenderer.class);
	
	public ParagraphRenderer()
	{
		
	}
	
	public Content render(Paragraph ofxParagraph)
	{
		Element p = new Element("p");
		
		for(Object o : ofxParagraph.getContent())
		{
			if(o instanceof String){p.addContent(new Text((String)o));}
			else if(o instanceof Reference){ReferenceRenderer r = new ReferenceRenderer();p.addContent(r.render((Reference)o));}
			else {logger.warn("Unknown content: "+o.getClass().getSimpleName());}
		}
		return p;
	}
}