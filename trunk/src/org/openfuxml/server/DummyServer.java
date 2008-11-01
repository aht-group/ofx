package org.openfuxml.server;

import org.apache.log4j.Logger;

import de.kisner.util.xml.XmlConfig;

public class DummyServer extends AbstractServer
{
	static Logger logger = Logger.getLogger(DummyServer.class);
	
	public DummyServer(XmlConfig xCnf)
	{
		logger.debug("vor set");
		setSystemProperties(xCnf);
		logger.debug("nach set");
		checkSystemProperties();
	}
}
