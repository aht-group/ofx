package org.openfuxml.renderer.text.table;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.table.OfxTableFactory;
import org.openfuxml.factory.xml.table.TestTableFactory;
import org.openfuxml.renderer.text.AbstractTestTextRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;

public class TestTextTableRenderer extends AbstractTestTextRenderer
{	
	private static enum Key {simple}
	
	private TextTableRenderer renderer;
	
	@Before
	public void init()
	{
		super.initDir("table");
		renderer = new TextTableRenderer(cmm,dsm);
	}
	
	@Test
	public void simple() throws OfxAuthoringException, IOException
	{
		initFile(Key.simple);
		renderer.render(OfxTableFactory.build(TestTableFactory.columnNames,TestTableFactory.data));
		renderTest(renderer);
	}
	
	public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();	
    	TestTextTableRenderer test = new TestTextTableRenderer();
    	test.setEnvironment(true);
    	
    	test.init();test.simple();
    }
}