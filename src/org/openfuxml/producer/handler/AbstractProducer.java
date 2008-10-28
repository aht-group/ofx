package org.openfuxml.producer.handler;

import org.apache.log4j.Logger;

public abstract class AbstractProducer
{
	static Logger logger = Logger.getLogger(AbstractProducer.class);
			
	public void close()
	{
		logger.debug("close im "+AbstractProducer.class.getSimpleName()+" nicht implementiert.");
	}

}
