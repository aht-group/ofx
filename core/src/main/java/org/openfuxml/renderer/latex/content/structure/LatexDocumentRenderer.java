package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.model.xml.core.ofx.Content;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.openfuxml.xml.renderer.cmp.Pdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexDocumentRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexDocumentRenderer.class);
	
	private int lvl;
	private LatexPreamble latexPreamble;
	private Pdf pdf;
	
	@Deprecated
	public LatexDocumentRenderer(Pdf pdf, LatexPreamble latexPreamble)
	{
		this(ConfigurationProviderFacotry.build(new NoOpCrossMediaManager(),new OfxDefaultSettingsManager()),pdf,latexPreamble);
	}
	
	public LatexDocumentRenderer(ConfigurationProvider cp, Pdf pdf, LatexPreamble latexPreamble)
	{
		super(cp);
		this.pdf=pdf;
		this.latexPreamble=latexPreamble;
	}
	
	public void render(Content content) throws OfxAuthoringException, OfxConfigurationException
	{
		lvl = 0;
		
		preTxt.add("\\begin{document}");
		renderToc();
		
		for(Object s : content.getContent())
		{
			if(s instanceof Section){renderSection((Section)s);}
		}
		postTxt.add("\\end{document}");
	}
	
	private void renderSection(Section section) throws OfxAuthoringException, OfxConfigurationException
	{
		LatexSectionRenderer sf = new LatexSectionRenderer(cp,lvl+1,latexPreamble);
		sf.render(section);
		renderer.add(sf);
	}
	
	private void renderToc()
	{
		if(pdf.isSetToc() && pdf.getToc().isPrint())
		{
			preTxt.add("\\tableofcontents");
		}
	}
}
