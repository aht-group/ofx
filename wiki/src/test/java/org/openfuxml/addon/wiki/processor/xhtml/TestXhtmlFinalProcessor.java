package org.openfuxml.addon.wiki.processor.xhtml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.exlp.util.io.StringUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openfuxml.addon.wiki.processor.markup.TestWikiInlineProcessor;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringIO;

public class TestXhtmlFinalProcessor extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	private XhtmlFinalProcessor xhtmlFinalP;
	
	private static final String srcDirName = "src/test/resources/data/xhtml/replace";
	private static final String dstDirName = "src/test/resources/data/xhtml/final";
	
	private File fTest;
	private File fRef;

	private void setup(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-6);
		fRef = new File(dstDirName,name+".xhtml");
	}
	
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xhtml");}
	
	@BeforeEach
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		xhtmlFinalP = new XhtmlFinalProcessor();
	}
	
	@AfterEach
	public void close()
	{
		xhtmlFinalP = null;
	}
    
    @ParameterizedTest @Disabled
    @MethodSource("initFileNames")
    public void test(File fTest) throws OfxInternalProcessingException
    {
    	logger.debug(fTest.getAbsolutePath());
    	setup(fTest);
    	test(false);
    }
	
	private void test(boolean saveReference) throws OfxInternalProcessingException
	{
		String inTxt = StringUtil.readFile(fTest);
		String outTxt = xhtmlFinalP.process(inTxt);
		if(saveReference)
		{
			logger.info("Write "+fRef);
			StringIO.writeTxt(fRef, outTxt);
		}
		else
		{
			String refTxt = StringUtil.readFile(fRef);
			Assertions.assertEquals(refTxt.trim(),outTxt.trim());
		}	
	}
	
	public static void chain(int id, boolean saveReference) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		int index = 0;
		for(Object[] o : TestXhtmlFinalProcessor.initFileNames())
		{
			if(id<0 | id==index)
			{
				File fTest = (File)o[0];
				TestXhtmlFinalProcessor test = new TestXhtmlFinalProcessor();
				test.setup(fTest);
				test.init();
				logger.trace(id+" "+index);
				test.test(saveReference);
				test.close();
			}			
			index++;
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		OfxBootstrap.init();
		
		boolean saveReference = true;
		
		int id = 0;
		
		TestXhtmlFinalProcessor.chain(id,saveReference);
    }
}