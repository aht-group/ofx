package org.openfuxml.client.simple.factory;

import java.io.FileNotFoundException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.openfuxml.client.simple.Client;

import de.kisner.util.io.resourceloader.ImageResourceLoader;

public class SimpleLabelFactory
{
	private static String fs = SystemUtils.FILE_SEPARATOR;
	private Client client;
	private Configuration config;
	
	public SimpleLabelFactory(Client client, Configuration config)
	{
		this.client=client;
		this.config=config;
	}
	
	public void createLogo()
	{
		Label labelImage = new Label(client, SWT.NONE);
		labelImage.setBackground(client.getBackground());

		GridData data = new GridData();
		data.widthHint = 131;
		data.heightHint = 60;
		data.horizontalSpan = 3;
		data.horizontalAlignment = GridData.END;
		data.verticalAlignment = GridData.FILL;
		labelImage.setLayoutData(data);

		try
		{
			String res = config.getString("logos/@dir")+fs+config.getString("logos/logo[@type='fuxklein']");
			
			Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), res, client.getDisplay());
			labelImage.setImage(img);
		}
		catch (FileNotFoundException e)
		{
			labelImage.setText("ERROR: Image not found!");
			labelImage.setForeground(client.getDisplay().getSystemColor(SWT.COLOR_RED));
		}
	}
}
