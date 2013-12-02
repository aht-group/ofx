package org.openfuxml.renderer.latex;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.structure.LatexDocumentRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.xml.renderer.cmp.Pdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxLatexRenderer.class);
	
	private LatexPreamble latexPreamble;
	private LatexDocumentRenderer rDocument;
	private List<String> txt;
	
	public OfxLatexRenderer(Pdf pdf)
	{
		latexPreamble = new LatexPreamble();
		rDocument = new LatexDocumentRenderer(pdf,latexPreamble);
		
		txt = new ArrayList<String>();
	}
	
	public void render(String ofxDocFileName) throws OfxAuthoringException
	{
		try
		{
			logger.debug("Processing: "+ofxDocFileName);
			Document ofxdoc = JaxbUtil.loadJAXB(ofxDocFileName, Document.class);
			render(ofxdoc);
		}
		catch (FileNotFoundException e) {logger.error("",e);}
	}
	
	public void render(Document ofxdoc) throws OfxAuthoringException
	{
		if(!ofxdoc.isSetContent())
		{
			throw new OfxAuthoringException("No content available");
		}
		rDocument.render(ofxdoc.getContent());
		latexPreamble.render();
		
		txt.addAll(latexPreamble.getContent());
		txt.addAll(rDocument.getContent());		
	}
	
	public List<String> getContent(){return txt;}
}