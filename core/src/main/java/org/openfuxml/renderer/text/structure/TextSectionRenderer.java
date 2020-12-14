package org.openfuxml.renderer.text.structure;

import java.io.Serializable;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxTextRenderer;
import org.openfuxml.renderer.text.AbstractOfxTextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextSectionRenderer extends AbstractOfxTextRenderer implements OfxTextRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TextSectionRenderer.class);
	
	public TextSectionRenderer(ConfigurationProvider cp)
	{
		this(cp,false);
	}
	
	public TextSectionRenderer(ConfigurationProvider cp, boolean preBlankLine)
	{
		super(cp);
		if(preBlankLine){preTxt.add("");}
	}
	
	public void render(Section section) throws OfxAuthoringException
	{			
		for(Serializable s : section.getContent())
		{
			if(s instanceof Paragraph){super.paragraphRenderer((Paragraph)s);}
			else {logger.error("NYI");}
		}
	}

}
