package org.openfuxml.xml.xpath.jsf;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openfuxml.model.xml.addon.jsf.TestXmlAttribute;
import org.openfuxml.model.xml.addon.jsf.TestXmlComponent;
import org.openfuxml.test.AbstractJsfTest;
import org.openfuxml.xml.addon.jsf.Attribute;
import org.openfuxml.xml.addon.jsf.Component;
import org.openfuxml.xml.xpath.JsfXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXPathJsfAttribute extends AbstractJsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXPathJsfAttribute.class);
    
	private Component parent;
	private final String codeOk = "code";
	private final String codeMulti = "multi";
	
	@BeforeEach
	public void iniDbseed()
	{
		parent = TestXmlComponent.create(false);

		Attribute child1 = TestXmlAttribute.create(false);child1.setName(codeOk);parent.getAttribute().add(child1);
		Attribute child2 = TestXmlAttribute.create(false);child2.setName(codeMulti);parent.getAttribute().add(child2);
		Attribute child3 = TestXmlAttribute.create(false);child3.setName(codeMulti);parent.getAttribute().add(child3);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Attribute test = JsfXpath.getAttribute(parent, codeOk);
	    Assertions.assertEquals(codeOk,test.getName());
	}

	@Test
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
	    Assertions.assertThrows(ExlpXpathNotFoundException.class, () -> {
		JsfXpath.getAttribute(parent, "-1");
	    });
	}
	
	 @Test
	 public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	 {
	     Assertions.assertThrows(ExlpXpathNotUniqueException.class, () -> {
		 JsfXpath.getAttribute(parent, codeMulti);
	     });
	 }
}