package org.openfuxml.client.control.log;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.openfuxml.client.control.ClientGuiCallback;

public class QueueLogConsumer extends Thread
{
	static Logger logger = Logger.getLogger(QueueLogConsumer.class);
	
	private LinkedBlockingQueue<String> q;
	private ClientGuiCallback guiCallback;
	private boolean active;
	
	public QueueLogConsumer(LinkedBlockingQueue<String> q, ClientGuiCallback guiCallback)
	{
		this.q=q;
		this.guiCallback=guiCallback;
		active=true;
	}
	
	public void run()
	{
		while(active)
		{
			try
			{
				String s = q.take();
				guiCallback.addLogline(s);
			}
			catch (InterruptedException e) {logger.error(e);}
		}
	}
}
