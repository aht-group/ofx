package org.openfuxml.renderer.markdown;

import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.markdown.structure.MdListRenderer;
import org.openfuxml.renderer.markdown.structure.MdSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class AbstractOfxMarkdownRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdSectionRenderer.class);

	protected CrossMediaManager cmm; // implementing, rendering images
	protected DefaultSettingsManager dsm; // config e.g. line separator

	protected List<String> txt;
	protected ArrayList<OfxMdRenderer> renderer;

	public AbstractOfxMarkdownRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		this.cmm = cmm;
		this.dsm = dsm;
		renderer = new ArrayList<OfxMdRenderer>();
		txt = new ArrayList<String>();
	}

	public List<String> getContent()
	{
		List<String> resultTxt = new ArrayList<String>();

		resultTxt.addAll(txt);
		for(OfxMdRenderer r : renderer)
		{
			resultTxt.addAll(r.getContent());
		}
		return resultTxt;
	}

	public void listRenderer(org.openfuxml.content.list.List list, OfxMdRenderer parent)
	{
		MdListRenderer listR = new MdListRenderer(cmm,dsm);
		listR.render(list, parent);
		renderer.add(listR);
	}
	public void write(Writer w) throws IOException
	{
		for(String s : getContent())
		{
			w.write(s+dsm.lineSeparator());
		}
		w.flush();
	}
}
