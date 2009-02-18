package org.openfuxml.client.simple;

import org.apache.log4j.Logger;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.ejb.RequestWrapper.ProductionType;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.producer.handler.Producer;

/**
 * ProducerThread implementiert den Thread, in dem die Methoden      
 * producableEntities bzw. produce der DispatcherBean ausgeführt werden.
 *   
 * @author Andrea Frank
 */
public class ProducerThread extends Thread
{
	final static int NO_ERROR	= 0;
	final static int ERROR		= 1;
	
	static Logger logger = Logger.getLogger(ProducerThread.class);

	ProductionType productionType;
	
	Client Parent;
	
	String Result;
	String Status;

	Producer producer;

	ProductionRequest pReq;

	/**
	 * Im Konstruktor wird die DispatcherBean erzeugt.
	 * typ wird auf UNDEFINED gesetzt.
	 * @param Parent - das  aufrufende Element
	 * @param JndiHost - String, der Host und Port bestimmt (Syntax host:port).
	 */
	public ProducerThread(Client Parent, Producer producer)
	{
		this.Parent = Parent;
		this.producer = producer;
	}

	/**
	 * In der Methode run wird unterschieden (typ), welche Methode der DispatcherBean
	 * aufgerufen wird. Nach  dem Aufruf wird die Bean entfernt und das Ergebnis 
	 * dem aufrufenden Element übergeben (Parent.fuelleTableProductionEntities 
	 * bzw. Parent.ProduktionEnde).
	 * Zum Schluss wird der Thread gestoppt.
	 */
    public void run()
	{
    	ProducedEntities pe = null;
    	switch (productionType)
		{
    		case INVOKE:
    			
    			try
    			{
    				pe = producer.invoke(pReq);
//   				logger.info("Producable Entities:\n" + pReq);

    				switch(pe.getTyp())
    				{
    					case ENTITIES:	Parent.setProducedEntities(pe, NO_ERROR);break;
    					case PRODUCE:	Parent.setProduced(pe, NO_ERROR);break;
    				}
    				
    			}
    			catch (ProductionSystemException e)
    			{
    				logger.fatal("ProductionSystemException", e);
    				Parent.setProducedEntities(pe, ERROR);
    			}
    			catch (ProductionHandlerException e)
    			{
    				logger.fatal("ProductionHandlerException", e);
    				Parent.setProducedEntities(pe, ERROR);
    			}		

    			break;

    		default:
    			logger.error("FEHLER: ProducerThread");
    			logger.error("        typ: " + productionType);
    			break;
		}
	}

    public void startInvoke(ProductionRequest pReq)
    {
    	this.pReq=pReq;
    	productionType = ProductionType.INVOKE;
    	this.start();
    }
}