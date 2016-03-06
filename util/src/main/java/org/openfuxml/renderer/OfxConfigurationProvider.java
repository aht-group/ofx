package org.openfuxml.renderer;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;

public class OfxConfigurationProvider implements ConfigurationProvider
{	
	public OfxConfigurationProvider()
	{
		
	}
	
	private DefaultSettingsManager defaultSettingsManager;
	public DefaultSettingsManager getDefaultSettingsManager() {return defaultSettingsManager;}
	public void setDefaultSettingsManager(DefaultSettingsManager defaultSettingsManager) {this.defaultSettingsManager = defaultSettingsManager;}
	
	private CrossMediaManager crossMediaManager;
	public CrossMediaManager getCrossMediaManager() {return crossMediaManager;}
	public void setCrossMediaManager(CrossMediaManager crossMediaManager) {this.crossMediaManager = crossMediaManager;}

}
