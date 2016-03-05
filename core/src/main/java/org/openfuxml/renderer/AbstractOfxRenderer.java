package org.openfuxml.renderer;

import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSectionRenderer.class);
	
	protected CrossMediaManager cmm;
	protected DefaultSettingsManager dsm;
	
	public AbstractOfxRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		this.cmm=cmm;
		this.dsm=dsm;
	}
}