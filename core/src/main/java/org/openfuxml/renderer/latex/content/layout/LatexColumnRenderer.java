package org.openfuxml.renderer.latex.content.layout;

import org.openfuxml.content.layout.Column;
import org.openfuxml.content.layout.Container;
import org.openfuxml.content.layout.Font;
import org.openfuxml.content.layout.Height;
import org.openfuxml.content.layout.Width;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.LatexRaiseboxUtil;
import org.openfuxml.renderer.latex.util.LatexWidthCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class LatexColumnRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexColumnRenderer.class);
	
	private LatexWidthCalculator lwc;
	
	public LatexColumnRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
		lwc = new LatexWidthCalculator();
	}
	
	public void render(Object parentRenderer, Column column) throws OfxAuthoringException
	{
		logger.trace(StringUtil.stars());
		JaxbUtil.trace(column);
		
		Width width = null;
		Height height = null;
		
		for(Object o : column.getContent())
		{
			if      (o instanceof Width){width = (Width)o;}
			else if (o instanceof Height){height = (Height)o;}
		}
		
		preTxt.add("\\begin{minipage}[t]"+minipageArguments(parentRenderer,width));
		LatexRaiseboxUtil.minipageBegin(preTxt,height);
		
		for(Object o : column.getContent())
		{
			if     (o instanceof String){}
			else if(o instanceof Paragraph){paragraphRenderer((Paragraph)o,true);}
			else if(o instanceof Container){containerRenderer((Container)o);}
			else if(o instanceof Image){renderImage((Image)o);}
			else if(o instanceof Font){}
			else if(o instanceof Width){}
			else if(o instanceof Height){}
			else {logger.warn("No Renderer for Element "+o.getClass().getSimpleName());}
		}

		LatexRaiseboxUtil.minipageEnd(postTxt,height);
		postTxt.add("\\end{minipage}%");
	}
	
	
	private String minipageArguments(Object parentRenderer,Width width) throws OfxAuthoringException
	{
		// {0.25\\marginparwidth}
		if(width==null){throw new OfxAuthoringException(Width.class.getSimpleName()+" required for "+Column.class.getSimpleName());}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append(lwc.buildWidth(parentRenderer,width));
		sb.append("}");
		
		return sb.toString();
	}
}