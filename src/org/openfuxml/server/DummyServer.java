package org.openfuxml.server;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import de.kisner.util.xml.XmlConfig;

public class DummyServer extends AbstractServer
{
	static Logger logger = Logger.getLogger(DummyServer.class);
	
	public DummyServer(Configuration config)
	{
		super(config);
		setSystemProperties();
		checkSystemProperties();
	}
}
