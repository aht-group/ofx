package org.openfuxml.renderer.processor.latex.util;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.content.ParagraphFactory;
import org.openfuxml.renderer.processor.latex.content.SectionFactory;
import org.openfuxml.renderer.processor.latex.content.list.ListFactory;

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
		txt.add("\\usepackage{paralist}");
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
	
	public void write(Writer w) throws IOException
	{
		for(String s : getContent())
		{
			w.write(s+SystemUtils.LINE_SEPARATOR);
		}
		w.flush();
	}
	
	protected void paragraphRenderer(Paragraph paragraph, boolean preBlankLine)
	{
		ParagraphFactory f = new ParagraphFactory(preBlankLine);
		f.render(paragraph);
		renderer.add(f);
	}
	
	protected void renderList(org.openfuxml.xml.content.list.List list,OfxLatexRenderer parent) throws OfxAuthoringException
	{
		ListFactory f = new ListFactory();
		f.render(list,parent);
		renderer.add(f);
	}
}
