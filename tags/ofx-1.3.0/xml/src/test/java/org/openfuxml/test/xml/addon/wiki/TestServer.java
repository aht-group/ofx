package org.openfuxml.test.xml.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.Server;
import org.openfuxml.addon.wiki.data.jaxb.Servers;
import org.openfuxml.test.xml.AbstractOfxXmlTest;

public class TestServer extends AbstractOfxXmlTest
{
	static Log logger = LogFactory.getLog(TestServer.class);
	
	private static final String rootDir = "src/test/resources/data/xml/wiki";
	
	private static File fServer,fServers;
	
	@BeforeClass
	public static void initFiles()
	{
		fServer = new File(rootDir,"server.xml");
		fServers = new File(rootDir,"servers.xml");
	}
    
    @Test
    public void testServer() throws FileNotFoundException
    {
    	Server test = createServer(true);
    	Server ref = (Server)JaxbUtil.loadJAXB(fServer.getAbsolutePath(), Server.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
    
    @Test
    public void testServers() throws FileNotFoundException
    {
    	Servers test = createServers(2);
    	Servers ref = (Servers)JaxbUtil.loadJAXB(fServers.getAbsolutePath(), Servers.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
 
    public void save()
    {
    	Server xmlServer = createServer(true);
    	JaxbUtil.debug2(this.getClass(),xmlServer, nsPrefixMapper);
    	JaxbUtil.save(fServer, xmlServer, nsPrefixMapper, true);
    	
    	Servers xmlServers = createServers(2);
    	JaxbUtil.debug2(this.getClass(),xmlServers, nsPrefixMapper);
    	JaxbUtil.save(fServers, xmlServers, nsPrefixMapper, true);
    }
    
    public static Servers createServers(int childs)
    {
    	Servers servers = new Servers();
    	if(childs>0)
    	{
    		for(int i=0;i<childs;i++)
    		{
    			servers.getServer().add(createServer(true));
    		}
    	}
    	return servers;
    }
   
    public static Server createServer(boolean withChilds)
    {
    	Server xml = new Server();
    	xml.setId("myId");
    	xml.setUrl("http://");
    	xml.setName("name");
    	xml.setDefault(true);
    	if(withChilds)
    	{
    		xml.getAuth().add(TestAuth.createAuth());
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestServer.initFiles();	
		TestServer test = new TestServer();
		test.save();
    }
}