package org.openfuxml.renderer.processor.latex.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.renderer.processor.latex.content.ParagraphFactory;
import org.openfuxml.renderer.processor.latex.content.SectionFactory;

public class AbstractOfxLatexRenderer
{
	static Log logger = LogFactory.getLog(SectionFactory.class);
	
	protected List<String> preTxt;
	protected List<String> txt;
	protected List<String> postTxt;
	
	protected List<OfxLatexRenderer> renderer;
	
	public AbstractOfxLatexRenderer()
	{
		preTxt = new ArrayList<String>();
		txt = new ArrayList<String>();
		postTxt = new ArrayList<String>();
		renderer = new ArrayList<OfxLatexRenderer>();
	}
	
	protected void addPackages()
	{
		txt.add("");
		txt.add("%% Packages");
		txt.add("\\usepackage{amsmath}");
		txt.add("\\usepackage{hyperref}");
		txt.add("\\usepackage{array}");
		txt.add("\\usepackage{ifthen}");
		txt.add("");
	}
	
	public List<String> getContent()
	{
		if(txt.size()!=0 && renderer.size()!=0){logger.warn("txt and renderer have content!!");}
		List<String> resultTxt = new ArrayList<String>();
		resultTxt.addAll(preTxt);
		
		resultTxt.addAll(txt);
		for(OfxLatexRenderer r : renderer)
		{
			resultTxt.addAll(r.getContent());
		}
		
		resultTxt.addAll(postTxt);
		
		return resultTxt;
	}
	
	protected void paragraphRenderer(Paragraph paragraph)
	{
		ParagraphFactory f = new ParagraphFactory();
		f.render(paragraph);
		renderer.add(f);
	}
}
