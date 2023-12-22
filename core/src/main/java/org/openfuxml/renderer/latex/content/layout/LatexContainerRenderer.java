package org.openfuxml.renderer.latex.content.layout;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.model.xml.core.layout.Column;
import org.openfuxml.model.xml.core.layout.Container;
import org.openfuxml.model.xml.core.layout.Font;
import org.openfuxml.model.xml.core.media.Image;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.LatexFontUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class LatexContainerRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexContainerRenderer.class);
	
	public LatexContainerRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Container container) throws OfxAuthoringException
	{
		logger.trace(StringUtil.stars());
		JaxbUtil.trace(container);
		Font font = null;
		
		// Font has special handling!
		for(Object o : container.getContent())
		{
			if(o instanceof Font)
			{
				if(font!=null){throw new OfxAuthoringException("More than one "+Font.class.getSimpleName()+" in "+Paragraph.class.getSimpleName()+" not allowed");}
				font = (Font)o;
			}
		}
				
		if(font!=null){preTxt.add(LatexFontUtil.environmentBegin(font));}
		
		for(Object o : container.getContent())
		{
			if     (o instanceof String){}
			else if(o instanceof Paragraph){paragraphRenderer((Paragraph)o,true);}
			else if(o instanceof Container){containerRenderer((Container)o);}
			else if(o instanceof Image){renderImage((Image)o);}
			else if(o instanceof Column){columnRenderer((Column)o);}
			else if(o instanceof Font){}
			else {logger.warn("No Renderer for Element "+o.getClass().getSimpleName());}
		}

		postTxt.add("");
		if(font!=null){postTxt.add(LatexFontUtil.environmentEnd());}
	}
}