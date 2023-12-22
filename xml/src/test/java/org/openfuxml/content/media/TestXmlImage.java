package org.openfuxml.content.media;

import org.openfuxml.content.layout.TestXmlAlignment;
import org.openfuxml.content.layout.TestXmlHeight;
import org.openfuxml.content.layout.TestXmlWidth;
import org.openfuxml.content.ofx.TestXmlComment;
import org.openfuxml.content.ofx.TestXmlTitle;
import org.openfuxml.model.xml.core.media.Image;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlImage extends AbstractXmlMediaTest<Image>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlImage(){super(Image.class);}
	public static Image create(boolean withChildren){return (new TestXmlImage()).build(withChildren);}
   
    public Image build(boolean withChilds)
    {
    	Image xml = new Image();
    	xml.setId("myId");
    	xml.setVersion("myVersion");
    	
    	if(withChilds)
    	{
    		xml.setTitle(TestXmlTitle.create(false));
    		xml.setAlignment(TestXmlAlignment.create(false));
    		xml.setComment(TestXmlComment.create(false));
    		xml.setMedia(TestXmlMedia.create(false));
    		xml.setWidth(TestXmlWidth.create(false));
    		xml.setHeight(TestXmlHeight.create(false));
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlImage test = new TestXmlImage();
		test.saveReferenceXml();
    }
}