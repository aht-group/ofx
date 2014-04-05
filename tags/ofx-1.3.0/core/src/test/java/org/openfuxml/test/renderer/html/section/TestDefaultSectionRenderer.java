package org.openfuxml.test.renderer.html.section;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

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
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.renderer.processor.html.section.DefaultSectionRenderer;
import org.openfuxml.test.AbstractFileProcessingTest;

@RunWith(Parameterized.class)
public class TestDefaultSectionRenderer extends AbstractFileProcessingTest
{
	static Log logger = LogFactory.getLog(TestDefaultSectionRenderer.class);
	
	private DefaultSectionRenderer renderer;
	
	public static String srcDirName = "src/test/resources/data/html/section/ofx";
	public static final String dstDirName = "src/test/resources/data/html/section/web";
	
	public TestDefaultSectionRenderer(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-4);
		fRef = new File(dstDirName,name+".html");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xml");}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		renderer = new DefaultSectionRenderer();
	}
	
	@After
	public void close()
	{
		renderer = null;
	}
    
    @Test
    public void render() throws OfxInternalProcessingException, FileNotFoundException
    {
    	render(false);
    }
	
	private void render(boolean saveReference) throws FileNotFoundException
	{
		logger.debug(fTest.getAbsoluteFile());
		Section section = (Section)JaxbUtil.loadJAXB(fTest.getAbsolutePath(), Section.class);

		Element html = new Element("html");
		html.addContent(renderer.render(section));
		
		Document doc = new Document();
		doc.setRootElement(html);
		
		if(saveReference)
		{
			JDomUtil.save(doc, fRef, Format.getPrettyFormat());
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
		int id = 3;
		int index = 0;
		
		for(Object[] o : TestDefaultSectionRenderer.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestDefaultSectionRenderer test = new TestDefaultSectionRenderer(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.render(saveReference);}
			test.close();
			index++;
		}
    }
}