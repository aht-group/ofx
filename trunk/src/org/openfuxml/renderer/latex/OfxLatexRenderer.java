package org.openfuxml.renderer.latex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.latex.util.LatexDocument;
import org.openfuxml.renderer.latex.util.TxtWriter;

public class OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(OfxLatexRenderer.class);
	
	private LatexPreamble latexPreamble;
	private LatexDocument latexDocument;
	private List<String> txt;
	
	public OfxLatexRenderer()
	{
		latexPreamble = new LatexPreamble();
		latexDocument = new LatexDocument(latexPreamble);
		
		txt = new ArrayList<String>();
	}
	
	public void render(String ofxDocFileName)
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
		catch (FileNotFoundException e)
		{
			// TODO OfxRenderEXception
			e.printStackTrace();
		}
	}
	
	public List<String> getContent(){return txt;}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/properties/user.properties");
		Configuration config = ConfigLoader.init();
		
		OfxLatexRenderer renderer = new OfxLatexRenderer();
		renderer.render(config.getString("wiki.xml"));
		
		File dstDir = new File(config.getString("wiki.latex.dir"));
		
		TxtWriter writer = new TxtWriter();
		writer.setTargetDirFile(dstDir, config.getString("wiki.latex.file"));
//		writer.debug(renderer.getContent());
		writer.writeFile(renderer.getContent());
	}
}