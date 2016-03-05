package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.ConfigurationProvider;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.openfuxml.xml.renderer.cmp.Pdf;
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
