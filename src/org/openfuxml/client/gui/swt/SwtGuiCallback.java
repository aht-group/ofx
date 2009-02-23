package org.openfuxml.client.gui.swt;

import org.apache.log4j.Logger;
import org.openfuxml.client.control.ClientGuiCallback;
import org.openfuxml.client.gui.simple.Client;
import org.openfuxml.client.gui.swt.composites.ProduzierenComposite;

public class SwtGuiCallback implements ClientGuiCallback
{
	 static Logger logger = Logger.getLogger(SwtGuiCallback.class);
	 
	private ProduzierenComposite compP;
	
	public SwtGuiCallback(){}
	public SwtGuiCallback(ProduzierenComposite compP)
	{
		this.compP=compP;
	}
	
	public void cboFormatSelected(){compP.zeigeOptionen();}
	public void entitiesProduced(){logger.debug("entitiesProduced");}
	public void setStatus(String status){compP.setStatus(status);}
	public void entitiesDiscovered(){logger.debug("entitiesDiscovered");}
	public void setControlsEnabled(boolean enabled){logger.debug("setControlsEnabled");}
	public void loescheErgebnis(){logger.debug("loescheErgebnis");}
	public void getProducableEntities(){compP.getProducableEntities();}
}
