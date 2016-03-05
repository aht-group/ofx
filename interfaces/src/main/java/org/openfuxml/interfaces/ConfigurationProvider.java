package org.openfuxml.interfaces;

import org.openfuxml.interfaces.media.CrossMediaManager;

public interface ConfigurationProvider
{
	public DefaultSettingsManager getDefaultSettingsManager();
	public CrossMediaManager getCrossMediaManager();
}