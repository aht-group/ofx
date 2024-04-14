package org.openfuxml.renderer.docx.aspose.util;

import java.io.File;

import org.openfuxml.renderer.docx.aspose.structure.WordDocumentRenderer;
import org.openfuxml.renderer.util.OfxDocumentStructureVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;

public class OfxAsposeDocxRenderer 
{
	final static Logger logger = LoggerFactory.getLogger(OfxAsposeDocxRenderer.class);
	
	private DocumentBuilder builder; public OfxAsposeDocxRenderer builder(DocumentBuilder builder) {this.builder=builder; return this;}
	
	public static OfxAsposeDocxRenderer instance() {return new OfxAsposeDocxRenderer();}
	private OfxAsposeDocxRenderer()
	{
		
	}
		
	public void render(org.openfuxml.model.xml.core.ofx.Document ofxDocument, File target) throws Exception
	{
		OfxDocumentStructureVerifier.checkForContent(ofxDocument);
		WordDocumentRenderer wDR = new WordDocumentRenderer();
		
		Document doc = wDR.render(ofxDocument.getContent());
		doc.save(target.getAbsolutePath());
	}	
}
