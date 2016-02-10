package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.openfuxml.xml.renderer.cmp.Pdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdDocumentRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdDocumentRenderer.class);

	private int lvl;
//	private LatexPreamble latexPreamble;
	private Pdf pdf;

	public MdDocumentRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
		this.cmm=cmm;
//		this.pdf=pdf;
//		this.latexPreamble=latexPreamble;
	}
	
	public void render(Content content) throws OfxAuthoringException, OfxConfigurationException
	{
		lvl = 0;
		
//		preTxt.add("\\begin{document}");
//		renderToc();
		
		
		for(Object s : content.getContent())
		{
			if(s instanceof Section){renderSection((Section)s);}
		}
//		postTxt.add("\\end{document}");
	}
	
	private void renderSection(Section section) throws OfxAuthoringException, OfxConfigurationException
	{
		MdSectionRenderer sf = new MdSectionRenderer(cmm,dsm,lvl+1);
		sf.render(section);
		renderer.add(sf);
	}
	
//	private void renderToc()
//	{
//		if(pdf.isSetToc() && pdf.getToc().isPrint())
//		{
//			preTxt.add("\\tableofcontents");
//		}
//	}
}
