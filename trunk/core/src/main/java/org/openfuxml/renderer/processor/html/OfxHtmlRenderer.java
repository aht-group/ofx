package org.openfuxml.renderer.processor.html;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.data.jaxb.Pdf;
import org.openfuxml.renderer.processor.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.processor.latex.util.LatexDocument;

public class OfxHtmlRenderer
{
	static Log logger = LogFactory.getLog(OfxHtmlRenderer.class);
	
	private LatexPreamble latexPreamble;
	private LatexDocument latexDocument;
	private List<String> txt;
	
	public OfxHtmlRenderer(Pdf pdf)
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
			Ofxdoc ofxdoc = (Ofxdoc)JaxbUtil.loadJAXB(ofxDocFileName, Ofxdoc.class);
			
			latexDocument.render(ofxdoc.getContent());
			latexPreamble.render();
			
			txt.addAll(latexPreamble.getContent());
			txt.addAll(latexDocument.getContent());
		}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	public List<String> getContent(){return txt;}
}