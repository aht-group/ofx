package org.openfuxml.renderer.latex;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.processor.latex.util.LatexDocument;
import org.openfuxml.xml.renderer.cmp.Pdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxLatexRenderer.class);
	
	private LatexPreamble latexPreamble;
	private LatexDocument latexDocument;
	private List<String> txt;
	
	public OfxLatexRenderer(Pdf pdf)
	{
		latexPreamble = new LatexPreamble();
		latexDocument = new LatexDocument(pdf,latexPreamble);
		
		txt = new ArrayList<String>();
	}
	
	public void render(String ofxDocFileName) throws OfxAuthoringException
	{
		try
		{
			logger.debug("Processing: "+ofxDocFileName);
			Ofxdoc ofxdoc = JaxbUtil.loadJAXB(ofxDocFileName, Ofxdoc.class);
			render(ofxdoc);
		}
		catch (FileNotFoundException e) {logger.error("",e);}
	}
	
	public void render(Ofxdoc ofxdoc) throws OfxAuthoringException
	{
		if(!ofxdoc.isSetContent())
		{
			throw new OfxAuthoringException("No content available");
		}
		latexDocument.render(ofxdoc.getContent());
		latexPreamble.render();
		
		txt.addAll(latexPreamble.getContent());
		txt.addAll(latexDocument.getContent());		
	}
	
	public List<String> getContent(){return txt;}
}