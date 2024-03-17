package org.openfuxml.renderer.wiki;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.exlp.util.io.txt.ExlpTxtWriter;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.latex.util.OfxLatexResources;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractWikiContentTest extends AbstractOfxCoreTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractWikiContentTest.class);
	
	protected static final String rootDir = "src/test/resources/data/wiki/content";

	protected File latexBase;
	
	protected void initLatexTestEnvironment(Configuration config)
	{
		latexBase = new File(config.getString("check-config-key"));
		logger.info("Test Environment "+latexBase.getAbsolutePath());
		
		try
		{
			OfxLatexResources latexBuilder = new OfxLatexResources(latexBase);
			latexBuilder.copyPackages();
			latexBuilder.copyTest();
		}
		catch (OfxConfigurationException e) {e.printStackTrace();}
	}
	
	protected void testLatex(List<String> content)
	{
		if(latexBase!=null && latexBase.exists() && latexBase.isDirectory())
		{
			ExlpTxtWriter w = new ExlpTxtWriter();
			w.add(content);
			w.writeFile(new File(latexBase,"test.tex"));
		}
		
	}
}