package org.openfuxml.client.control;

import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProject;

public interface OfxGuiAction
{
	//Combos
	public void cboApplicationSelected(OfxApplication selectedOfxA);
	public void cboProjectSelected(OfxProject selectedOfxP);
	public void cboDocumentSelected(OfxDocument selectedofxD);
	public void cboFormateSelected(OfxFormat selectedOfxF);
	
	//Buttons
	public void btnUpdate();
}
