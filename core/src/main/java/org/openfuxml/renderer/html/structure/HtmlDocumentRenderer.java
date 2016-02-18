package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.head.HtmlHead;
import org.openfuxml.renderer.markdown.structure.MdSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlDocumentRenderer extends AbstractOfxHtmlRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlDocumentRenderer.class);

	private String pageTitle;

	public HtmlDocumentRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm, String pageTitle)
	{
		super(cmm,dsm);
		this.cmm=cmm;
		this.pageTitle = pageTitle;
	}
	
	public void render(Content content) throws OfxAuthoringException, OfxConfigurationException
	{
		HtmlHead head = new	HtmlHead(cmm,dsm);
		head.render(null, pageTitle);
//		System.out.println("Doc 1 Html content size: " + html.getContent().size());
		renderBody(content);
	}
	
	private void renderBody(Content content) throws OfxAuthoringException, OfxConfigurationException
	{
		HtmlBody body = new HtmlBody(cmm,dsm);
//		System.out.println("Doc 2 Html content size: " + html.getContent().size());
		body.render(content);
	}
}
