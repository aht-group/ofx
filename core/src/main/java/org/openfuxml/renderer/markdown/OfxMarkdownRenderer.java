package org.openfuxml.renderer.markdown;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.ConfigurationProvider;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.OfxRenderer;
import org.openfuxml.renderer.markdown.structure.MdDocumentRenderer;
import org.openfuxml.renderer.util.OfxDocumentStructureVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OfxMarkdownRenderer extends OfxRenderer
{
	private List<String> txt;
	private MdDocumentRenderer rDocument;

	@Deprecated
	public OfxMarkdownRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		logger = LoggerFactory.getLogger(OfxMarkdownRenderer.class);
		txt = new ArrayList<String>();
		rDocument = new MdDocumentRenderer(cmm, dsm);
	}

	public OfxMarkdownRenderer(ConfigurationProvider cp) {
		logger = LoggerFactory.getLogger(OfxMarkdownRenderer.class);
		txt = new ArrayList<String>();
		rDocument = new MdDocumentRenderer(cp);
	}

	public List<String> getContent(){return txt;}

	public void render(Document ofxDocument) throws OfxAuthoringException, OfxConfigurationException
	{
		OfxDocumentStructureVerifier.checkForContent(ofxDocument);
		rDocument.render(ofxDocument.getContent());
		txt.addAll(rDocument.getContent());
	}}
