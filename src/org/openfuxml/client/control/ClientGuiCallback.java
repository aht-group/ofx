package org.openfuxml.client.control;

public interface ClientGuiCallback
{
	public void setStatus(String status);
	public void entitiesProduced();
	public void entitiesDiscovered();
	public void setControlsEnabled(boolean enabled);
	public void loescheErgebnis();
	public void getProducableEntities();
	
	public void cboFormatSelected();
}
