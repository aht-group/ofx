package org.openfuxml.producer.handler;

import org.apache.log4j.Logger;

import de.kisner.util.architecture.EnvironmentParameter;

public abstract class AbstractProducer
{
	static Logger logger = Logger.getLogger(AbstractProducer.class);
	protected EnvironmentParameter envP;
	
	public AbstractProducer(EnvironmentParameter envP)
	{
		this.envP=envP;
	}
	
	public void close()
	{
		logger.debug("close im "+AbstractProducer.class.getSimpleName()+" nicht implementiert.");
	}

}
