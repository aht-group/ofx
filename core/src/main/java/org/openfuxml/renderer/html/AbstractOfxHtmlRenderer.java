package org.openfuxml.renderer.html;

import org.jdom2.Element;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.renderer.markdown.structure.MdSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class AbstractOfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdSectionRenderer.class);

	protected CrossMediaManager cmm;
	protected DefaultSettingsManager dsm;

	protected List<Element> txt;
	protected ArrayList<OfxHtmlRenderer> renderer;

	public AbstractOfxHtmlRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		this.cmm = cmm;
		this.dsm = dsm;
		renderer = new ArrayList<OfxHtmlRenderer>();
		txt = new ArrayList<Element>();
	}

	public List<String> getContent()
	{
		List<String> resultTxt = new ArrayList<String>();
		for(Element e : txt){resultTxt.add(e.toString());}
		for(OfxHtmlRenderer r : renderer){resultTxt.addAll(r.getContent());}
		return resultTxt;
	}

	public void listRenderer(org.openfuxml.content.list.List list)
	{
	}
	public void paragraphRenderer(Paragraph paragraph)
	{
	}

	public void imageRenderer(Image image)
	{
	}
	public void write(Writer w) throws IOException
	{
		for(String s : getContent()){w.write(s+dsm.lineSeparator());}
		w.flush();
	}
}
