package org.openfuxml.interfaces.configuration;

import org.openfuxml.interfaces.media.CrossMediaManager;

public interface ConfigurationProvider
{
	DefaultSettingsManager getDefaultSettingsManager();
	CrossMediaManager getCrossMediaManager();
}