package org.openfuxml.client.gui.swt.factory;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.openfuxml.client.control.OfxGuiAction;
import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Sessionpreferences.Productionentities;

public class ProducerEntitiesDisplayFactory
{
	static Logger logger = Logger.getLogger(ProducerEntitiesDisplayFactory.class);
	
	private OfxGuiAction ofxAction;
	
	public ProducerEntitiesDisplayFactory(OfxGuiAction ofxAction)
	{
		this.ofxAction=ofxAction;
	}
	
	public TabFolder createTabFolder(Composite composite)
	{
		TabFolder tf = new TabFolder(composite, SWT.TOP);
		GridData data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.grabExcessVerticalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.FILL;
			data.horizontalSpan = 4;
			tf.setLayoutData(data);
		return tf;
	}
	
	public Table createTable(Composite composite)
	{
		final Table tableProductionEntities = new Table(composite, SWT.CHECK | SWT.BORDER);

		GridData data = new GridData();
			data.widthHint = 450;
			data.heightHint = 200;
			data.horizontalSpan = 2;
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			data.verticalAlignment = GridData.FILL;
			data.grabExcessVerticalSpace = true;
			tableProductionEntities.setLayoutData(data);

		TableColumn tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
			tableColumn.setText("");
			tableColumn.setWidth(20);

		tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
			tableColumn.setText("Beschreibung");
			tableColumn.setWidth(160);

		tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
			tableColumn.setText("Serverausgabe");
			tableColumn.setWidth(180);
			
		tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
			tableColumn.setText("Dateiname");
			tableColumn.setWidth(100);

		tableProductionEntities.setHeaderVisible(true);
		tableProductionEntities.setLinesVisible(true);
		
		tableProductionEntities.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt)
			{
				ProducibleEntities pe = new ProducibleEntities();
				for(int i=0; i<tableProductionEntities.getItemCount(); i++)
				{
					TableItem tableItem = tableProductionEntities.getItem(i);
					if (tableItem.getChecked())
					{
						logger.debug("setData?");
						Productionentities.File fi = (Productionentities.File)tableItem.getData();
						logger.debug("setData?");
						if(fi!=null){logger.debug("YES: "+fi.getDescription());}
						else{logger.debug("NO");}
						ProducibleEntities.File f = new ProducibleEntities.File();
							f.setDescription(tableItem.getText(1));
							f.setDirectory(tableItem.getText(2));
							f.setFilename(tableItem.getText(3));
						pe.getFile().add(f);
					} 
				}
				ofxAction.tblEntitiesSelected(pe);
			}
			
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