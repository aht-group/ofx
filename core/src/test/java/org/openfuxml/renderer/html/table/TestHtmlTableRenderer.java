package org.openfuxml.renderer.html.table;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Content;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.renderer.html.head.HtmlHead;
import org.openfuxml.renderer.html.structure.HtmlBody;
import org.openfuxml.renderer.html.structure.HtmlDocumentRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.TableProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlTableRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlTableRenderer.class);

	private enum Key {simple, complex, withHead}
	
	private HtmlBody renderer;

	@Before public void init()
	{
		super.initDir("table");
		renderer = new HtmlBody(new OfxConfigurationProvider());
		HtmlHead hh = new HtmlHead(new OfxConfigurationProvider());
		hh.render(null);
	}

	@Test public void simple() throws IOException
	{
		initFile(Key.simple);
        renderer.render(TableProvider.buildWithoutSpecification());
    	renderTest(renderer);
	}

	@Test public void complex() throws IOException
	{
		initFile(Key.complex);
		renderer.render(TableProvider.buildWithoutSpecification(4,6));
		renderTest(renderer);
	}

	@Test public void withHead() throws IOException, OfxConfigurationException, OfxAuthoringException
	{
		initFile(Key.withHead);
		HtmlDocumentRenderer docR = new HtmlDocumentRenderer(new OfxConfigurationProvider(), "TableTest");
		Content c = new Content();
		c.getContent().add(TableProvider.build(4,6));
		docR.render(c);
		renderTest(renderer);
	}

	public static void main(String[] args) throws IOException, OfxConfigurationException, OfxAuthoringException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlTableRenderer test = new TestHtmlTableRenderer();
        test.setEnvironment(true);
		
        test.init();test.simple();
		test.init();test.complex();
		test.init();test.withHead();
	}
}