package org.openfuxml.transform;

import java.io.FileNotFoundException;

import org.jdom2.Document;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxWikiTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

@RunWith(Parameterized.class)
public class TestXhtmlTransformer extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXhtmlTransformer.class);
	
	private XhtmlTransformer xmlP;

	public TestXhtmlTransformer()
	{
		xmlP = new XhtmlTransformer();
	}
	
	public void basic()
	{
		String txt = "<p>jhbbjh <strong>jkbjhbj</strong> bhbj <em>njhbhb</em></p><p>hui <em>b</em>j</p>";
		
		Section xml = xmlP.process(txt);
		JaxbUtil.info(xml);
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		OfxWikiTstBootstrap.init();
		
		TestXhtmlTransformer cli = new TestXhtmlTransformer();
		cli.basic();
    }
}