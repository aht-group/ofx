package org.openfuxml.test.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.addon.wiki.processor.markup.WikiModelProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;

@RunWith(Parameterized.class)
public class TestModelProcessor extends AbstractFileProcessingTest
{
	static Log logger = LogFactory.getLog(TestWikiInlineProcessor.class);
	
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
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".txt");}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		wmp = new WikiModelProcessor();
	}
	
	@After
	public void close()
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
			WikiContentIO.writeTxt(fRef, modelXhtml.trim());
		}
		else
		{
			String xhtmlRef = WikiContentIO.loadTxt(fRef);
			Assert.assertEquals(xhtmlRef.trim(),modelXhtml.trim());
		}	
	}
	
	public static void chain(int id, boolean saveReference) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		int index = 0;
		for(Object[] o : TestModelProcessor.initFileNames())
		{
			if(id<0 | id==index)
			{
				File fTest = (File)o[0];
				TestModelProcessor test = new TestModelProcessor(fTest);
				test.init();
				logger.trace(id+" "+index);
				test.wikiPlainToMarkup(saveReference);
				test.close();
			}			
			index++;
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		boolean saveReference = true;
		int id = 6;
		
		TestModelProcessor.chain(id,saveReference);
    }
}