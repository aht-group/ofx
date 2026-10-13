package org.openfuxml.processor.pre;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.xpath.XPathExpression;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.xml.JDomUtil;

public class TestExternalContentEagerLoader extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestExternalContentEagerLoader.class);
	
	private ExternalContentEagerLoader ecel;
	int expectedExternals;
	
	public static String srcDir = "src/test/resources";
	public static String srcDirName = srcDir+"/data/pre/external/elements/in";
	public static final String dstDirName = srcDir+"/data/pre/external/elements/out";
	
	private void setup(File fTest)
	{
		this.fTest = fTest;
		expectedExternals = Integer.valueOf(FilenameUtils.removeExtension(fTest.getName()));
		String name = FilenameUtils.removeExtension(fTest.getName());
		fRef = new File(dstDirName,name+".xml");
	}
	
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xml");}
	
	@BeforeEach
	public void init()
	{	
		ecel = new ExternalContentEagerLoader();
	}
	
	@Test
	public void dummy() {}
	
	@AfterEach
	public void close()
	{
		ecel = null;
	}
	
//	@Test
    public void xpathWithDocument() throws OfxInternalProcessingException, FileNotFoundException
    {
    	Document doc = JDomUtil.load(fTest);
    	XPathExpression<Element> xpe = ecel.getXpeInclude();

    	List<Element> list = xpe.evaluate(doc);
    	Assertions.assertEquals(expectedExternals,list.size());
    }
	
//    @Test
    public void xpathWithRootElement() throws OfxInternalProcessingException, FileNotFoundException
    {
    	Document doc = JDomUtil.load(fTest);
    	XPathExpression<Element> xpe = ecel.getXpeInclude();

    	List<Element> list = xpe.evaluate(doc.getRootElement());
    	Assertions.assertEquals(expectedExternals,list.size());
    }
	
    
//    @Test
    public void loadFromFile() throws FileNotFoundException, OfxAuthoringException
    {
    	render(fTest.getAbsolutePath(),false);
    }
    
//    @Test
    public void loadFromResource() throws FileNotFoundException, OfxAuthoringException
    {
    	RelativePathFactory rpf = new RelativePathFactory(new File(srcDir));
    	String relativeResource = rpf.relativate(fTest.getAbsoluteFile());
    	logger.info(relativeResource);
    	render(relativeResource,false);
    }
	
    private void render(boolean saveReference) throws FileNotFoundException, OfxAuthoringException
    {
    	render(fTest.getAbsolutePath(),saveReference);
    }
    
	private void render(String fileName, boolean saveReference) throws FileNotFoundException, OfxAuthoringException
	{
		Document docActual = ecel.load(fileName);
		
		if(saveReference)
		{
			JDomUtil.save(docActual, fRef, Format.getRawFormat());
		}
		else
		{
			Document docExcepcted = JDomUtil.load(fRef);
			Assertions.assertEquals(JDomUtil.toString(docExcepcted),JDomUtil.toString(docActual));
		}	
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxAuthoringException
    {
		OfxBootstrap.init();
		
		boolean saveReference = true;
		int id = -1;
		int index = 0;
		
		for(Object[] o : TestExternalContentEagerLoader.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestExternalContentEagerLoader test = new TestExternalContentEagerLoader();
			test.setup(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.render(saveReference);}
			test.close();
			index++;
		}
    }
}