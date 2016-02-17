package org.openfuxml.renderer.html.table;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.renderer.html.structure.HtmlBody;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.SectionAndTitleProvider;
import org.openfuxml.test.provider.TableProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestHtmlTableRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlTableRenderer.class);

	private enum Key {table}
	
	private HtmlBody renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlBody(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
	}

	@Test public void table() throws IOException
	{
		initFile(Key.table);
        renderer.render(TableProvider.build());
    	renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlTableRenderer test = new TestHtmlTableRenderer();
        test.setEnvironment(true);
		
        test.init();test.table();
	}
}