package org.openfuxml.renderer.html;
/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.OfxRenderer;
import org.openfuxml.renderer.html.structure.HtmlDocumentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OfxHTMLRenderer extends OfxRenderer
{
	Logger logger = LoggerFactory.getLogger(OfxHTMLRenderer.class);

	private List<String> txt;
	private HtmlDocumentRenderer document;

	@Deprecated
	public OfxHTMLRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		txt = new ArrayList<String>();
		logger = LoggerFactory.getLogger(OfxHTMLRenderer.class);
	}

	public OfxHTMLRenderer(ConfigurationProvider cp, String pageTitle) {
		txt = new ArrayList<String>();
		logger = LoggerFactory.getLogger(OfxHTMLRenderer.class);
		document = new HtmlDocumentRenderer(cp, pageTitle);
	}

	public List<String> getContent(){return txt;}

	@Override
	public void render(Document ofxDocument) throws OfxAuthoringException, OfxConfigurationException
	{

	}
}
