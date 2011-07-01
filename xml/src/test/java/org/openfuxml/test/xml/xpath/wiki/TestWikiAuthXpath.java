package org.openfuxml.test.xml.xpath.wiki;

import java.io.FileNotFoundException;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.Auth;
import org.openfuxml.addon.wiki.data.jaxb.Servers;
import org.openfuxml.test.xml.AbstractOfxXmlTest;
import org.openfuxml.test.xml.addon.wiki.TestServer;
import org.openfuxml.xml.xpath.wiki.WikiAuthXpath;

public class TestWikiAuthXpath extends AbstractOfxXmlTest
{
	static Log logger = LogFactory.getLog(TestWikiAuthXpath.class);
    
    @Test
    public void testAuth() throws FileNotFoundException
    {
    	Servers servers = TestServer.createServers(5);
    	
    }
    
    @Test(expected=ExlpXpathNotFoundException.class)
    public void testAuthNoServerId() throws ExlpXpathNotFoundException
    {
    	Servers servers = TestServer.createServers(5);
    	WikiAuthXpath.getAuth("-1", "-1");
    }
}