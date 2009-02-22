package org.openfuxml.producer;

import java.util.List;

import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.producer.ejb.AvailableFormats;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

public interface Producer
{
	//neue Methoden
	ProducedEntities invoke(ProductionRequest request) throws ProductionSystemException,ProductionHandlerException;
	
	//ganz neue Methoden
	List<OfxApplication> getAvailableApplications() throws ProductionSystemException,ProductionHandlerException;
	public List<OfxFormat> getAvailableFormats(OfxApplication ofxA) throws ProductionSystemException, ProductionHandlerException;
	
	public void close();
}

