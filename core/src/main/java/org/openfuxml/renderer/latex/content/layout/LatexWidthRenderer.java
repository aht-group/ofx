package org.openfuxml.renderer.latex.content.layout;

import org.openfuxml.content.layout.Width;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.LatexWidthCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class LatexWidthRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexWidthRenderer.class);
	
	private LatexWidthCalculator lwc;
	
	public LatexWidthRenderer(ConfigurationProvider cp)
	{
		super(cp);
		lwc = new LatexWidthCalculator();
	}
	
	public void render(Object parentRenderer, Width width) throws OfxAuthoringException
	{
		logger.trace(StringUtil.stars());
		JaxbUtil.trace(width);
		
		StringBuffer sb = new StringBuffer();
		sb.append("\\hspace{");
		sb.append(lwc.buildWidth(parentRenderer, width));
		sb.append("}%");
		txt.add(sb.toString());
	}
}