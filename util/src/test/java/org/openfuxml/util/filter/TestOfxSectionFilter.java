package org.openfuxml.util.filter;

import org.exlp.util.jx.JaxbUtil;
import org.junit.Before;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.test.AbstractOfxUtilTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxSectionFilter extends AbstractOfxUtilTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestOfxSectionFilter.class);
	
	private Section section;
	
	@Before
	public void init()
	{
		Section sub2 = XmlSectionFactory.build();
		
		Section sub1 = XmlSectionFactory.build();
		sub1.getContent().add(sub2);
		
		section = XmlSectionFactory.build();
		section.getContent().add(sub1);
	}
	
	public void test()
	{
		JaxbUtil.info(section);
		
		Section filtered = OfxSectionFilter.toMaxDepth(section,0);
		JaxbUtil.info(filtered);
	}
	
	public static void main(String[] args) throws Exception
    {
		OfxBootstrap.init();
			
		TestOfxSectionFilter cli = new TestOfxSectionFilter();
		cli.init();
    	cli.test();
    }
}