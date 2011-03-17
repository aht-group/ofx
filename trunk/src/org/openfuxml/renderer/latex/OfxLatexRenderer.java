package org.openfuxml.renderer.latex;

import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.renderer.latex.document.LatexDocument;
import org.openfuxml.renderer.latex.header.LatexArticle;

public class OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(OfxLatexRenderer.class);
	
	private LatexArticle latexHeader;
	private LatexDocument latexDocument;
	
	public OfxLatexRenderer()
	{
		latexHeader = new LatexArticle();
		latexDocument = new LatexDocument();
	}
	
	public void render()
	{
		List<String> txt = new ArrayList<String>();
		txt.addAll(latexHeader.render());
		txt.addAll(latexDocument.render());
		
		for(String s : txt)
		{
			System.out.println(s);
		}
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/config/wiki/wiki.xml");
		Configuration config = ConfigLoader.init();
		
		String ofxDoc = "resources/data/xml/latex/helloworld.xml";
		OfxLatexRenderer renderer = new OfxLatexRenderer();
		renderer.render();
	}
}
