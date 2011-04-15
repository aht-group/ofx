package org.openfuxml.server;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DummyServer extends AbstractServer
{
	static Log logger = LogFactory.getLog(DummyServer.class);
	
	public DummyServer(Configuration config)
	{
		super(config);
		setSystemProperties();
		checkSystemProperties();
	}
}
