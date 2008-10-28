package org.openfuxml.server;

import de.kisner.util.xml.XmlConfig;

public class DummyServer extends AbstractServer
{
	public DummyServer(XmlConfig xCnf)
	{
		setSystemProperties(xCnf);
		checkSystemProperties();
	}
}
