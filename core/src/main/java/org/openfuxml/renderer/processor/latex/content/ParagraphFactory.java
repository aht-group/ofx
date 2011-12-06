package org.openfuxml.renderer.processor.latex.content;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParagraphFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(ParagraphFactory.class);
	
	public ParagraphFactory(boolean preBlankLine)
	{
		if(preBlankLine){preTxt.add("");}
	}
	
	public void render(Paragraph paragraph)
	{
		//TODO manage paragraph top spacing
		//if(paragraph.isSetTop()){logger.warn("Top spacing ignored");}
		for(Object o : paragraph.getContent())
		{
			if(o instanceof String){addString((String)o);}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
	}
	
	private void addString(String s)
	{
		txt.add(s);
	}
}
