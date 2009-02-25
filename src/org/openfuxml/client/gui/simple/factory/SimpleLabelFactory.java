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
	private Composite composite;
	private Configuration config;
	
	public SimpleLabelFactory(Composite composite, Configuration config)
	{
		this.composite=composite;
		this.config=config;
	}
	
	public void createDummyLabel(int horizontalSpan)
	{
		Label labelDummy = new Label(composite, SWT.NONE);
		labelDummy.setText("");

		GridData data = new GridData();
		data.horizontalSpan = 2;
		labelDummy.setLayoutData(data);
	}
	
	public void createLogo()
	{
		logger.debug("Creating Logos");
		String[] logoRes = config.getStringArray("logos/logo[@type='fuxklein']");
		logger.debug(logoRes.length);
		
		for(int i=0;i<logoRes.length;i++)
		{
		
			Label labelImage = new Label(composite, SWT.NONE);
			labelImage.setBackground(composite.getBackground());
	
			GridData data = new GridData();
			if(logoRes.length==1){data.horizontalSpan = 3;}
			else if(logoRes.length==2){data.horizontalSpan = 2-i;}
			else if(logoRes.length==3){data.horizontalSpan = 1;}
			data.horizontalAlignment = GridData.END;
			data.verticalAlignment = GridData.FILL;
			
			try
			{
				String res = config.getString("logos/@dir")+fs+logoRes[i];
				Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), res, composite.getDisplay());
				data.widthHint = img.getBounds().width;
				data.heightHint = img.getBounds().height;
				labelImage.setImage(img);
			}
			catch (Exception e)
			{
				labelImage.setText("ERROR");
				logger.error(e);
				labelImage.setForeground(composite.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			labelImage.setLayoutData(data);
		}
	}
	
	public Label createLblRepository()
	{
		Label labelVerz = new Label(composite, SWT.NONE);
		labelVerz.setText("Verzeichnis");
		labelVerz.setBackground(composite.getBackground());
			

			Label lblRepository = new Label(composite, SWT.NONE);
			lblRepository.setBackground(composite.getBackground());

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
		Label lblEvent = new Label(composite, SWT.NONE);
		lblEvent.setText("");
		lblEvent.setBackground(composite.getBackground());

		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		lblEvent.setLayoutData(data);
		return lblEvent;
	}
}
