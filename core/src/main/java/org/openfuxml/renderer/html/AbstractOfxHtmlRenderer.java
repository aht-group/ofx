package org.openfuxml.renderer.html;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Table;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.content.text.Symbol;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.AbstractOfxRenderer;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.openfuxml.renderer.html.structure.HtmlCommentRenderer;
import org.openfuxml.renderer.html.structure.HtmlEmphasisRenderer;
import org.openfuxml.renderer.html.structure.HtmlListRenderer;
import org.openfuxml.renderer.html.structure.HtmlMarginaliaRenderer;
import org.openfuxml.renderer.html.structure.HtmlParagraphRenderer;
import org.openfuxml.renderer.html.table.HtmlTableRenderer;
import org.openfuxml.renderer.markdown.structure.MdSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxHtmlRenderer extends AbstractOfxRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdSectionRenderer.class);

	protected static HtmlElement html = new HtmlElement("html");
	protected static Document doc = new Document(html, new DocType("html"));
	
	@Deprecated
	public AbstractOfxHtmlRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		this(ConfigurationProviderFacotry.build(cmm,dsm));
	}
	
	public AbstractOfxHtmlRenderer(ConfigurationProvider cp)
	{
		super(cp);
		html.setNamespace(Namespace.getNamespace("http://www.w3.org/1999/xhtml"));
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
		HtmlListRenderer listRenderer = new HtmlListRenderer(cp);
		listRenderer.render(parent, list);
	}

	/*Allgemeines verarbeiten von Paragraphen*/
	public void paragraphRenderer(HtmlElement parent,Paragraph paragraph)
	{
		HtmlParagraphRenderer paraRenderer = new HtmlParagraphRenderer(cp);
		paraRenderer.render(parent,paragraph);
	}

	/*Für den Fall, dass in der XML Vorlage ein Paragraph Element vorhanden ist,
	* dieses in HTML aber nicht möglich ist. (z.B. HTML erlaubt keine verschachtelten <p> Elemente)*/
	public void paragraphContentRenderer(HtmlElement parent, Paragraph p)
	{
		HtmlParagraphRenderer paraRenderer = new HtmlParagraphRenderer(cp);
		paraRenderer.renderWithout(parent,p);
	}

	/*Mit Symbol sind Icons der Font Awesome gemeint. Die Dazugehörige .css muss im Head als <link> angegeben werden,
	* ansonsten wird ein Browser sie nicht darstellen.*/
	public void renderSymbol(HtmlElement parent, Symbol symbol)
	{
			HtmlElement sym = new HtmlElement("i");
			java.util.List<Attribute> attr = new ArrayList<Attribute>();
			attr.add(new Attribute("class", symbol.getCode()));
			attr.add(new Attribute("style", "float: left;font-size:4em;"));
			sym.setAttributes(attr);
			parent.addContent(sym);
	}

	/*Allgemeines verarbeiten von Bildern/Grafiken, außerhalb anderer Elemente!
	 *Für inline Gebrauch ggf diese Methode überschreiben.  */
	public void imageRenderer(HtmlElement parent,Image image)
	{
		HtmlImageRenderer imgRenderer = new HtmlImageRenderer(cp);
		imgRenderer.render(parent, image);
	}

	/*Allgemeines verarbeiten von Marginalia. (Marginalia = */
	public void marginaliaRenderer(HtmlElement parent,Marginalia marg)
	{
		HtmlMarginaliaRenderer imgRenderer = new HtmlMarginaliaRenderer(cp);
		imgRenderer.render(parent, marg);
	}

	/*Allgemeines verarbeiten von Paragraphen*/
	public void renderEmphasis(HtmlElement p, Emphasis em)
	{
		HtmlEmphasisRenderer emph = new HtmlEmphasisRenderer(cp);
		emph.render(p, em);
	}

	public void renderTable(HtmlElement p, Table table)
	{
		HtmlTableRenderer emph = new HtmlTableRenderer(cp);
		emph.render(p, table);
	}

	public void commentRenderer(HtmlElement parent, org.openfuxml.content.ofx.Comment comment)
	{
		HtmlCommentRenderer commentRenderer = new HtmlCommentRenderer(cp);
		commentRenderer.render(parent, comment);
	}

	/*Dokument in eine Datei schreiben.*/
	public void write(Writer w) throws IOException
	{
		try{
			XMLOutputter xmlOutput = new XMLOutputter(ownPrettyFormat().setLineSeparator(dsm.lineSeparator()));
			xmlOutput.output(doc, w);
		} catch (IOException io){io.printStackTrace();}
	}

	private static Format ownPrettyFormat()
	{
		Format f = Format.getPrettyFormat();
//		f.setTextMode(Format.TextMode.PRESERVE);
		f.setTextMode(Format.TextMode.TRIM);
		f.setIndent("\t");
		f.setOmitDeclaration(true);
		return f;
	}

}
