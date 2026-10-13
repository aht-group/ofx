package org.openfuxml.processor.pre;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.exlp.util.jx.JaxbUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExternalMergerOfxDoc extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestExternalMergerOfxDoc.class);
	
	private ExternalContentEagerLoader exMerger;
	
	public static String srcDirName = "src/test/resources/data/pre/external/ofxdoc/in";
	public static final String dstDirName = "src/test/resources/data/pre/external/ofxdoc/out";
	
	private void setup(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-4);
		fRef = new File(dstDirName,name+".xml");
	}
	
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xml");}
	
	@BeforeEach
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		exMerger = new ExternalContentEagerLoader();
	}
	
	@AfterEach
	public void close()
	{
		exMerger = null;
	}
    
    @ParameterizedTest
    @MethodSource("initFileNames")
    public void render(File fTest) throws OfxInternalProcessingException, FileNotFoundException
    {
    	setup(fTest);
    	render(false);
    }
	
	private void render(boolean saveReference) throws FileNotFoundException, OfxInternalProcessingException
	{
		logger.debug(fTest.getAbsolutePath());
		Document ofxDocDst = exMerger.mergeToOfxDoc(fTest);
		
		if(saveReference)
		{
			JaxbUtil.save(fRef, ofxDocDst, true);
		}
		else
		{
			Document ofxDocRef = JaxbUtil.loadJAXB(fRef.getAbsolutePath(), Document.class);
			Assertions.assertEquals(JaxbUtil.toString(ofxDocRef),JaxbUtil.toString(ofxDocDst));
		}	
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		OfxBootstrap.init();
			
		boolean saveReference = true;
		int id = -1;
		int index = 0;
		
		for(Object[] o : TestExternalMergerOfxDoc.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestExternalMergerOfxDoc test = new TestExternalMergerOfxDoc();
			test.setup(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.render(saveReference);}
			test.close();
			index++;
		}
    }
}