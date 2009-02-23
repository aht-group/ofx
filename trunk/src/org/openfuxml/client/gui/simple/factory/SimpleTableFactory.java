package org.openfuxml.client.gui.simple.factory;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class SimpleTableFactory
{
	 static Logger logger = Logger.getLogger(SimpleTableFactory.class);
	 
	private Table tableProductionEntities;
	
	public SimpleTableFactory()
	{

	}
	
	public Table createTable(Composite composite)
	{
		tableProductionEntities = new Table(composite, SWT.CHECK | SWT.BORDER);

		{
			GridData data = new GridData();
			data.widthHint = 450;
			data.heightHint = 200;
			data.horizontalSpan = 2;
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			data.verticalAlignment = GridData.FILL;
			data.grabExcessVerticalSpace = true;
			tableProductionEntities.setLayoutData(data);
		}

		{
			TableColumn tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
			tableColumn.setText("");
			tableColumn.setWidth(20);
		}
		{
			TableColumn tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
			tableColumn.setText("Beschreibung");
			tableColumn.setWidth(160);
		}
		{
			TableColumn tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
			tableColumn.setText("Serverausgabe");
			tableColumn.setWidth(180);
		}
		{
			TableColumn tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
			tableColumn.setText("Dateiname");
			tableColumn.setWidth(100);
		}
		tableProductionEntities.setHeaderVisible(true);
		tableProductionEntities.setLinesVisible(true);
		
		tableProductionEntities.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent evt) {
				// Bestimmen des ausgewählten Eintrags.
				TableItem[] selection = tableProductionEntities.getSelection();
				TableItem selectedRow = selection[0];

				logger.debug(selectedRow.getText(1)+" "+selectedRow.getText(2));
			}
		});
		
		return tableProductionEntities;
	}
}
