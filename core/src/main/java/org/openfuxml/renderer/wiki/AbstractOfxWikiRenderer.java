package org.openfuxml.renderer.wiki;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.AbstractOfxRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxWikiRenderer extends AbstractOfxRenderer
{

	final static Logger logger = LoggerFactory.getLogger(WikiParagraphRenderer.class);
	
	protected List<String> txt;
	
	protected List<OfxWikiRenderer> renderer;
	
	@Deprecated
	public AbstractOfxWikiRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		this(ConfigurationProviderFacotry.build(cmm,dsm));
	}
	
	public AbstractOfxWikiRenderer(ConfigurationProvider cp)
	{
		super(cp);
		txt = new ArrayList<String>();
		renderer = new ArrayList<OfxWikiRenderer>();
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
		
		resultTxt.addAll(txt);
		for(OfxWikiRenderer r : renderer)
		{
			resultTxt.addAll(r.getContent());
		}
		
		
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
	
	public void paragraphRenderer(Paragraph paragraph) throws OfxAuthoringException
	{
		WikiParagraphRenderer wpr = new WikiParagraphRenderer(cmm,dsm);
		wpr.render(paragraph);
		renderer.add(wpr);
	}

}
