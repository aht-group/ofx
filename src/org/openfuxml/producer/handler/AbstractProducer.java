package org.openfuxml.producer.handler;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.jaxb.Productionresult;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

import de.kisner.util.architecture.EnvironmentParameter;

public abstract class AbstractProducer
{
	static Logger logger = Logger.getLogger(AbstractProducer.class);
	protected EnvironmentParameter envP;
	protected Configuration config;
	
	public AbstractProducer(Configuration config, EnvironmentParameter envP)
	{
		this.config=config;
		this.envP=envP;
	}
	
	public void close()
	{
		logger.debug("close im "+AbstractProducer.class.getSimpleName()+" nicht implementiert.");
	}
	
	public List<OfxApplication> getAvailableApplications22() throws ProductionSystemException,ProductionHandlerException
	{
		logger.warn("Not implemented for this Handler!!");
		return null;
	}
	
	public Productionresult produce(Sessionpreferences ofxR) throws ProductionSystemException
	{
		logger.warn("Not implemented for this Handler!!");
		return null;
	}
}
