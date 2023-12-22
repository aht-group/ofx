package org.openfuxml.renderer.latex;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.renderer.OfxRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexDocumentRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.util.OfxDocumentStructureVerifier;
import org.openfuxml.xml.renderer.cmp.Pdf;
import org.slf4j.LoggerFactory;

public class OfxLatexRenderer extends OfxRenderer
{
	private LatexPreamble latexPreamble;
	private LatexDocumentRenderer rDocument;
	private List<String> txt;
	
	public OfxLatexRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm,Pdf pdf)
	{
		logger = LoggerFactory.getLogger(OfxLatexRenderer.class);
		latexPreamble = new LatexPreamble(ConfigurationProviderFacotry.build(cmm,dsm));
		rDocument = new LatexDocumentRenderer(ConfigurationProviderFacotry.build(cmm,dsm),pdf,latexPreamble);
		
		txt = new ArrayList<String>();
	}
	
	public void render(Document ofxDocument) throws OfxAuthoringException, OfxConfigurationException
	{
		OfxDocumentStructureVerifier.checkForContent(ofxDocument);
		rDocument.render(ofxDocument.getContent());
		latexPreamble.render();
		
		txt.addAll(latexPreamble.getContent());
		txt.addAll(rDocument.getContent());		
	}
	
	public List<String> getContent(){return txt;}
}