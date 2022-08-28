package org.openfuxml.test.renderer;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.renderer.OfxRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliOfxRenderer
{
	final static Logger logger = LoggerFactory.getLogger(CliOfxRenderer.class);
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = OfxClientTestBootstrap.init();
		
		OfxRenderer ofx = new OfxRenderer();
		ofx.initCmpUtil(config.getString("ofx.xml.cmp"));
		
		ofx.preProcessor();
		ofx.renderTarget();
	}
}