package org.openfuxml.renderer.word;

import java.io.File;


import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.util.OfxDocumentStructureVerifier;
import org.openfuxml.renderer.word.structure.WordDocumentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;

public class OfxWordRenderer extends AbstractOfxWordRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxWordRenderer.class);
	
	public OfxWordRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
		
	public void render(org.openfuxml.model.xml.core.ofx.Document ofxDocument, File target) throws Exception
	{
		OfxDocumentStructureVerifier.checkForContent(ofxDocument);
		WordDocumentRenderer wDR = new WordDocumentRenderer();
		
		Document doc = wDR.render(ofxDocument.getContent());
		doc.save(target.getAbsolutePath());
	}	
}
