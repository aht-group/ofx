package org.openfuxml.renderer.processor.html.section;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Text;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.renderer.processor.html.structure.ReferenceRenderer;

public class ParagraphRenderer
{
	static Log logger = LogFactory.getLog(ParagraphRenderer.class);
	
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