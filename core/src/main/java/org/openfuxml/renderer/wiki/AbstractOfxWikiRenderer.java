package org.openfuxml.renderer.wiki;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openfuxml.content.layout.Column;
import org.openfuxml.content.layout.Container;
import org.openfuxml.content.layout.Width;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Highlight;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.interfaces.ConfigurationProvider;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.AbstractOfxRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxWikiRenderer extends AbstractOfxRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WikiParagraphRenderer.class);
	
	protected List<String> preTxt;
	protected List<String> txt;
	protected List<String> postTxt;
	
	protected List<OfxWikiRenderer> renderer;
	
	@Deprecated
	public AbstractOfxWikiRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		this(ConfigurationProviderFacotry.build(cmm,dsm));
	}
	
	public AbstractOfxWikiRenderer(ConfigurationProvider cp)
	{
		super(cp);
		preTxt = new ArrayList<String>();
		txt = new ArrayList<String>();
		postTxt = new ArrayList<String>();
		renderer = new ArrayList<OfxWikiRenderer>();
	}
	
	protected void addPackages9()
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
		for(OfxWikiRenderer r : renderer)
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
		
	}
	
	protected void containerRenderer(Container container) throws OfxAuthoringException
	{
		
	}
	
	protected void columnRenderer(Column column) throws OfxAuthoringException
	{
		
	}
	
	protected void widthRenderer(Width width) throws OfxAuthoringException
	{
		
	}
	
	protected void highlightRenderer(Highlight highlight) throws OfxAuthoringException
	{
		
	}
	
	protected void renderList(org.openfuxml.content.list.List list,OfxWikiRenderer parent) throws OfxAuthoringException
	{
		
	}
	
	protected void renderImage(Image image) throws OfxAuthoringException
	{
		
	}
	
	protected void renderMarginalia(Marginalia marginalia) throws OfxAuthoringException
	{
		
	}

    protected void addString(String s)
    {
       
    }
}