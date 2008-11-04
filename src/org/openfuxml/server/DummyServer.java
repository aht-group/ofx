package org.openfuxml.server;

import org.apache.log4j.Logger;

import de.kisner.util.xml.XmlConfig;

public class DummyServer extends AbstractServer
{
	static Logger logger = Logger.getLogger(DummyServer.class);
	
	public DummyServer(XmlConfig xCnf)
	{
		setSystemProperties(xCnf);
		checkSystemProperties();
	}
}
