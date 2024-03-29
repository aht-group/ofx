package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxMdRenderer;
import org.openfuxml.model.xml.core.ofx.Content;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdDocumentRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdDocumentRenderer.class);

	private int lvl;
	@Deprecated
	public MdDocumentRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
		this.cmm=cmm;
	}

	public MdDocumentRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(Content content) throws OfxAuthoringException, OfxConfigurationException
	{
		lvl = 0;
		
		for(Object s : content.getContent())
		{
			if(s instanceof Section){renderSection((Section)s);}
		}
	}
	
	private void renderSection(Section section) throws OfxAuthoringException, OfxConfigurationException
	{
		MdSectionRenderer sf = new MdSectionRenderer(cp,lvl+1);
		sf.render(section);
		renderer.add(sf);
	}
}
