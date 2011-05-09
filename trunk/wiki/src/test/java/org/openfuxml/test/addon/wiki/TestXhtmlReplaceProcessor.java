package org.openfuxml.test.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlReplaceProcessor;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.xml.renderer.cmp.Cmp;

@RunWith(Parameterized.class)
public class TestXhtmlReplaceProcessor extends AbstractFileProcessingTest
{
	static Log logger = LogFactory.getLog(TestWikiInlineProcessor.class);
	
	private XhtmlReplaceProcessor xhtmlP;
	
	private static final String srcDirName = "src/test/resources/data/xhtml/model";
	private static final String dstDirName = "src/test/resources/data/xhtml/replace";
	
	private static Cmp cmp;
	private File fTest;
	private File fRef;

	public TestXhtmlReplaceProcessor(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-6);
		fRef = new File(dstDirName,name+".xhtml");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xhtml");}
	
	@BeforeClass
	public static void initCmp() throws FileNotFoundException
	{
		String fNameCmp = "src/test/resources/config/cmp/wiki.xml";
		cmp = (Cmp)JaxbUtil.loadJAXB(fNameCmp, Cmp.class);
	}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		xhtmlP = new XhtmlReplaceProcessor(cmp.getPreprocessor().getWiki().getXhtmlProcessor().getReplacements());
	}
	
	@After
	public void close()
	{
		xhtmlP = null;
	}
    
    @Test
    public void test() throws OfxInternalProcessingException
    {
    	logger.debug(fTest.getAbsoluteFile());
    	test(false);
    }
	
	private void test(boolean saveReference) throws OfxInternalProcessingException
	{
		String inTxt = WikiContentIO.loadTxt(fTest);
		String outTxt = xhtmlP.process(inTxt);
		if(saveReference)
		{
			WikiContentIO.writeTxt(fRef, outTxt.trim());
		}
		else
		{
			String refTxt = WikiContentIO.loadTxt(fRef);
			Assert.assertEquals(refTxt.trim(),outTxt.trim());
		}	
	}
	
	public static void chain(int id, boolean saveReference) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		int index = 0;
		TestXhtmlReplaceProcessor.initCmp();
		for(Object[] o : TestXhtmlReplaceProcessor.initFileNames())
		{
			if(id<0 | id==index)
			{
				File fTest = (File)o[0];
				TestXhtmlReplaceProcessor test = new TestXhtmlReplaceProcessor(fTest);
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
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		boolean saveReference = true;
		int id = -1;

		TestXhtmlReplaceProcessor.chain(id,saveReference);
    }
}