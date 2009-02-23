package org.openfuxml.client.simple.factory;

import org.apache.commons.configuration.Configuration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.openfuxml.client.simple.Client;

public class SimpleButtonFactory
{
	private Client client;
	
	public SimpleButtonFactory(Client client)
	{
		this.client=client;
	}
	
	public Button createBtnChange()
	{
		Button btnChange = new Button(client, SWT.PUSH | SWT.CENTER);
		btnChange.setText("change");

		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		btnChange.setLayoutData(data);

		btnChange.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent evt) {
				client.Einstellungen();
				}
			});
		return btnChange;
	}
	
	public Button createBtnUpdate()
	{
		Label labelDummy = new Label(client, SWT.NONE);
		labelDummy.setText("");

		GridData data = new GridData();
		data.horizontalSpan = 2;
		labelDummy.setLayoutData(data);

		Button btnUpdate = new Button(client, SWT.PUSH | SWT.CENTER);
		btnUpdate.setText("update");

		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		btnUpdate.setLayoutData(data);

		btnUpdate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				client.loescheErgebnis();
				client.getProducableEntities();
				}
			});
		return btnUpdate;
	}
	
	public Button createBtnProduce()
	{
		Button btnProduce = new Button(client, SWT.PUSH | SWT.CENTER);
		btnProduce.setText("produce");

		GridData data = new GridData();
		data.verticalAlignment = GridData.END;
		data.horizontalAlignment = GridData.FILL;
		btnProduce.setLayoutData(data);

		btnProduce.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				client.loescheErgebnis();
				client.produce();
			}
		});
		return btnProduce;
	}
}
