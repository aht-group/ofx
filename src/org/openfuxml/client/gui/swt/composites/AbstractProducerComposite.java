package org.openfuxml.client.gui.swt.composites;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.openfuxml.client.control.OfxClientControl;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

public class AbstractProducerComposite extends Composite
{
	static Logger logger = Logger.getLogger(AbstractProducerComposite.class);
	
	protected OfxClientControl ofxCC;
	
	protected Combo cboFormats, cboDocuments;
	
	public AbstractProducerComposite(Composite parent, int swt)
	{
		super(parent, swt);
	}
	
	public void fillCboFormats()
	{
		cboFormats.removeAll();

		List<OfxFormat> lFormats = ofxCC.getAvailableFormats();
		if(lFormats!=null && lFormats.size()>0)
		{
			for(OfxFormat ofxF : lFormats)
			{
				cboFormats.add(ofxF.getFormat().getTitle());
				cboFormats.setData(ofxF.getFormat().getTitle(),ofxF);
			}
		}
		else {logger.error("Server meldet keine Formate!");}
	}
	
	/**
	 * Die Methode fuelleComboDokumente schreibt alle Dateien aus dem Verzeichnis 
	 * "labelVerzeichnis.getText()/comboAnwendungen.getText()/comboProjekte.getText()", 
	 * die die Endung ".xml" haben, in die Combo comboDokumente.
	 */
	public void fillCboDocuments()
	{
		cboDocuments.removeAll();
//		tabDiscoveredEntities.removeAll();
		

		List<OfxDocument> lOfxD = ofxCC.lDocuments();
		
		for (OfxDocument ofxD : lOfxD)
		{
			cboDocuments.add(ofxD.getName());
			cboDocuments.setData(ofxD.getName(),ofxD);
		}

	}
}
