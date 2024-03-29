package org.openfuxml.renderer.text.structure;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxTextRenderer;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.renderer.text.AbstractOfxTextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextParagraphRenderer extends AbstractOfxTextRenderer implements OfxTextRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TextParagraphRenderer.class);
	
	public TextParagraphRenderer(ConfigurationProvider cp)
	{
		this(cp,false);
	}
	
	public TextParagraphRenderer(ConfigurationProvider cp, boolean preBlankLine)
	{
		super(cp);
		if(preBlankLine){preTxt.add("");}
	}
	
	public void render(Paragraph paragraph) throws OfxAuthoringException
	{			
		txt.add(toText(paragraph));
	}
	
	public String toText(Paragraph paragraph) throws OfxAuthoringException
	{
		StringBuffer sb = new StringBuffer();
		for(Object o : paragraph.getContent())
		{
			if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
			else if(o instanceof String){sb.append((String)o);}
//			else if(o instanceof Emphasis){renderEmphasis(sb, (Emphasis)o);}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
		return sb.toString();
	}
}
