package org.openfuxml.client.gui.swt;

import org.apache.log4j.Logger;
import org.openfuxml.client.control.ClientGuiCallback;
import org.openfuxml.client.gui.swt.composites.LogComposite;
import org.openfuxml.client.gui.swt.composites.ProducerComposite;

public class SwtGuiCallback implements ClientGuiCallback
{
	 static Logger logger = Logger.getLogger(SwtGuiCallback.class);
	 
	private ProducerComposite compP;
	private LogComposite compL;
	
	public SwtGuiCallback(){}
	public SwtGuiCallback(ProducerComposite compP, LogComposite compL)
	{
		this.compP=compP;
		this.compL=compL;
	}
	
	public void error(String s){{logger.debug("DISPLAY "+s);}}
	public void addLogline(String s){compL.addLogline(s);}
	public void clearLog(){compL.clearLog();}
	
	public void cboFormatSelected(){compP.zeigeOptionen();}
	public void cboApplicationSelected(){}
	public void cboProjectSelected(){}
	
	public void entitiesProduced(){logger.debug("entitiesProduced");}
	public void setStatus(String status){compP.setStatus(status);}
	public void entitiesDiscovered(){compP.entitiesDiscovered();}
	public void setProductionControlsEnabled(boolean isEnabled){compP.setControlsEnabled(isEnabled);}
	public void loescheErgebnis(){logger.debug("loescheErgebnis");}
}
