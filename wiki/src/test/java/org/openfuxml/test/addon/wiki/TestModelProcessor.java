package org.openfuxml.test.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.addon.wiki.processor.markup.WikiModelProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;

@RunWith(Parameterized.class)
public class TestModelProcessor
{
	static Log logger = LogFactory.getLog(TestModelProcessor.class);
	
	private WikiModelProcessor wmp;
	
	private static final String srcDirName = "src/test/resources/data/wiki/markup";
	private static final String dstDirName = "src/test/resources/data/xhtml/model";
	
	private File fTest;
	private File fRef;

	public TestModelProcessor(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-4);
		fRef = new File(dstDirName,name+".xhtml");
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
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
    }
	
	@Before
	public void initWmp() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		wmp = new WikiModelProcessor();
	}
	
	@After
	public void closeWmp()
	{
		wmp = null;
	}
    
    @Test
    public void testPlainToMarkup() throws OfxInternalProcessingException
    {
    	logger.debug(fTest.getAbsoluteFile());
    	wikiPlainToMarkup(false);
    }
	
	private void wikiPlainToMarkup(boolean saveReference) throws OfxInternalProcessingException
	{
		String markupTxt = WikiContentIO.loadTxt(fTest);
		String modelXhtml = wmp.process(markupTxt);
		if(saveReference)
		{
			WikiContentIO.writeTxt(fRef, modelXhtml);
		}
		else
		{
			String xhtmlRef = WikiContentIO.loadTxt(fRef);
			Assert.assertEquals(xhtmlRef,modelXhtml);
		}	
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		boolean genRefFiles = false;
		int testFileId = 4;
		
		if(testFileId>0)
		{
			File fTest = new File(srcDirName,testFileId+".txt");
			String markupTxt = WikiContentIO.loadTxt(fTest);
			
			WikiModelProcessor wmp = new WikiModelProcessor();
			System.out.println(wmp.process(markupTxt));
		}
		
		if(genRefFiles)
		{
			for(Object[] o : TestMarkupProcessor.initFileNames())
			{
				File fTest = (File)o[0];
				logger.debug(fTest);
				
				TestModelProcessor test = new TestModelProcessor(fTest);
				test.initWmp();
				test.wikiPlainToMarkup(true);
				test.closeWmp();
			}
		}
		
		
    }
}