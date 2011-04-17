package org.openfuxml.renderer.processor.latex.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.data.jaxb.Pdf;
import org.openfuxml.renderer.processor.latex.content.SectionFactory;
import org.openfuxml.renderer.processor.latex.preamble.LatexPreamble;

public class LatexDocument extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(LatexDocument.class);
	
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
