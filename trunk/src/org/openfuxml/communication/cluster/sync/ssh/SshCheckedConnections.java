package org.openfuxml.communication.cluster.sync.ssh;

import org.apache.log4j.Logger;

public class SshCheckedConnections extends Thread
{
	static Logger logger = Logger.getLogger(SshCheckedConnections.class);
	
	int verifiedConnectionLifetime;
	
	public SshCheckedConnections(int verifiedConnectionLifetime)
	{
		this.verifiedConnectionLifetime=verifiedConnectionLifetime;
		logger.warn("Cache"+SshCheckedConnections.class.getSimpleName()+"Noch nicht implementiert");
	}
	
	public boolean stillVerified(String sshConnection)
	{
		//TODO Muss noch implementiert werden
		return false;
	}
	
	public void verified(String sshConnection)
	{
		
	}
}
