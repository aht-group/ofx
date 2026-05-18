package org.openfuxml.renderer.md.table;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.table.TestTableFactory;
import org.openfuxml.factory.xml.table.XmlTableFactory;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.renderer.markdown.OfxMdRenderer;
import org.openfuxml.renderer.text.AbstractTestTextRenderer;
import org.openfuxml.renderer.text.table.TextTableRenderer;
import org.openfuxml.test.OfxBootstrap;

public class TestMdTableRenderer extends AbstractTestTextRenderer
{	
	private static enum Key {simple}
	
	private TextTableRenderer renderer;
	
	@Before
	public void init()
	{
		super.initDir("table");
		renderer = new TextTableRenderer(cp);
	}
	
	@Test
	public void simple() throws OfxAuthoringException, IOException
	{
		Table table = XmlTableFactory.build(TestTableFactory.columnNames,TestTableFactory.data);
		
		OfxMdRenderer.instance().render(table,System.out);
		
	}
	
	public static void main(String[] args) throws Exception
    {
    	OfxBootstrap.init();	
    	TestMdTableRenderer test = new TestMdTableRenderer();
    	test.setEnvironment(true);
    	
    	test.init();
    	test.simple();
    }
}