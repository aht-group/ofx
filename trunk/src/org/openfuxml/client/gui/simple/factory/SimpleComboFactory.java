package org.openfuxml.client.gui.simple.factory;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.openfuxml.client.control.OfxGuiAction;
import org.openfuxml.client.gui.simple.Client;

public class SimpleComboFactory
{
	 static Logger logger = Logger.getLogger(SimpleComboFactory.class);
	
	private Composite composite;
	private Client client;
	private OfxGuiAction ofxAction;
	
	public SimpleComboFactory(Composite composite, OfxGuiAction ofxAction)
	{
		this.composite=composite;
		this.ofxAction=ofxAction;
	}
	
	public SimpleComboFactory(Client client, Composite composite, OfxGuiAction ofxAction)
	{
		this.client=client;
		this.composite=composite;
		this.ofxAction=ofxAction;
	}
	
	public Combo createCboApplication()
	{
		Label labelAnwendungen = new Label(client, SWT.NONE);
		labelAnwendungen.setText("Anwendung");
		labelAnwendungen.setBackground(client.getBackground());

		Combo cboApplications = new Combo(client, SWT.READ_ONLY | SWT.NONE);
		cboApplications.setData("Application");

		GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			cboApplications.setLayoutData(data);

		cboApplications.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				//TODO Update Client Settings
				//ClientConfigWrapper.updateKeyValue("application", comboAnwendungen.getText());
				client.fillCboProjects();
				client.fuelleComboFormate();
				client.entitiesDiscovered();
				client.loescheErgebnis();
			}
		});

		Label labelDummy = new Label(client, SWT.NONE);
		labelDummy.setText("");
		return cboApplications;
	}
	
	public Combo createCboProject()
	{
		Label labelProjekt = new Label(client, SWT.NONE);
		labelProjekt.setText("Projekt");
		labelProjekt.setBackground(client.getBackground());

		Combo cboProjects = new Combo(client, SWT.READ_ONLY | SWT.NONE);
		cboProjects.setData("Project");
		
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		cboProjects.setLayoutData(data);

		cboProjects.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				//TODO Update Client Settings
				//ClientConfigWrapper.updateKeyValue("project", comboProjekte.getText());
				client.fillCboDocuments();
				
				client.entitiesDiscovered();
				client.loescheErgebnis();
			}
		});

		Label labelDummy = new Label(client, SWT.NONE);
		labelDummy.setText("");
		return cboProjects;
	}
	
	public Combo createCboDocument()
	{
		Label labelDocument = new Label(composite, SWT.NONE);
		labelDocument.setText("Dokument");
		labelDocument.setBackground(composite.getBackground());


		Combo cboDocuments = new Combo(composite, SWT.READ_ONLY | SWT.NONE);
		cboDocuments.setData("Document");
		
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		cboDocuments.setLayoutData(data);

		cboDocuments.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				ofxAction.cboDocumentSelected();
			}
		});

		Label labelDummy = new Label(composite, SWT.NONE);
		labelDummy.setText("");
		return cboDocuments;
	}
	
	public Combo createCboFormats()
	{
		Label labelFormate = new Label(composite, SWT.NONE);
		labelFormate.setText("Format");
		labelFormate.setBackground(composite.getBackground());

		Combo cboFormats = new Combo(composite, SWT.READ_ONLY | SWT.NONE);
		cboFormats.setData("Format");

		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		cboFormats.setLayoutData(data);

	
		cboFormats.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				ofxAction.cboFormateSelected();
			}
		});

		Label labelDummy = new Label(composite, SWT.NONE);
		labelDummy.setText("");
		return cboFormats;
	}
}
