package org.openfuxml.producer.handler;

import org.apache.log4j.Logger;
import org.openfuxml.communication.cluster.ejb.Host;
import org.openfuxml.communication.cluster.sync.NoSync;
import org.openfuxml.communication.cluster.sync.ServerSync;
import org.openfuxml.communication.cluster.sync.unison.UnisonSync;
import org.openfuxml.producer.ejb.AvailableApplications;
import org.openfuxml.producer.ejb.AvailableFormats;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

import de.kisner.util.xml.XmlConfig;
import de.kisner.util.xml.XmlElementNotFoundException;

public class SyncProducer extends AbstractProducer implements Producer
{
	static Logger logger = Logger.getLogger(DirectProducer.class);
	
	private Producer p;
	private ServerSync unisonSync,noSync;
	
	public SyncProducer(XmlConfig xCnf,Host host)
	{
		String baseDir;
		try {baseDir = xCnf.getTextException("dirs/dir[@typ=\"basedir\"]");}
		catch (XmlElementNotFoundException e)
		{
			baseDir = xCnf.getWorkingDir();
			logger.warn("No \"baseDir\" defined in xmlConfig. Using WorkingDir: "+baseDir);
		}
		
		unisonSync = new UnisonSync(xCnf.getPath("dirs/dir[@typ=\"repository\"]",baseDir),xCnf.getPath("dirs/dir[@typ=\"output\"]",baseDir));
		noSync = new NoSync();
		p = new DirectProducer(xCnf,host);
	}
	
	public AvailableApplications getAvailableApplications() throws ProductionSystemException,ProductionHandlerException
	{
		return p.getAvailableApplications();
	}
	
	public AvailableFormats getAvailableFormats(String application) throws  ProductionSystemException,ProductionHandlerException
	{
		return p.getAvailableFormats(application);
	}
	
	
	public ProducedEntities invoke(ProductionRequest pReq) throws ProductionSystemException,ProductionHandlerException
	{
		ServerSync sync=null;
		logger.debug("invoke aufgerufen: Sync="+pReq.getSync());
		switch(pReq.getSync())
		{
			case UNISON: sync=unisonSync;break;
			case NOSYNC: sync=noSync;break;
		}
		sync.setSyncLocations(pReq.getSyncLocations());
		if(sync.available())
		{
			sync.getRepo(pReq);
			sync.getOutput(pReq);
		}
		ProducedEntities producedEntities=p.invoke(pReq);
		sync.upOutput(pReq);
		return producedEntities;
		
	}
}
