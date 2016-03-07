package org.openfuxml.renderer.html.structure;
/**
 * Author: Rebecca Roblick
 */
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Namespace;
import org.openfuxml.content.ofx.Content;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.html.head.HtmlHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlDocumentRenderer extends AbstractOfxHtmlRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlDocumentRenderer.class);
	public static HtmlElement html;
	public static Document doc;
	private String pageTitle;

	@Deprecated
	public HtmlDocumentRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm, String pageTitle)
	{
		super(cmm,dsm);
		this.cmm=cmm;
		init();
		this.pageTitle = pageTitle;
	}

	public HtmlDocumentRenderer(ConfigurationProvider cp, String pageTitle) {
		super(cp);

		this.pageTitle = pageTitle;
	}

	public void render(Content content) throws OfxAuthoringException, OfxConfigurationException
	{
		init();
		HtmlHead head = new	HtmlHead(cp);
		head.render(null, pageTitle);
		HtmlBody body = new HtmlBody(cp);
		body.render(content);
	}

	public void init()
	{
		html = new HtmlElement("html");
		doc = new Document(html, new DocType("html"));
		html.setNamespace(Namespace.getNamespace("http://www.w3.org/1999/xhtml"));
	}

//	private void renderBody(Content content) throws OfxAuthoringException, OfxConfigurationException
//	{
//		HtmlBody body = new HtmlBody(cmm,dsm);
//		body.render(content);
//	}
}
