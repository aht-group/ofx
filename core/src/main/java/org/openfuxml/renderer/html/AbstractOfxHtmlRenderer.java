package org.openfuxml.renderer.html;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.openfuxml.renderer.html.structure.HtmlParagraphRenderer;
import org.openfuxml.renderer.markdown.structure.MdSectionRenderer;
import org.openfuxml.xml.renderer.cmp.Html;
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

	protected HtmlElement html;
	protected Document doc;

	public AbstractOfxHtmlRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		this.cmm = cmm;
		this.dsm = dsm;
		html = new HtmlElement("html");
		html.setNamespace(Namespace.getNamespace("http://www.w3.org/1999/xhtml"));
		doc = new Document(html, new DocType("html"));
	}

	public List<String> getContent()
	{
		List<String> content = new ArrayList<String>();
		XMLOutputter xmlOutput = new XMLOutputter(ownPrettyFormat());
		content.add(xmlOutput.outputString(html));
		return content;
	}

	public void listRenderer(org.openfuxml.content.list.List list)
	{
	}
	public void paragraphRenderer(HtmlElement parent,Paragraph paragraph)
	{
		HtmlParagraphRenderer paraRenderer = new HtmlParagraphRenderer(cmm,dsm);
		paraRenderer.render(parent,paragraph);
	}

	public void imageRenderer(HtmlElement parent,Image image)
	{
		HtmlImageRenderer imgRenderer = new HtmlImageRenderer(cmm, dsm);
		imgRenderer.render(parent, image);
	}
	public void write(Writer w) throws IOException
	{
		try{
			XMLOutputter xmlOutput = new XMLOutputter(ownPrettyFormat());
			xmlOutput.output(doc, w);
		} catch (IOException io){io.printStackTrace();}
	}

	private static Format ownPrettyFormat()
	{
		Format f = Format.getCompactFormat();
		f.setTextMode(Format.TextMode.PRESERVE);
		f.setIndent("\t");
		f.setOmitDeclaration(true);
		return f;
	}

}
