package org.openfuxml.renderer.latex.structure;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.latex.OfxLatexRenderer;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.util.reference.OfxReferenceDocumentBuilder;
import org.openfuxml.util.reference.OfxReferencePdfBuilder;
import org.openfuxml.xml.renderer.cmp.Pdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestLatexDocumentRenderer extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexDocumentRenderer.class);

	private Document ofxDocument;
	private Pdf pdfSettings;
	
	@Before
	public void init()
	{
		ofxDocument = OfxReferenceDocumentBuilder.ofxDocument();
		pdfSettings = OfxReferencePdfBuilder.referencePdfSettings();
	}
	
	@Test(expected=OfxAuthoringException.class)
	public void noContent() throws OfxAuthoringException, OfxConfigurationException
	{
		ofxDocument.setContent(null);
		OfxLatexRenderer latexRenderer = new OfxLatexRenderer(cmm,dsm,null);
		latexRenderer.render(ofxDocument);
	}
	
	public void writeExpected() throws OfxAuthoringException, OfxConfigurationException
	{
		logger.info("Writing expected Document");
		JaxbUtil.info(ofxDocument);
		
		OfxLatexRenderer latexRenderer = new OfxLatexRenderer(cmm,dsm,pdfSettings);
		latexRenderer.render(ofxDocument);
		for(String s : latexRenderer.getContent())
		{
			logger.info(s);
		}
	}
  
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();	
    	TestLatexDocumentRenderer rrLatex = new TestLatexDocumentRenderer();
    	rrLatex.init();
    	rrLatex.writeExpected();
    }
}