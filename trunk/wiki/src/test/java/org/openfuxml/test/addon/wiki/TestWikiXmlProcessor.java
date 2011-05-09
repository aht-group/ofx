package org.openfuxml.test.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.addon.wiki.processor.ofx.xml.WikiPageProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;

@RunWith(Parameterized.class)
public class TestWikiXmlProcessor extends AbstractFileProcessingTest
{
	static Log logger = LogFactory.getLog(TestWikiInlineProcessor.class);
	
	private WikiPageProcessor xmlP;
	
	private static final String srcDirName = "src/test/resources/data/xhtml/final";
	private static final String dstDirName = "src/test/resources/data/xml/ofx";
	
	private File fTest;
	private File fRef;

	public TestWikiXmlProcessor(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-6);
		fRef = new File(dstDirName,name+".xml");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xhtml");}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		xmlP = new WikiPageProcessor();
	}
	
	@After
	public void close()
	{
		xmlP = null;
	}
    
    @Test
    public void test() throws OfxInternalProcessingException
    {
    	logger.debug(fTest.getAbsoluteFile());
    	test(false);
    }
	
	private void test(boolean saveReference) throws OfxInternalProcessingException
	{
		String inTxt = WikiContentIO.loadTxt(fTest,false);
		Element xml = xmlP.process(inTxt);
		Document doc = new Document();
		doc.setRootElement(xml);
		if(saveReference)
		{
			JDomUtil.save(doc, fRef, Format.getRawFormat());
		}
		else
		{
			Document refDoc = JDomUtil.load(fRef);
			Assert.assertEquals(JDomUtil.docToTxt(refDoc),JDomUtil.docToTxt(doc));
		}	
	}
	
	public static void chain(int id, boolean saveReference) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		int index = 0;
		for(Object[] o : TestWikiXmlProcessor.initFileNames())
		{
			if(id<0 | id==index)
			{
				File fTest = (File)o[0];
				TestWikiXmlProcessor test = new TestWikiXmlProcessor(fTest);
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
		
		TestWikiXmlProcessor.chain(id,saveReference);
    }
}