package org.openfuxml.client.control;

import org.openfuxml.model.jaxb.Productionresult;

public interface ClientGuiCallback
{
	public void setStatus(String status);
	public void setProduced(Productionresult presult);
}
