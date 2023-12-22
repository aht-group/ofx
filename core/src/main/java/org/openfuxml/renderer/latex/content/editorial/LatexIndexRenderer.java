package org.openfuxml.renderer.latex.content.editorial;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.model.xml.core.editorial.Index;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexIndexRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexIndexRenderer.class);

	public LatexIndexRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Index index) throws OfxAuthoringException
	{
		StringBuffer sb = new StringBuffer();
		sb.append(index.getCode());
		sb.append("\\index{");
		sb.append(index.getCode().substring(0,1).toUpperCase());
		sb.append(index.getCode().substring(1));
		sb.append("}");

		txt.add(sb.toString());
	}
}