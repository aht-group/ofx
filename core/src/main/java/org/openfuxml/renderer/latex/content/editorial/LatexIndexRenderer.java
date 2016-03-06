package org.openfuxml.renderer.latex.content.editorial;

import org.openfuxml.content.editorial.Index;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexIndexRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexIndexRenderer.class);

	public LatexIndexRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
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