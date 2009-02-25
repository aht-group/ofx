package org.openfuxml.client.gui.swt;

import org.apache.log4j.Logger;
import org.openfuxml.client.control.ClientGuiCallback;
import org.openfuxml.client.gui.swt.composites.ProducerComposite;

public class SwtGuiCallback implements ClientGuiCallback
{
	 static Logger logger = Logger.getLogger(SwtGuiCallback.class);
	 
	private ProducerComposite compP;
	
	public SwtGuiCallback(){}
	public SwtGuiCallback(ProducerComposite compP)
	{
		this.compP=compP;
	}
	
	public void error(String s){{logger.debug("DISPLAY "+s);}}
	
	public void cboFormatSelected(){compP.zeigeOptionen();}
	public void cboApplicationSelected(){}
	public void cboProjectSelected(){}
	
	public void entitiesProduced(){logger.debug("entitiesProduced");}
	public void setStatus(String status){compP.setStatus(status);}
	public void entitiesDiscovered(){logger.debug("entitiesDiscovered");}
	public void setControlsEnabled(boolean enabled){logger.debug("setControlsEnabled");}
	public void loescheErgebnis(){logger.debug("loescheErgebnis");}
}
