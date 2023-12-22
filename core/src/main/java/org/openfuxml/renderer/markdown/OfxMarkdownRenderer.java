package org.openfuxml.renderer.markdown;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.renderer.OfxRenderer;
import org.openfuxml.renderer.markdown.structure.MdDocumentRenderer;
import org.openfuxml.renderer.util.OfxDocumentStructureVerifier;
import org.slf4j.LoggerFactory;

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
	}
}