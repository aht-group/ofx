package org.openfuxml.renderer.latex.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.renderer.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.OfxLatexRenderer;

public class ParagraphFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(ParagraphFactory.class);
	
	public ParagraphFactory()
	{

	}
	
	public void render(Paragraph paragraph)
	{
		if(paragraph.isSetTop()){logger.warn("Top spacing ignored");}
		for(Object o : paragraph.getContent())
		{
			if(o instanceof String){addString((String)o);}
			else {logger.warn("Unknown object");}
		}
	}
	
	private void addString(String s)
	{
		txt.add(s);
	}
}
