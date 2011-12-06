package org.openfuxml.test.renderer.pre;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

import org.jdom.Document;
import org.jdom.output.Format;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestExternalMergerElements extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestExternalMergerElements.class);
	
	private OfxExternalMerger exMerger;
	
	public static String srcDirName = "src/test/resources/data/pre/external/elements/in";
	public static final String dstDirName = "src/test/resources/data/pre/external/elements/out";
	
	public TestExternalMergerElements(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-4);
		fRef = new File(dstDirName,name+".xml");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xml");}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		exMerger = new OfxExternalMerger();
	}
	
	@After
	public void close()
	{
		exMerger = null;
	}
    
    @Test
    public void render() throws OfxInternalProcessingException, FileNotFoundException
    {
    	render(false);
    }
	
	private void render(boolean saveReference) throws FileNotFoundException, OfxInternalProcessingException
	{
		logger.debug(fTest.getAbsolutePath());
		
		Document doc = exMerger.mergeToDoc(fTest);
		
		if(saveReference)
		{
			JDomUtil.save(doc, fRef, Format.getRawFormat());
		}
		else
		{
			Document docRef = JDomUtil.load(fRef);
			Assert.assertEquals(JDomUtil.toString(docRef),JDomUtil.toString(doc));
		}	
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		boolean saveReference = true;
		int id = -1;
		int index = 0;
		
		for(Object[] o : TestExternalMergerElements.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestExternalMergerElements test = new TestExternalMergerElements(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.render(saveReference);}
			test.close();
			index++;
		}
    }
}