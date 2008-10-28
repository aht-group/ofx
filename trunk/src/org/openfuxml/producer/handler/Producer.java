package org.openfuxml.producer.handler;

//import rs.ssi.*;
import org.openfuxml.producer.ejb.AvailableApplications;
import org.openfuxml.producer.ejb.AvailableFormats;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

public interface Producer
{
	//neue Methoden
	AvailableApplications getAvailableApplications() throws ProductionSystemException,ProductionHandlerException;
	AvailableFormats getAvailableFormats(String application) throws  ProductionSystemException,ProductionHandlerException;
	ProducedEntities invoke(ProductionRequest request) throws ProductionSystemException,ProductionHandlerException;
	
	public void close();

}

