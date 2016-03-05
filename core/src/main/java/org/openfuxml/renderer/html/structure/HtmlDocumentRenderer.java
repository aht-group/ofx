package org.openfuxml.renderer.html.structure;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.ConfigurationProvider;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.html.head.HtmlHead;
import org.openfuxml.renderer.markdown.structure.MdSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlDocumentRenderer extends AbstractOfxHtmlRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlDocumentRenderer.class);

	private String pageTitle;

	@Deprecated
	public HtmlDocumentRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm, String pageTitle)
	{
		super(cmm,dsm);
		this.cmm=cmm;
		html = new HtmlElement("html");
		doc = new Document(html, new DocType("html"));
		this.pageTitle = pageTitle;
	}

	public HtmlDocumentRenderer(ConfigurationProvider cp, String pageTitle) {
		super(cp);
		html = new HtmlElement("html");
		doc = new Document(html, new DocType("html"));
		this.pageTitle = pageTitle;
	}

	public void render(Content content) throws OfxAuthoringException, OfxConfigurationException
	{
		HtmlHead head = new	HtmlHead(cp);
		head.render(null, pageTitle);
		HtmlBody body = new HtmlBody(cp);
		body.render(content);
	}
	
//	private void renderBody(Content content) throws OfxAuthoringException, OfxConfigurationException
//	{
//		HtmlBody body = new HtmlBody(cmm,dsm);
//		body.render(content);
//	}
}
