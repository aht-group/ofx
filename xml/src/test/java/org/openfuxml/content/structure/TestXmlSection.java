package org.openfuxml.content.structure;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSection extends AbstractXmlStructureTest<Section>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlSection(){super(Section.class);}
	public static Section create(boolean withChildren){return (new TestXmlSection()).build(withChildren);}
   
    public Section build(boolean withChilds)
    {
    	Section xml = new Section();
    	xml.setContainer(true);
    	xml.setInclude("myInclude");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlSection test = new TestXmlSection();
		test.saveReferenceXml();
    }
}