package org.openfuxml.controller.processor.pre;

import org.exlp.util.jx.JaxbUtil;
import org.jdom2.JDOMException;
import org.openfuxml.controller.provider.DemoContentProvider;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.processor.pre.OfxIdGenerator;
import org.openfuxml.test.OfxUtilTestBootstrap;

public class TestIdGenerator
{
	private Document doc;
	private OfxIdGenerator idGenerator;
	
	public TestIdGenerator()
	{
		idGenerator = new OfxIdGenerator();
		doc = DemoContentProvider.instance().withIds(false).build();
	}
	
	public void tableFactory() throws JDOMException
	{
//		JaxbUtil.info(doc);
		doc = idGenerator.generate(doc);
		JaxbUtil.info(doc);
	}
	
	public static void main(String[] args) throws Exception
    {
		OfxUtilTestBootstrap.init();
			
    	TestIdGenerator cli = new TestIdGenerator();
    	cli.tableFactory();
    }
}
