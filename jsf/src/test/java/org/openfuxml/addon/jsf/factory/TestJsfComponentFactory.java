package org.openfuxml.addon.jsf.factory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.exlp.util.jx.JaxbUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openfuxml.test.AbstractJsfTest;
import org.openfuxml.xml.addon.jsf.Attribute;
import org.openfuxml.xml.addon.jsf.Component;
import org.openfuxml.xml.xpath.JsfXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJsfComponentFactory extends AbstractJsfTest
{
	final static Logger logger = LoggerFactory.getLogger(JsfComponentFactory.class);
	
	private JsfComponentFactory jcf;
	private final String componentXhtml = "src/main/resources/META-INF/resources/ofx/dummy.xhtml";
	
	@BeforeEach
	public void init()
	{
		jcf = new JsfComponentFactory();
	}
	
	@Test
	public void testInit()
	{
		Assertions.assertNotNull(jcf);
		Component actual = jcf.buildComponent(componentXhtml);
		Assertions.assertNotNull(actual);
		JaxbUtil.debug(actual);
	}
	
	@Test
	public void value() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Component xml = jcf.buildComponent(componentXhtml);
		Attribute actual = JsfXpath.getAttribute(xml, "value");
		Assertions.assertEquals(true, actual.isRequired());
		Assertions.assertEquals("value", actual.getName());
		Assertions.assertEquals("myDefault", actual.getDefault());
		Assertions.assertEquals("myDescription", actual.getShortDescription());		
	}
}