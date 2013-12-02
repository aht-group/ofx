package org.openfuxml.renderer.latex.util;

import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.SectionFactory;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.xml.renderer.cmp.Pdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexDocument extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexDocument.class);
	
	private int lvl;
	private LatexPreamble latexPreamble;
	private Pdf pdf;
	
	public LatexDocument(Pdf pdf, LatexPreamble latexPreamble)
	{
		this.latexPreamble=latexPreamble;
		this.pdf=pdf;
	}
	
	public void render(Content content) throws OfxAuthoringException
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
	
	private void renderSection(Section section) throws OfxAuthoringException
	{
		SectionFactory sf = new SectionFactory(lvl+1,latexPreamble);
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
