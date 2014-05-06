package org.openfuxml.processor.pre;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.io.FilenameUtils;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestExternalContentEagerLoader extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestExternalContentEagerLoader.class);
	
	private ExternalContentEagerLoader exMerger;
	
	public static String srcDirName = "src/test/resources/data/pre/external/elements/in";
	public static final String dstDirName = "src/test/resources/data/pre/external/elements/out";
	
	public TestExternalContentEagerLoader(File fTest)
	{
		this.fTest = fTest;
		String name = FilenameUtils.removeExtension(fTest.getName());
		fRef = new File(dstDirName,name+".xml");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xml");}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		exMerger = new ExternalContentEagerLoader();
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
		OfxCoreTestBootstrap.init();
		
		boolean saveReference = true;
		int id = -1;
		int index = 0;
		
		for(Object[] o : TestExternalContentEagerLoader.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestExternalContentEagerLoader test = new TestExternalContentEagerLoader(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.render(saveReference);}
			test.close();
			index++;
		}
    }
}