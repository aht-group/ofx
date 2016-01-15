package org.openfuxml.renderer.text.table;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.table.OfxTableFactory;
import org.openfuxml.factory.xml.table.TestTableFactory;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;

public class TestTextTableRenderer extends AbstractOfxCoreTest
{	
	private Table table;
	private TextTableRenderer renderer;
	
	@Before
	public void init()
	{
		table = OfxTableFactory.build(TestTableFactory.columnNames,TestTableFactory.data);
		renderer = new TextTableRenderer(cmm,dsm);
	}
	
	@Test
	public void testOfx() throws OfxAuthoringException
	{
		renderer.render(table);
		debugCharacter(renderer);
	}
	
	public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestTextTableRenderer cli = new TestTextTableRenderer();
    	cli.init();
    	cli.testOfx();
    }
}
