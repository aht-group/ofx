package org.openfuxml.addon.wiki.processor.markup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.exlp.util.io.StringUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringIO;

public class TestWikiModelProcessor extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	private WikiModelProcessor wmp;
	
	private static final String srcDirName = "src/test/resources/data/wiki/markup";
	private static final String dstDirName = "src/test/resources/data/xhtml/model";
	
	private File fTest;
	private File fRef;

	private void setup(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-4);
		fRef = new File(dstDirName,name+".xhtml");
	}
	
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".txt");}
	
	@BeforeEach
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		wmp = new WikiModelProcessor();
	}
	
	@AfterEach
	public void close()
	{
		wmp = null;
	}
    
    @ParameterizedTest
    @MethodSource("initFileNames")
    public void testPlainToMarkup(File fTest) throws OfxInternalProcessingException
    {
    	logger.debug(fTest.getAbsolutePath());
    	setup(fTest);
    	wikiPlainToMarkup(false);
    }
	
	private void wikiPlainToMarkup(boolean saveReference) throws OfxInternalProcessingException
	{
		String markupTxt = StringUtil.readFile(fTest);
		String modelXhtml = wmp.process(markupTxt);
		if(saveReference)
		{
			StringIO.writeTxt(fRef, modelXhtml.trim());
		}
		else
		{
			String xhtmlRef = StringUtil.readFile(fRef);
			Assertions.assertEquals(xhtmlRef.trim(),modelXhtml.trim());
		}	
	}
	
	public static void chain(int id, boolean saveReference) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		int index = 0;
		for(Object[] o : TestWikiModelProcessor.initFileNames())
		{
			if(id<0 | id==index)
			{
				File fTest = (File)o[0];
				TestWikiModelProcessor test = new TestWikiModelProcessor();
				test.setup(fTest);
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
		OfxBootstrap.init();
		
		boolean saveReference = true;
		int id = -1;
		
		TestWikiModelProcessor.chain(id,saveReference);
    }
}