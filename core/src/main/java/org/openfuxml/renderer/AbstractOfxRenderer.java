package org.openfuxml.renderer;

import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.interfaces.ConfigurationProvider;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSectionRenderer.class);
	
	protected ConfigurationProvider cp;
	
	protected CrossMediaManager cmm;
	protected DefaultSettingsManager dsm;
	
	public AbstractOfxRenderer(ConfigurationProvider cp)
	{
		this.cp=cp;
	}
	
	@Deprecated //Use ConfigurationProvider
	public AbstractOfxRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		cp = ConfigurationProviderFacotry.build(cmm,dsm);
		this.cmm=cmm;
		this.dsm=dsm;
	}
}