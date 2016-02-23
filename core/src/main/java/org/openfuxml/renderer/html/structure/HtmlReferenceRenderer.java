package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.ofx.Reference;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlReferenceRenderer extends AbstractOfxHtmlRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlReferenceRenderer.class);


	public HtmlReferenceRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	/*Referenzen innerhalb des Dokumentes.
	Das referenzierte Objekt benötigt eine id!
	*/
	public void renderIntern(HtmlElement parent, Reference reference,/*@NotNull*/ String text)
	{
		HtmlElement ref = new HtmlElement("a");
		String target = "#" + reference.getTarget();
		ref.setAttribute("href",target);
		ref.addContent(text);
		parent.addContent(ref);
	}

	/*Referenzen/Links zu anderen Webseiten usw.*/
	public void renderExtern(HtmlElement parent, String link, String text)
	{
		HtmlElement ref = new HtmlElement("a");
		ref.setAttribute("href",link);
		ref.addContent(text);
		parent.addContent(ref);
	}
}
