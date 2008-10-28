/*
 * Created on 2006-12-09
 */
package org.openfuxml.communication.cluster.sync;

import java.util.Collection;

import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.ejb.SyncLocation;
import org.openfuxml.producer.exception.ProductionHandlerException;

/**
 * @author Thorsten
 */
public interface ServerSync
{
	/**
	 * @author Thorsten
	 * Das gesamte Repository f�r das aktive Projeckt wird mit dem Server
	 * synchronisiert. Der Server �berschreibt lokale �nderungen.
	 */
	public void getRepo(ProductionRequest request);
	
	/**
	 * @author Thorsten
	 * Das gesamte Output f�r das aktive Projeckt wird mit dem Server
	 * synchronisiert. Der Server �berschreibt lokale �nderungen.
	 */
	public void getOutput(ProductionRequest request);
	
	public void upOutput(ProductionRequest request);//,ProductionResult result);
	
	/**
	 * @author Thorsten
	 * Pr�ft, ob die Synchronisationsmethode verf�gbar ist.
	 */
	public boolean available() throws ProductionHandlerException;
	
	/**
	 * @author Thorsten
	 * Definiert die Sync-Ziele f+�r die aktuelle Verbindung.
	 */
	public void setSyncLocations(Collection<SyncLocation> sls);
}

