package org.openfuxml.renderer.text;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxTextRenderer;
import org.openfuxml.renderer.AbstractOfxRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.text.structure.TextParagraphRenderer;
import org.openfuxml.renderer.text.structure.TextSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxTextRenderer extends AbstractOfxRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSectionRenderer.class);
	
	protected List<String> preTxt;
	protected List<String> txt;
	protected List<String> postTxt;
	
	protected List<OfxTextRenderer> renderer;
	
//	@Deprecated
//	public AbstractOfxTextRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
//	{
//		this(ConfigurationProviderFacotry.build(cmm,dsm));
//	}
	
	public AbstractOfxTextRenderer(ConfigurationProvider cp)
	{
		super(cp);
		preTxt = new ArrayList<String>();
		txt = new ArrayList<String>();
		postTxt = new ArrayList<String>();
		renderer = new ArrayList<OfxTextRenderer>();
	}
	
	public String getSingleLine() throws OfxAuthoringException
	{
		List<String> resultTxt = getContent();
		if(resultTxt.size()!=1){throw new OfxAuthoringException("Result is not a single line");}
		return resultTxt.get(0);
	}
	
	public List<String> getContent()
	{
		List<String> resultTxt = new ArrayList<String>();
		resultTxt.addAll(preTxt);
		
		resultTxt.addAll(txt);
		for(OfxTextRenderer r : renderer)
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
	
	private void renderSection(Section section) throws OfxAuthoringException, OfxConfigurationException
	{
		TextSectionRenderer sf = new TextSectionRenderer(cp);
		sf.render(section);
		renderer.add(sf);
	}
	
	protected void paragraphRenderer(Paragraph paragraph) throws OfxAuthoringException
	{
		TextParagraphRenderer f = new TextParagraphRenderer(cp,false);
		f.render(paragraph);
		renderer.add(f);
	}
	
    protected void addString(String s)
    {
        txt.add(s);
    }
}
