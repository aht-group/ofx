package org.openfuxml.renderer.html.structure;

import java.util.List;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Namespace;
import org.openfuxml.content.ofx.Content;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.head.HtmlHead;
import org.openfuxml.renderer.html.util.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Rebecca Roblick
 */
public class HtmlDocumentRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlDocumentRenderer.class);

	private HtmlElement html; public HtmlElement getHtml() {return html;} public void setHtml(HtmlElement html) {this.html = html;}
	private Document doc; public Document getDoc(){return doc;} public void setDoc(Document doc) {this.doc = doc;}

	private String pageTitle;

	private static HtmlDocumentRenderer instance; public static HtmlDocumentRenderer getInstance() {return instance;}

	public HtmlDocumentRenderer(ConfigurationProvider cp, String pageTitle)
	{
		super(cp);
		this.pageTitle = pageTitle;
		instance = this;
	}

	public void render(Content content) throws OfxAuthoringException, OfxConfigurationException
	{
		this.init();
		HtmlHead head = new	HtmlHead(cp);
		head.render(null, pageTitle);
		HtmlBody body = new HtmlBody(cp);
		body.render(content);
	}

	public void render(Content content, List<String>css) throws OfxAuthoringException, OfxConfigurationException {
		this.init();
		HtmlHead head = new HtmlHead(this.cp);
		head.render(css, this.pageTitle);
		HtmlBody body = new HtmlBody(this.cp);
		body.render(content);
	}
	
	public void init()
	{	/*Initialisieren der html und doc Objekte, sowie Namespace für das html Objekt setzen.*/
		html = new HtmlElement("html");
		doc = new Document(html, new DocType("html"));
		html.setNamespace(Namespace.getNamespace("http://www.w3.org/1999/xhtml"));
	}
}
