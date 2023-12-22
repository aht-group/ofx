package org.openfuxml.renderer.latex;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openfuxml.content.layout.Column;
import org.openfuxml.content.layout.Container;
import org.openfuxml.content.layout.Width;
import org.openfuxml.content.ofx.Highlight;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.model.xml.core.media.Image;
import org.openfuxml.renderer.AbstractOfxRenderer;
import org.openfuxml.renderer.latex.content.layout.LatexColumnRenderer;
import org.openfuxml.renderer.latex.content.layout.LatexContainerRenderer;
import org.openfuxml.renderer.latex.content.layout.LatexWidthRenderer;
import org.openfuxml.renderer.latex.content.list.LatexListRenderer;
import org.openfuxml.renderer.latex.content.media.LatexImageRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexHighlightRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexMarginaliaRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxLatexRenderer extends AbstractOfxRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSectionRenderer.class);
	
	protected List<String> preTxt;
	protected List<String> txt;
	protected List<String> postTxt;
	
	protected List<OfxLatexRenderer> renderer; //listing different renderer e.g. Section, Title, List; nacheinander abgearbeitet
	
	public AbstractOfxLatexRenderer(ConfigurationProvider cp)
	{
		super(cp);
		preTxt = new ArrayList<String>();
		txt = new ArrayList<String>();
		postTxt = new ArrayList<String>();
		renderer = new ArrayList<OfxLatexRenderer>();
	}
	
	protected void addPackages()
	{
		
	}
	
	public String getSingleLine() throws OfxAuthoringException
	{
		List<String> resultTxt = getContent();
		if(resultTxt.size()!=1){throw new OfxAuthoringException("Result is not a single line");}
		return resultTxt.get(0);
	}
	
	public String getContentAsSingleLine(String delimiter)
	{
		return StringUtils.join(getContent(), delimiter);
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
			w.write(s+dsm.lineSeparator());
		}
		w.flush();
	}
	
	protected void paragraphRenderer(Paragraph paragraph, boolean preBlankLine) throws OfxAuthoringException
	{
		LatexParagraphRenderer f = new LatexParagraphRenderer(cp,preBlankLine);
		f.render(paragraph);
		renderer.add(f);
	}
	
	protected void containerRenderer(Container container) throws OfxAuthoringException
	{
		LatexContainerRenderer f = new LatexContainerRenderer(cp);
		f.render(container);
		renderer.add(f);
	}
	
	protected void columnRenderer(Column column) throws OfxAuthoringException
	{
		LatexColumnRenderer f = new LatexColumnRenderer(cp);
		f.render(this,column);
		renderer.add(f);
	}
	
	protected void widthRenderer(Width width) throws OfxAuthoringException
	{
		LatexWidthRenderer f = new LatexWidthRenderer(cp);
		f.render(this,width);
		renderer.add(f);
	}
	
	protected void highlightRenderer(Highlight highlight) throws OfxAuthoringException
	{
		LatexHighlightRenderer f = new LatexHighlightRenderer(cp);
		f.render(highlight);
		renderer.add(f);
	}
	
	protected void renderList(org.openfuxml.model.xml.core.list.List list,OfxLatexRenderer parent) throws OfxAuthoringException
	{
		LatexListRenderer f = new LatexListRenderer(cp);
		f.render(list,parent);
		renderer.add(f);
	}
	
	protected void renderImage(Image image) throws OfxAuthoringException
	{
		LatexImageRenderer f = new LatexImageRenderer(cp);
		f.render(this,image);
		renderer.add(f);
	}
	
	protected void renderMarginalia(Marginalia marginalia) throws OfxAuthoringException
	{
		logger.trace("Rendering Marginalia");
		LatexMarginaliaRenderer r = new LatexMarginaliaRenderer(cp);
		r.render(marginalia);
		renderer.add(r);
	}

    protected void addString(String s)
    {
        txt.add(TexSpecialChars.replace(s));
    }
}
