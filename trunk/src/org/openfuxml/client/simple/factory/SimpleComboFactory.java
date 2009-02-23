package org.openfuxml.client.simple.factory;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.openfuxml.client.simple.Client;
import org.openfuxml.util.config.OfxPathHelper;

public class SimpleComboFactory
{
	 static Logger logger = Logger.getLogger(SimpleComboFactory.class);
	 
	private Client client;
	private Configuration config;
	
	public SimpleComboFactory(Client client, Configuration config)
	{
		this.client=client;
		this.config=config;
	}
	
	public Label createCboRepository()
	{
		Label labelVerz = new Label(client, SWT.NONE);
		labelVerz.setText("Verzeichnis");
		labelVerz.setBackground(client.getBackground());
			

			Label lblRepository = new Label(client, SWT.NONE);
			lblRepository.setBackground(client.getBackground());

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				lblRepository.setLayoutData(data);
			}
			
			lblRepository.setText(OfxPathHelper.getDir(config, "repository"));
			logger.debug("Repository: "+lblRepository.getText());

		
		return lblRepository;
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
		Label labelDocument = new Label(client, SWT.NONE);
		labelDocument.setText("Dokument");
		labelDocument.setBackground(client.getBackground());


		Combo cboDocuments = new Combo(client, SWT.READ_ONLY | SWT.NONE);
		cboDocuments.setData("Document");
		
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		cboDocuments.setLayoutData(data);

		cboDocuments.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				//TODO Update Client Settings
				//ClientConfigWrapper.updateKeyValue("document", comboDokumente.getText());
				client.entitiesDiscovered();
				client.loescheErgebnis();
			}
		});

		Label labelDummy = new Label(client, SWT.NONE);
		labelDummy.setText("");
		return cboDocuments;
	}
	
	public Combo createCboFormats()
	{
		Label labelFormate = new Label(client, SWT.NONE);
		labelFormate.setText("Format");
		labelFormate.setBackground(client.getBackground());

		Combo cboFormats = new Combo(client, SWT.READ_ONLY | SWT.NONE);
		cboFormats.setData("Format");

		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		cboFormats.setLayoutData(data);

	
		cboFormats.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				//TODO Update Client Settings
				//ClientConfigWrapper.updateKeyValue("format", comboFormate.getText());
				client.entitiesDiscovered();
				client.loescheErgebnis();
			}
		});

		Label labelDummy = new Label(client, SWT.NONE);
		labelDummy.setText("");
		return cboFormats;
	}
}
