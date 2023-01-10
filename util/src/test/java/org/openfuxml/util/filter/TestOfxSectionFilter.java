package org.openfuxml.util.filter;

import org.junit.Before;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.test.AbstractOfxUtilTest;
import org.openfuxml.test.OfxUtilTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

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
		OfxUtilTestBootstrap.init();
			
		TestOfxSectionFilter cli = new TestOfxSectionFilter();
		cli.init();
    	cli.test();
    }
}