package org.openfuxml.renderer.html;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Table;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.openfuxml.renderer.html.structure.HtmlEmphasisRenderer;
import org.openfuxml.renderer.html.structure.HtmlMarginaliaRenderer;
import org.openfuxml.renderer.html.structure.HtmlParagraphRenderer;
import org.openfuxml.renderer.html.table.HtmlTableRenderer;
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

	/*Das Wurzelelement html rekursiv als List von Java String Objekten zurückgeben.
	 * Hauptsächlich zu Test und Debug Zwecken */
	public List<String> getContent()
	{
		List<String> content = new ArrayList<String>();
		XMLOutputter xmlOutput = new XMLOutputter(ownPrettyFormat());
		content.add(xmlOutput.outputString(html));
		return content;
	}

	public void listRenderer(HtmlElement parent,org.openfuxml.content.list.List list)
	{
	}

	/*Allgemeines verarbeiten von Paragraphen*/
	public void paragraphRenderer(HtmlElement parent,Paragraph paragraph)
	{
		HtmlParagraphRenderer paraRenderer = new HtmlParagraphRenderer(cmm,dsm);
		paraRenderer.render(parent,paragraph);
	}

	/*Allgemeines verarbeiten von Bildern/Grafiken, außerhalb anderer Elemente!
	 *Für inline Gebrauch ggf diese Methode überschreiben.  */
	public void imageRenderer(HtmlElement parent,Image image)
	{
		HtmlImageRenderer imgRenderer = new HtmlImageRenderer(cmm, dsm);
		imgRenderer.render(parent, image);
	}

	/*Allgemeines verarbeiten von Marginalia. (Marginalia = */
	public void marginaliaRenderer(HtmlElement parent,Marginalia marg)
	{
		HtmlMarginaliaRenderer imgRenderer = new HtmlMarginaliaRenderer(cmm, dsm);
		imgRenderer.render(parent, marg);
	}

	/*Allgemeines verarbeiten von Paragraphen*/
	public void renderEmphasis(HtmlElement p, Emphasis em)
	{
		HtmlEmphasisRenderer emph = new HtmlEmphasisRenderer(cmm,dsm);
		emph.render(p, em);
	}

	public void renderTable(HtmlElement p, Table table)
	{
		HtmlTableRenderer emph = new HtmlTableRenderer(cmm,dsm);
		emph.render(p, table);
	}

	/*Dokument in eine Datei schreiben.*/
	public void write(Writer w) throws IOException
	{
		try{
			XMLOutputter xmlOutput = new XMLOutputter(ownPrettyFormat());
			xmlOutput.output(doc, w);
		} catch (IOException io){io.printStackTrace();}
	}

	private static Format ownPrettyFormat()
	{
		Format f = Format.getPrettyFormat();
		f.setTextMode(Format.TextMode.NORMALIZE);
		f.setIndent("\t");
		f.setOmitDeclaration(true);
		return f;
	}

}
