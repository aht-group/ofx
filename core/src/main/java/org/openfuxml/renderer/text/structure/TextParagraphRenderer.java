package org.openfuxml.renderer.text.structure;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxTextRenderer;
import org.openfuxml.renderer.text.AbstractOfxTextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextParagraphRenderer extends AbstractOfxTextRenderer implements OfxTextRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TextParagraphRenderer.class);
	
	public TextParagraphRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		this(cmm,dsm,false);
	}
	
	public TextParagraphRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm,boolean preBlankLine)
	{
		super(cmm,dsm);
		if(preBlankLine){preTxt.add("");}
	}
	
	public void render(Paragraph paragraph) throws OfxAuthoringException
	{			
		StringBuffer sb = new StringBuffer();
		for(Object o : paragraph.getContent())
		{
			if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
			else if(o instanceof String){sb.append((String)o);}
//			else if(o instanceof Emphasis){renderEmphasis(sb, (Emphasis)o);}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
		txt.add(sb.toString());
	}
}
