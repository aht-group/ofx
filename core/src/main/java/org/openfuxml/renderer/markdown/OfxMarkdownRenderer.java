package org.openfuxml.renderer.markdown;

import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OfxMarkdownRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxMarkdownRenderer.class);

	private List<String> txt;

	public OfxMarkdownRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		txt = new ArrayList<String>();
	}

	public List<String> getContent(){return txt;}
}
