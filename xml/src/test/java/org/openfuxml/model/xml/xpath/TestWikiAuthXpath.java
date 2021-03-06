package org.openfuxml.model.xml.xpath;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.Servers;
import org.openfuxml.model.xml.addon.wiki.TestXmlServers;
import org.openfuxml.test.AbstractOfxTest;
import org.openfuxml.xml.xpath.wiki.WikiAuthXpath;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

public class TestWikiAuthXpath extends AbstractOfxTest
{
	protected static NsPrefixMapperInterface nsPrefixMapper;
    
    @Test
    public void testAuth() throws FileNotFoundException
    {
    	Servers servers = TestXmlServers.create(true);
    	Assert.assertNotNull(servers);
    	
    }
    
    @Test(expected=ExlpXpathNotFoundException.class)
    public void testAuthNoServerId() throws ExlpXpathNotFoundException
    {
    	Servers servers = TestXmlServers.create(true);
    	WikiAuthXpath.getAuth("-1", "-1");
    	Assert.assertNotNull(servers);
    }
}