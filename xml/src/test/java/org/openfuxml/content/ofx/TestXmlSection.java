package org.openfuxml.content.ofx;

import java.io.FileNotFoundException;

import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.content.media.TestXmlImage;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSection extends AbstractXmlOfxTest<Section>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlSection(){super(Section.class);}
	public static Section create(boolean withChildren){return (new TestXmlSection()).build(withChildren);}
   
    public Section build(boolean withChilds)
    {
    	Section xml = new Section();
    	xml.setId("myId");
    	xml.setLang("myLang");
    	xml.setClassifier("myClassifier");
    	xml.setInclude("myInclude");
    	
    	logger.warn("Not fully implemented");
    	if(withChilds)
    	{
    		xml.getContent().add(TestXmlComment.create(false));
    		xml.getContent().add(TestXmlTitle.create(false));
    		xml.getContent().add(TestXmlTitle.create(false));
    		xml.getContent().add(TestXmlParagraph.create(false));
 //   		xml.getContent().add(TestXmlHighlight.create(false));
            xml.getContent().add(TestXmlListing.create(false));
            xml.getContent().add(TestXmlImage.create(false));
    	}
    	
    	return xml;
    }
    
    @Test @Ignore public void xml() throws FileNotFoundException { }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlSection test = new TestXmlSection();
		test.saveReferenceXml();
    }
}