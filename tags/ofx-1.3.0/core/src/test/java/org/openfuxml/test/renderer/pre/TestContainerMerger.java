package org.openfuxml.test.renderer.pre;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.renderer.processor.pre.OfxContainerMerger;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.xml.ns.OfxNsPrefixMapper;

@RunWith(Parameterized.class)
public class TestContainerMerger extends AbstractFileProcessingTest
{
	static Log logger = LogFactory.getLog(TestContainerMerger.class);
	
	private OfxContainerMerger containerMerger;
	
	public static String srcDirName = "src/test/resources/data/pre/container/in";
	public static final String dstDirName = "src/test/resources/data/pre/container/out";
	
	public TestContainerMerger(File fTest)
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
		containerMerger = new OfxContainerMerger();
	}
	
	@After
	public void close()
	{
		containerMerger = null;
	}
    
    @Test
    public void render() throws OfxInternalProcessingException, FileNotFoundException
    {
    	render(false);
    }
	
	private void render(boolean saveReference) throws FileNotFoundException, OfxInternalProcessingException
	{
		logger.debug(fTest.getAbsoluteFile());
		Ofxdoc ofxSrc = (Ofxdoc)JaxbUtil.loadJAXB(fTest.getAbsolutePath(), Ofxdoc.class);

		Ofxdoc ofxDst = containerMerger.merge(ofxSrc);
		
		if(saveReference)
		{
			JaxbUtil.save(fRef, ofxDst, new OfxNsPrefixMapper(), true);
		}
		else
		{
			Ofxdoc ofxRef = (Ofxdoc)JaxbUtil.loadJAXB(fRef.getAbsolutePath(), Ofxdoc.class);
			Assert.assertEquals(JaxbUtil.toString(ofxRef),JaxbUtil.toString(ofxDst));
		}	
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		boolean saveReference = false;
		int id = -1;
		int index = 0;
		
		for(Object[] o : TestContainerMerger.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestContainerMerger test = new TestContainerMerger(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.render(saveReference);}
			test.close();
			index++;
		}
    }
}