package org.openfuxml.renderer.word;

import java.io.File;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.util.OfxDocumentStructureVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxWordRenderer extends AbstractOfxWordRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxWordRenderer.class);
	
	public OfxWordRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
		
	public void render(Document document, File target) throws OfxAuthoringException
	{
		OfxDocumentStructureVerifier.checkForContent(document);
	}
}