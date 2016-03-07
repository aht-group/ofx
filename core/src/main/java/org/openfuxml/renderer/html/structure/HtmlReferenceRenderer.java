package org.openfuxml.renderer.html.structure;

/**
 * Author: Rebecca Roblick
 */

import org.openfuxml.content.ofx.Reference;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.openfuxml.renderer.html.table.HtmlTableRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlReferenceRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlReferenceRenderer.class);

	@Deprecated
	public HtmlReferenceRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}

	public HtmlReferenceRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	/*Referenzen innerhalb des Dokumentes.
		Das referenzierte Objekt benötigt eine id!
		*/
	public void renderIntern(HtmlElement parent, Reference reference)
	{
		HtmlElement ref = new HtmlElement("a");
		String target = "#" + reference.getTarget();
		ref.setAttribute("href",target);
		ref.addContent(HtmlSectionRenderer.sectionCount + numbering(reference.getTarget()));
		parent.addContent(ref);
	}

	/*Referenzen/Links zu anderen Webseiten */
	public void renderExtern(HtmlElement parent, String link, String text)
	{
		HtmlElement ref = new HtmlElement("a");
		ref.setAttribute("href",link);
		ref.addContent(text);
		parent.addContent(ref);
	}

	private String numbering(String target)
	{
		String nr = ".";
		switch (target.charAt(0)){
			case 't': nr+= HtmlTableRenderer.tblcount; break;
			case 'i': nr+= HtmlImageRenderer.imgcount; break;
		}
		return nr;
	}
}
