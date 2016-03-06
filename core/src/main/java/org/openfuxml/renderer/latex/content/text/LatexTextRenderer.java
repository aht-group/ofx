package org.openfuxml.renderer.latex.content.text;

import java.util.List;

import org.openfuxml.content.text.Text;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTextRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTextRenderer.class);
	
	public LatexTextRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Text text) throws OfxAuthoringException
	{	
		txt.add(text.getValue());
	}
	public void render(List<Text> texts) throws OfxAuthoringException
	{	
		for(Text text : texts)
		{
			txt.add(TexSpecialChars.replace(text.getValue()));
		}
		
	}
}
