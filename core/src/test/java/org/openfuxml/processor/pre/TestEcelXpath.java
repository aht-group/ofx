package org.openfuxml.processor.pre;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.io.FilenameUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.xpath.XPathExpression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestEcelXpath extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestEcelXpath.class);
	
	int expected;
	private ExternalContentEagerLoader ecel;
	
	public static String srcDirName = "src/test/resources/data/pre/external/elements/in";
	public static final String dstDirName = "src/test/resources/data/pre/external/elements/out";
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xml");}
	
	public TestEcelXpath(File fTest)
	{
		this.fTest = fTest;
		expected = new Integer(FilenameUtils.removeExtension(fTest.getName()));
	}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		ecel = new ExternalContentEagerLoader();
	}
	    
    @Test
    public void elementsWith() throws OfxInternalProcessingException, FileNotFoundException
    {
    	Document doc = JDomUtil.load(fTest);
    	XPathExpression<Element> xpe = ecel.build();

    	List<Element> list = xpe.evaluate(doc);
    	Assert.assertEquals(expected,list.size());
    }
	
	
}