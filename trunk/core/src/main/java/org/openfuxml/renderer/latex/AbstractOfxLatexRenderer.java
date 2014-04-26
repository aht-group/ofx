package org.openfuxml.renderer.latex;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.content.list.LatexListRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSectionRenderer.class);
	
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
	
	protected void addPackages9()
	{
		
	}
	
	public List<String> getContent()
	{
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
	
	protected void paragraphRenderer(Paragraph paragraph, boolean preBlankLine) throws OfxAuthoringException
	{
		LatexParagraphRenderer f = new LatexParagraphRenderer(preBlankLine);
		f.render(paragraph);
		renderer.add(f);
	}
	
	protected void renderList(org.openfuxml.content.list.List list,OfxLatexRenderer parent) throws OfxAuthoringException
	{
		LatexListRenderer f = new LatexListRenderer();
		f.render(list,parent);
		renderer.add(f);
	}

    protected void addString(String s)
    {
        txt.add(TexSpecialChars.replace(s));
    }
}
