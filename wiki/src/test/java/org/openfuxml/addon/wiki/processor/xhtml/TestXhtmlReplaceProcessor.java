package org.openfuxml.addon.wiki.processor.xhtml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.exlp.util.io.StringUtil;
import org.exlp.util.jx.JaxbUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.addon.wiki.processor.markup.TestWikiInlineProcessor;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringIO;

@RunWith(Parameterized.class)
public class TestXhtmlReplaceProcessor extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
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
    	logger.debug(fTest.getAbsolutePath());
    	test(false);
    }
	
	private void test(boolean saveReference) throws OfxInternalProcessingException
	{
		String inTxt = StringUtil.readFile(fTest);
		String outTxt = xhtmlP.process(inTxt);
		if(saveReference)
		{
			StringIO.writeTxt(fRef, outTxt.trim());
		}
		else
		{
			String refTxt = StringUtil.readFile(fRef);
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
		OfxBootstrap.init();
		
		boolean saveReference = true;
		int id = -1;

		TestXhtmlReplaceProcessor.chain(id,saveReference);
    }
}