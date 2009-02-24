package org.openfuxml.client.gui.simple.factory;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.openfuxml.util.config.OfxPathHelper;

import de.kisner.util.io.resourceloader.ImageResourceLoader;

public class SimpleLabelFactory
{
	 static Logger logger = Logger.getLogger(SimpleLabelFactory.class);
	 
	private static String fs = SystemUtils.FILE_SEPARATOR;
	private Composite client;
	private Configuration config;
	
	public SimpleLabelFactory(Composite composite, Configuration config)
	{
		this.client=composite;
		this.config=config;
	}
	
	public void createLogo()
	{
		logger.debug("Creating Logos");
		String[] logoRes = config.getStringArray("logos/logo[@type='fuxklein']");
		logger.debug(logoRes.length);
		
		for(int i=0;i<logoRes.length;i++)
		{
		
			Label labelImage = new Label(client, SWT.NONE);
			labelImage.setBackground(client.getBackground());
	
			GridData data = new GridData();
			if(logoRes.length==1){data.horizontalSpan = 3;}
			else if(logoRes.length==2){data.horizontalSpan = 2-i;}
			else if(logoRes.length==3){data.horizontalSpan = 1;}
			data.horizontalAlignment = GridData.END;
			data.verticalAlignment = GridData.FILL;
			
			try
			{
				String res = config.getString("logos/@dir")+fs+logoRes[i];
				Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), res, client.getDisplay());
				data.widthHint = img.getBounds().width;
				data.heightHint = img.getBounds().height;
				labelImage.setImage(img);
			}
			catch (Exception e)
			{
				labelImage.setText("ERROR");
				logger.error(e);
				labelImage.setForeground(client.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			labelImage.setLayoutData(data);
		}
	}
	
	public Label createLblRepository()
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
	
	public Label creatLblEvent()
	{
		Label lblEvent = new Label(client, SWT.NONE);
		lblEvent.setText("");
		lblEvent.setBackground(client.getBackground());

		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		lblEvent.setLayoutData(data);
		return lblEvent;
	}
}
