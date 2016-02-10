package org.openfuxml.renderer.markdown;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.markdown.media.MdImageRenderer;
import org.openfuxml.renderer.markdown.structure.MdListRenderer;
import org.openfuxml.renderer.markdown.structure.MdParagraphRenderer;
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

	protected CrossMediaManager cmm;
	protected DefaultSettingsManager dsm;

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

	public void listRenderer(org.openfuxml.content.list.List list)
	{
		MdListRenderer listR = new MdListRenderer(cmm,dsm);
		listR.render(list);
		renderer.add(listR);
	}
	public void paragraphRenderer(Paragraph paragraph)
	{
		MdParagraphRenderer paraR = new MdParagraphRenderer(cmm,dsm);
		paraR.render(paragraph);
		renderer.add(paraR);
	}

	public void imageRenderer(Image image)
	{
		MdImageRenderer imageRenderer = new MdImageRenderer(cmm,dsm);
		imageRenderer.render(image);
		renderer.add(imageRenderer);
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
