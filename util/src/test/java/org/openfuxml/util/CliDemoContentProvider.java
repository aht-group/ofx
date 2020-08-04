package org.openfuxml.util;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.test.OfxUtilTestBootstrap;
import org.openfuxml.util.provider.DemoContentProvider;

import net.sf.exlp.util.xml.JaxbUtil;

public class CliDemoContentProvider
{	
	public static void main(String[] args) throws Exception
    {
		OfxUtilTestBootstrap.init();
			
    	Document doc = DemoContentProvider.buildComplexALL();
    	JaxbUtil.info(doc);
    }
}