package org.openfuxml.client.control;

public interface ClientGuiCallback
{
	public void setStatus(String status);
	public void entitiesProduced();
	public void entitiesDiscovered();
	public void loescheErgebnis();
	
	//ErrorMessage
	public void error(String s);
	
	//Combo-Actions
	public void cboFormatSelected();
	public void cboApplicationSelected();
	public void cboProjectSelected();
	
	//Controls
	public void setProductionControlsEnabled(boolean isEnabled);
}
