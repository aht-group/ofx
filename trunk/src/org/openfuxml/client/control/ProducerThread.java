package org.openfuxml.client.control;

import org.apache.log4j.Logger;
import org.openfuxml.model.jaxb.Productionresult;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.producer.Producer;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.producer.handler.DirectProducer;

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
	
	private ClientGuiCallback guiCallback;
	private Producer producer;
	private DirectProducer.Typ typ;
	private Sessionpreferences spref;
	String Result;
	String Status;

	

	ProductionRequest pReq;

	/**
	 * Im Konstruktor wird die DispatcherBean erzeugt.
	 * typ wird auf UNDEFINED gesetzt.
	 * @param Parent - das  aufrufende Element
	 * @param JndiHost - String, der Host und Port bestimmt (Syntax host:port).
	 */
	public ProducerThread(ClientGuiCallback guiCallback, Producer producer)
	{
		this.guiCallback = guiCallback;
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
		try
		{
			guiCallback.setStatus("Producing ...");
			Productionresult presult = producer.produce(spref);
			switch(typ)
			{
//    					case ENTITIES:	Parent.setProducedEntities(pe, NO_ERROR);break;
					case PRODUCE:	guiCallback.setStatus("Entities produced");
									guiCallback.setProduced(presult);break;
			}
				
			}
			catch (ProductionSystemException e)
			{
				logger.fatal("ProductionSystemException", e);
//    				Parent.setProducedEntities(pe, ERROR);
			}
			catch (ProductionHandlerException e)
			{
				logger.fatal("ProductionHandlerException", e);
//    				Parent.setProducedEntities(pe, ERROR);
			}		

		
	}

    public void produce(Sessionpreferences spref)
    {
    	typ = DirectProducer.Typ.PRODUCE;
    	this.spref=spref;
    	this.start();
    }
    

}