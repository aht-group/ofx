package org.openfuxml.renderer.html;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.structure.HtmlDocumentRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.DocumentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestHtmlDocumentRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlDocumentRenderer.class);

	private enum Key {doc, withSub}
	
	private HtmlDocumentRenderer renderer;

	@Before public void init()
	{
		super.initDir("");
		renderer = new HtmlDocumentRenderer(cp, "Test Doc");
	}

	@Test public void doc() throws IOException, OfxConfigurationException, OfxAuthoringException
	{
		initFile(Key.doc);
        renderer.render(DocumentProvider.build().getContent());
    	renderTest(renderer);
	}

	@Test public void withSub() throws IOException, OfxConfigurationException, OfxAuthoringException
	{
		initFile(Key.withSub);
		renderer.render(DocumentProvider.buildWithSubcontent().getContent());
		renderTest(renderer);
	}
	
	public void test() throws OfxAuthoringException, OfxConfigurationException, IOException
	{
		Document doc = DocumentProvider.buildComplexALL();
		JaxbUtil.info(doc);
		
		logger.info(StringUtil.stars());
		
		Writer w = new OutputStreamWriter(System.out, "UTF-8");
		
		OfxHtmlRenderer2 htmlRenderer = new OfxHtmlRenderer2(new OfxConfigurationProvider(),"");
		htmlRenderer.render(w,(Section)doc.getContent().getContent().get(0));
	}

	public static void main(String[] args) throws IOException, OfxConfigurationException, OfxAuthoringException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlDocumentRenderer test = new TestHtmlDocumentRenderer();
        test.setEnvironment(true);
		
//        test.init();test.doc();
//		test.init();test.withSub();
		test.init();test.test();
	}
}