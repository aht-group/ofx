package org.openfuxml.transform;

import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.junit.Test;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@RunWith(Parameterized.class)
public class TestXhtmlTransformer extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXhtmlTransformer.class);
	
	private XhtmlTransformer xmlP;

	public TestXhtmlTransformer()
	{
		xmlP = new XhtmlTransformer();
	}
	
	@Test public void dummy() {}
	
	public void basic()
	{
		String txt = "<p>This <em>is</em> a <strong>test</strong>.</p><p>An now a list:</p><ul><li>Point A</li><li>Point B</li></ul>";
		
		Section xml = xmlP.process(txt);
		JaxbUtil.info(xml);
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		OfxBootstrap.init();
		
		TestXhtmlTransformer cli = new TestXhtmlTransformer();
		cli.basic();
    }
}