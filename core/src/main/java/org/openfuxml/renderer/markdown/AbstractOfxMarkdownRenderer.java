package org.openfuxml.renderer.markdown;

import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.markdown.structure.MdSectionRenderer;
import org.openfuxml.interfaces.renderer.markdown.OfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class AbstractOfxMarkdownRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdSectionRenderer.class);

	protected CrossMediaManager cmm; // implementing, rendering images
	protected DefaultSettingsManager dsm; // config e.g. line separator

	protected List<String> txt;
	protected ArrayList<OfxMarkdownRenderer> renderer;

	public AbstractOfxMarkdownRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		this.cmm = cmm;
		this.dsm = dsm;
		renderer = new ArrayList<OfxMarkdownRenderer>();
		txt = new ArrayList<String>();
	}

	public List<String> getContent()
	{
		List<String> resultTxt = new ArrayList<String>();

		resultTxt.addAll(txt);
		for(OfxMarkdownRenderer r : renderer)
		{
			resultTxt.addAll(r.getContent());
		}
		return resultTxt;
	}
}
