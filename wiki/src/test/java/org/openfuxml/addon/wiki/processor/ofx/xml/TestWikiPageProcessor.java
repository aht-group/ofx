package org.openfuxml.addon.wiki.processor.ofx.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.exlp.util.io.StringUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openfuxml.addon.wiki.processor.markup.TestWikiInlineProcessor;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;

public class TestWikiPageProcessor extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	private WikiPageProcessor xmlP;
	
	private static final String srcDirName = "src/test/resources/data/xhtml/final";
	private static final String dstDirName = "src/test/resources/data/xml/ofx";
	
	private File fTest;
	private File fRef;

	private void setup(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-6);
		fRef = new File(dstDirName,name+".xml");
	}
	
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xhtml");}
	
	@BeforeEach
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		xmlP = new WikiPageProcessor();
	}
	
	@AfterEach
	public void close()
	{
		xmlP = null;
	}
    
    @ParameterizedTest
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
			Assertions.assertEquals(JDomUtil.docToTxt(refDoc),JDomUtil.docToTxt(doc));
		}	
	}
	
	public static void chain(int id, boolean saveReference) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		int index = 0;
		for(Object[] o : TestWikiPageProcessor.initFileNames())
		{
			if(id<0 | id==index)
			{
				File fTest = (File)o[0];
				TestWikiPageProcessor test = new TestWikiPageProcessor();
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
		int id = -1;
		
		TestWikiPageProcessor.chain(id,saveReference);
    }
}