package org.openfuxml.renderer.processor.latex.content.list;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.Auth;
import org.openfuxml.test.AbstractOfxCoreTest;

public class TestXmlAuth extends AbstractOfxCoreTest
{	
    
    @Test
    public void testAuth() throws FileNotFoundException
    {
    	
    }
   
    public static Auth create()
    {
    	Auth xml = new Auth();
    	xml.setType("wiki");
    	xml.setName("name");
    	xml.setPassword("xyz");
    	return xml;
    }
    
   
}