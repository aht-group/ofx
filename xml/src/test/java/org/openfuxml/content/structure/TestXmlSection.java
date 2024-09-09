package org.openfuxml.content.structure;

import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSection extends AbstractXmlStructureTest<Section>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public static TestXmlSection instance() {return new TestXmlSection();}
	private TestXmlSection() {super(Section.class);}
	
    @Override public Section build(boolean wChildren)
    {
    	Section xml = new Section();
    	xml.setContainer(true);
    	xml.setInclude("myInclude");
    	
    	if(wChildren)
    	{
    		
    	}
    	
    	return xml;
    }
    	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlSection.instance().saveReferenceXml();
    }
}