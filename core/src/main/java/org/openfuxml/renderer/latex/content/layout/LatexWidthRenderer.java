package org.openfuxml.renderer.latex.content.layout;

import org.exlp.util.io.StringUtil;
import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.model.xml.core.layout.Width;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.LatexWidthCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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