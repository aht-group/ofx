package org.openfuxml.util;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.test.AbstractOfxUtilTest;
import org.openfuxml.test.OfxUtilTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxMultilangFilter extends AbstractOfxUtilTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestOfxMultilangFilter.class);

	private Section section;
	
	@Before
	public void init()
	{
		section = XmlSectionFactory.build();
		section.getContent().add(XmlTitleFactory.build("de","test"));
		section.getContent().add(XmlTitleFactory.build("en","test"));
	}
	
	@Test
	public void filterLang() throws OfxAuthoringException
	{
		JaxbUtil.info(section);
	}
	
  
    public static void main(String[] args) throws Exception
    {
    	OfxUtilTestBootstrap.init();
			
    	TestOfxMultilangFilter test = new TestOfxMultilangFilter();
    	test.init();
    	test.filterLang();
    }
}