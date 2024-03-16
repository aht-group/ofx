package org.openfuxml.addon.wiki.processor.markup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import org.exlp.util.io.StringUtil;
import org.exlp.util.jx.JaxbUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.model.xml.addon.wiki.MarkupProcessor;
import org.openfuxml.model.xml.addon.wiki.Templates;
import org.openfuxml.test.AbstractOfxTest;
import org.openfuxml.test.OfxBootstrap;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringIO;

@RunWith(Parameterized.class)
public class TestWikiMarkupProcessor extends AbstractOfxTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiMarkupProcessor.class);
	
	private WikiMarkupProcessor wmp;
	
	private static final String srcDirName = "src/test/resources/data/wiki/plain";
	private static final String dstDirName = "src/test/resources/data/wiki/markup";
	
	private File fTest;
	private File fRef;

	public TestWikiMarkupProcessor(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-4);
		fRef = new File(dstDirName,name+".txt");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames()
	{
		Collection<Object[]> c = new ArrayList<Object[]>();
		File srcDir = new File(srcDirName);
		for(File f : srcDir.listFiles())
		{
			if(f.getName().endsWith(".txt"))
			{
				Object[] o = new Object[] {f};
				c.add(o);
			}
		}
		return c;
	}
	
	@Before
	public void initWmp() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		Cmp cmp = (Cmp)JaxbUtil.loadJAXB("src/test/resources/config/cmp/wiki.xml", Cmp.class);
		MarkupProcessor mpXml = cmp.getPreprocessor().getWiki().getMarkupProcessor();
		Templates   templates = cmp.getPreprocessor().getWiki().getTemplates();
		
		wmp = new WikiMarkupProcessor(mpXml.getReplacements(), mpXml.getInjections(), templates);
	}
	
	@After
	public void closeWmp()
	{
		wmp = null;
	}
    
    @Test
    public void testPlainToMarkup() throws OfxInternalProcessingException
    {
    	logger.debug(fTest.getAbsolutePath());
    	wikiPlainToMarkup(false);
    }
	
	private void wikiPlainToMarkup(boolean saveReference) throws OfxInternalProcessingException
	{
		String plainTxt = StringUtil.readFile(fTest);
		String markupTxt = wmp.process(plainTxt, "article ... req?");
		if(saveReference)
		{
			StringIO.writeTxt(fRef, markupTxt);
		}
		else
		{
			String markupRefTxt = StringUtil.readFile(fRef);
			Assert.assertEquals(markupRefTxt,markupTxt);
		}	
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		OfxBootstrap.init();
		
		for(Object[] o : TestWikiMarkupProcessor.initFileNames())
		{
			File fTest = (File)o[0];
			logger.debug(fTest.getAbsolutePath());
			
			TestWikiMarkupProcessor test = new TestWikiMarkupProcessor(fTest);
			test.initWmp();
			test.wikiPlainToMarkup(true);
			test.closeWmp();
		}
    }
}