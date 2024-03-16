package org.openfuxml.content.text;

import org.openfuxml.model.xml.core.text.Text;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlText extends AbstractXmlOfxTextTest<Text>
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlText.class);
	
	public TestXmlText(){super(Text.class);}
	public static Text create(boolean withChildren){return (new TestXmlText()).build(withChildren);}
   
    public Text build(boolean withChildren)
    {
    	Text xml = new Text();
    	xml.setLang("myLang");
    	xml.setClassifier("myClassifier");
    	xml.setValue("myRawValue");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlText test = new TestXmlText();
		test.saveReferenceXml();
    }
}